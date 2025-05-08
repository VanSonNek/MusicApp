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
    private val songsRef = database.getReference("Songs") // Tham chiếu đến bảng "Songs"
    private val artistsRef = database.getReference("artists") // Tham chiếu đến bảng "artists"

    var songsList by mutableStateOf<List<Map<String, Any>>>(emptyList())  // Dữ liệu bài hát dạng Map
        private set

    var artistsMap by mutableStateOf<Map<String, Map<String, Any>>>(emptyMap())  // Dữ liệu nghệ sĩ dạng Map
        private set

    init {
        loadSongsFromFirebase()
        loadArtistsFromFirebase()
    }

    // Hàm để lấy tất cả các nghệ sĩ từ Firebase
    private fun loadArtistsFromFirebase() {
        artistsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val artists = mutableMapOf<String, Map<String, Any>>()
                for (childSnapshot in snapshot.children) {
                    val artistData = childSnapshot.value as? Map<String, Any>
                    artistData?.let { artists[childSnapshot.key ?: ""] = it }
                }
                artistsMap = artists
                Log.d("Firebase", "Danh sách nghệ sĩ đã được tải: $artists")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Lỗi khi tải dữ liệu nghệ sĩ: ${error.message}")
            }
        })
    }

    // Hàm để lấy tất cả các bài hát từ Firebase
    private fun loadSongsFromFirebase() {
        songsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val songs = mutableListOf<Map<String, Any>>()
                for (childSnapshot in snapshot.children) {
                    val songData = childSnapshot.value as? Map<String, Any>
                    songData?.let { songs.add(it) }
                }
                // Thêm tên nghệ sĩ vào danh sách bài hát
                val songsWithArtists = songs.map { song ->
                    val artistId = song["artistId"] as? String ?: ""
                    val artistName = artistsMap[artistId]?.get("name") as? String ?: "Không có tên nghệ sĩ"
                    song + ("artistName" to artistName)
                }
                songsList = songsWithArtists
                Log.d("Firebase", "Danh sách bài hát đã được tải: $songsWithArtists")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Lỗi khi tải dữ liệu bài hát: ${error.message}")
            }
        })
    }
}
