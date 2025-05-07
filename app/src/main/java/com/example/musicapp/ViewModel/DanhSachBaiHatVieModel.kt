package com.example.musicapp.ViewModel
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.musicapp.Model.BaiHat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DanhSachBaiHatVieModel : ViewModel() {
    private val database = Firebase.database
    private val bxhRef = database.getReference("DanhSachBaiHat")

    var songsList by mutableStateOf<List<BaiHat>>(emptyList())
        private set

    init {
        loadSongsFromFirebase()
    }

    private fun loadSongsFromFirebase() {
        bxhRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val songs = mutableListOf<BaiHat>()
                for (childSnapshot in snapshot.children) {
                    val song = childSnapshot.getValue(BaiHat::class.java)
                    song?.let { songs.add(it) }
                }
                songsList = songs.sortedBy { it.id }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })
    }

    fun themBaiHatAutoId(baiHat: BaiHat) {
        val key = bxhRef.push().key ?: return
        val baiHatCoId = baiHat.copy(id = key)
        bxhRef.child(key).setValue(baiHatCoId)
    }

}

@Composable
fun AddSongToFirebase(danhSachBaiHat: DanhSachBaiHatVieModel) {
    // Danh sách các bài hát cần thêm (không truyền id)
    val danhSach = listOf(
        BaiHat( title = "Lạc Trôi", artist = "Sơn Tùng M-TP", imageUrl = "https://i.imgur.com/0pUim0S.jpeg", luotNghe = 9),
        BaiHat( title = "Nơi Này Có Anh", artist = "Sơn Tùng M-TP", imageUrl = "https://i.imgur.com/0pUim0S.jpeg",luotNghe = 8),
        BaiHat( title = "Em Của Ngày Hôm Qua", artist = "Sơn Tùng M-TP", imageUrl = "https://i.imgur.com/0pUim0S.jpeg",luotNghe = 7),

    BaiHat( title = "Đừng làm trái tim anh đau", artist = "Sơn Tùng M-TP", imageUrl = "https://i.imgur.com/0pUim0S.jpeg",luotNghe = 6),
    BaiHat( title = "Cơn mưa ngang qua", artist = "Sơn Tùng M-TP", imageUrl = "https://i.imgur.com/0pUim0S.jpeg",luotNghe = 5)
    )

    Button(
        onClick = {
            for (baiHat in danhSach) {
                // Tạo bài hát với id tự động từ Firebase
                danhSachBaiHat.themBaiHatAutoId(baiHat)
                Log.d("Firebase", "Đã thêm bài hát: ${baiHat.title}")
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Thêm nhiều bài hát vào Firebase")
    }
}

