package com.example.musicapp.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DanhSachBaiHatVieModel : ViewModel() {
    private val database = Firebase.database
    private val songsRef = database.getReference("Songs")
    private val artistsRef = database.getReference("artists")

    var songsList by mutableStateOf<List<Map<String, Any>>>(emptyList())
        private set

    var artistsMap by mutableStateOf<Map<String, Map<String, Any>>>(emptyMap())
        private set

    init {
        // Tải nghệ sĩ trước, sau khi tải xong mới tải bài hát
        loadArtistsFromFirebase {
            loadSongsFromFirebase()
        }
    }

    private fun loadArtistsFromFirebase(onComplete: () -> Unit) {
        artistsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val artists = mutableMapOf<String, Map<String, Any>>()
                for (childSnapshot in snapshot.children) {
                    val artistId = childSnapshot.key ?: continue
                    val artistData = childSnapshot.value as? Map<String, Any> ?: continue
                    artists[artistId] = artistData
                }
                artistsMap = artists
                Log.d("Firebase", "Đã tải xong ${artists.size} nghệ sĩ")
                onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Lỗi tải nghệ sĩ: ${error.message}")
                onComplete() // Vẫn tiếp tục tải bài hát dù có lỗi
            }
        })
    }

    private fun loadSongsFromFirebase() {
        songsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val songs = mutableListOf<Map<String, Any>>()
                for (childSnapshot in snapshot.children) {
                    try {
                        val songData = childSnapshot.value as? Map<String, Any> ?: continue
                        val processedSong = processSongData(songData)
                        songs.add(processedSong)
                    } catch (e: Exception) {
                        Log.e("Firebase", "Lỗi xử lý bài hát ${childSnapshot.key}: ${e.message}")
                    }
                }
                songsList = songs
                Log.d("Firebase", "Đã tải xong ${songs.size} bài hát")
                logSongData() // Log chi tiết dữ liệu bài hát
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Lỗi tải bài hát: ${error.message}")
            }
        })
    }

    private fun processSongData(rawSong: Map<String, Any>): Map<String, Any> {
        return mutableMapOf<String, Any>().apply {
            putAll(rawSong) // Copy tất cả dữ liệu gốc

            // Xử lý artistName
            val artistId = rawSong["artistId"] as? String ?: ""
            val artistName = artistsMap[artistId]?.get("name") as? String ?: "Unknown Artist"
            put("artistName", artistName)

            // Xử lý luotNghe
            val luotNghe = when (val plays = rawSong["luotNghe"]) {
                is Long -> plays.toInt()
                is Int -> plays
                is String -> plays.toIntOrNull() ?: 0
                else -> 0
            }
            put("luotNghe", luotNghe)
        }
    }

    private fun logSongData() {
        songsList.forEach { song ->
            Log.d("SongData", """
                Title: ${song["title"]}
                Artist: ${song["artistName"]}
                LuotNghe: ${song["luotNghe"]} (${song["luotNghe"]?.javaClass?.simpleName})
                Image: ${song["imageUrl"]}
                ======================
            """.trimIndent())
        }
    }
}