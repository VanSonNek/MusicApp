package com.example.musicapp.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.Model.Playlist
import com.example.musicapp.Model.PlaylistSong
import com.example.musicapp.Model.Song
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlaylistViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val playlistsRef = database.getReference("playlists")
    private val songsRef = database.getReference("Songs")
    private val auth = FirebaseAuth.getInstance()

    val userPlaylists = mutableStateOf<List<Playlist>>(emptyList())
    val publicPlaylists = mutableStateOf<List<Playlist>>(emptyList())
    val currentPlaylist = mutableStateOf<Playlist?>(null)
    val playlistSongs = mutableStateOf<List<Song>>(emptyList())

    init {
        loadUserPlaylists()
        loadPublicPlaylists()
    }

    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun loadUserPlaylists() {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) {
            userPlaylists.value = emptyList()
            return
        }

        playlistsRef.orderByChild("createdBy").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val playlists = mutableListOf<Playlist>()
                    for (playlistSnapshot in snapshot.children) {
                        val playlist = playlistSnapshot.getValue(Playlist::class.java)
                        playlist?.let { playlists.add(it) }
                    }
                    userPlaylists.value = playlists
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("PlaylistViewModel", "Error loading user playlists: ${error.message}")
                }
            })
    }

    fun loadPublicPlaylists() {
        playlistsRef.orderByChild("isPublic").equalTo(true)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val playlists = mutableListOf<Playlist>()
                    for (playlistSnapshot in snapshot.children) {
                        val playlist = playlistSnapshot.getValue(Playlist::class.java)
                        playlist?.let { playlists.add(it) }
                    }
                    publicPlaylists.value = playlists
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("PlaylistViewModel", "Error loading public playlists: ${error.message}")
                }
            })
    }

    fun loadPlaylistById(playlistId: String, onComplete: (Boolean) -> Unit = {}) {
        playlistsRef.child(playlistId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playlist = snapshot.getValue(Playlist::class.java)
                currentPlaylist.value = playlist
                playlist?.let { loadPlaylistSongs(it) }
                onComplete(playlist != null)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PlaylistViewModel", "Error loading playlist: ${error.message}")
                onComplete(false)
            }
        })
    }

    private fun loadPlaylistSongs(playlist: Playlist) {
        if (playlist.songs.isEmpty()) {
            playlistSongs.value = emptyList()
            return
        }

        val songIds = playlist.songs.keys.toList()
        val songs = mutableListOf<Song>()

        songIds.forEach { songId ->
            songsRef.child(songId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val song = snapshot.getValue(Song::class.java)
                    song?.let {
                        songs.add(it)
                        if (songs.size == songIds.size) {
                            // Sắp xếp bài hát theo thứ tự trong playlist
                            val sortedSongs = songs.sortedBy { song ->
                                playlist.songs[song.id]?.position ?: Int.MAX_VALUE
                            }
                            playlistSongs.value = sortedSongs
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("PlaylistViewModel", "Error loading song: ${error.message}")
                }
            })
        }
    }

    fun createPlaylist(
        title: String,
        description: String,
        coverImageUrl: String,
        isPublic: Boolean,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) {
            onComplete(false, "Bạn cần đăng nhập để tạo playlist")
            return
        }

        val playlistId = playlistsRef.push().key
        if (playlistId == null) {
            onComplete(false, "Không thể tạo playlist mới")
            return
        }

        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            .format(Date())

        val playlist = Playlist(
            id = playlistId,
            title = title,
            description = description,
            coverImageUrl = coverImageUrl,
            createdBy = userId,
            createdAt = timestamp,
            totalSongs = 0,
            isPublic = isPublic
        )

        playlistsRef.child(playlistId).setValue(playlist)
            .addOnSuccessListener {
                onComplete(true, playlistId)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    fun addSongToPlaylist(playlistId: String, songId: String, onComplete: (Boolean, String?) -> Unit) {
        playlistsRef.child(playlistId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playlist = snapshot.getValue(Playlist::class.java)
                if (playlist == null) {
                    onComplete(false, "Không tìm thấy playlist")
                    return
                }

                // Kiểm tra quyền chỉnh sửa playlist
                if (playlist.createdBy != getCurrentUserId()) {
                    onComplete(false, "Bạn không có quyền chỉnh sửa playlist này")
                    return
                }

                // Kiểm tra xem bài hát đã có trong playlist chưa
                if (playlist.songs.containsKey(songId)) {
                    onComplete(false, "Bài hát đã có trong playlist")
                    return
                }

                val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    .format(Date())

                val position = playlist.songs.size + 1
                val updates = HashMap<String, Any>()
                updates["songs/$songId"] = PlaylistSong(position, timestamp)
                updates["totalSongs"] = playlist.totalSongs + 1

                playlistsRef.child(playlistId).updateChildren(updates)
                    .addOnSuccessListener {
                        onComplete(true, null)
                    }
                    .addOnFailureListener { e ->
                        onComplete(false, e.message)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(false, error.message)
            }
        })
    }

    fun removeSongFromPlaylist(playlistId: String, songId: String, onComplete: (Boolean, String?) -> Unit) {
        playlistsRef.child(playlistId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playlist = snapshot.getValue(Playlist::class.java)
                if (playlist == null) {
                    onComplete(false, "Không tìm thấy playlist")
                    return
                }

                // Kiểm tra quyền chỉnh sửa playlist
                if (playlist.createdBy != getCurrentUserId()) {
                    onComplete(false, "Bạn không có quyền chỉnh sửa playlist này")
                    return
                }

                // Kiểm tra xem bài hát có trong playlist không
                if (!playlist.songs.containsKey(songId)) {
                    onComplete(false, "Bài hát không có trong playlist")
                    return
                }

                val updates = HashMap<String, Any?>()
                updates["songs/$songId"] = null
                updates["totalSongs"] = playlist.totalSongs - 1

                playlistsRef.child(playlistId).updateChildren(updates)
                    .addOnSuccessListener {
                        onComplete(true, null)
                    }
                    .addOnFailureListener { e ->
                        onComplete(false, e.message)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(false, error.message)
            }
        })
    }

    fun deletePlaylist(playlistId: String, onComplete: (Boolean, String?) -> Unit) {
        playlistsRef.child(playlistId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playlist = snapshot.getValue(Playlist::class.java)
                if (playlist == null) {
                    onComplete(false, "Không tìm thấy playlist")
                    return
                }

                // Kiểm tra quyền xóa playlist
                if (playlist.createdBy != getCurrentUserId()) {
                    onComplete(false, "Bạn không có quyền xóa playlist này")
                    return
                }

                playlistsRef.child(playlistId).removeValue()
                    .addOnSuccessListener {
                        onComplete(true, null)
                    }
                    .addOnFailureListener { e ->
                        onComplete(false, e.message)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(false, error.message)
            }
        })
    }
}
