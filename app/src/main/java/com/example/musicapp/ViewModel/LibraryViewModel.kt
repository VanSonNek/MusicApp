import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.Model.Playlist
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LibraryViewModel : ViewModel() {
    var playlistState by mutableStateOf<List<Playlist>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    private val firestore = Firebase.firestore
    private val playlistCollection = firestore.collection("playlists")

    init {
        getPlaylists()
    }

    fun getPlaylists() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // Láº¥y dá»¯ liá»‡u tá»« Firestore
                val querySnapshot = playlistCollection.get().await()
                val playlists = querySnapshot.documents.mapNotNull { document ->
                    // Láº¥y dá»¯ liá»‡u cÆ¡ báº£n tá»« document
                    val id = document.id
                    val title = document.getString("title") ?: ""
                    val description = document.getString("description") ?: ""
                    val coverImageUrl = document.getString("coverImageUrl") ?: ""
                    val createdAt = document.getString("createdAt") ?: ""
                    val createdBy = document.getString("createdBy") ?: ""
                    val isPublic = document.getBoolean("isPublic") ?: false
                    val totalSongs = document.getLong("totalSongs")?.toInt() ?: 0

                    Playlist(
                        id = id,
                        title = title,
                        description = description,
                        coverImageUrl = coverImageUrl,
                        createdAt = createdAt,
                        createdBy = createdBy,
                        isPublic = isPublic,
                        totalSongs = totalSongs
                    )
                }

                playlistState = playlists
            } catch (e: Exception) {
                errorMessage = "KhĂ´ng thá»ƒ táº£i danh sĂ¡ch phĂ¡t: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Láº¯ng nghe thay Ä‘á»•i tá»« Firestore theo thá»i gian thá»±c
    fun listenToPlaylists() {
        playlistCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                errorMessage = "Lá»—i khi láº¯ng nghe thay Ä‘á»•i: ${error.message}"
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val playlists = snapshot.documents.mapNotNull { document ->
                    val id = document.id
                    val title = document.getString("title") ?: ""
                    val description = document.getString("description") ?: ""
                    val coverImageUrl = document.getString("coverImageUrl") ?: ""
                    val createdAt = document.getString("createdAt") ?: ""
                    val createdBy = document.getString("createdBy") ?: ""
                    val isPublic = document.getBoolean("isPublic") ?: false
                    val totalSongs = document.getLong("totalSongs")?.toInt() ?: 0

                    Playlist(
                        id = id,
                        title = title,
                        description = description,
                        coverImageUrl = coverImageUrl,
                        createdAt = createdAt,
                        createdBy = createdBy,
                        isPublic = isPublic,
                        totalSongs = totalSongs
                    )
                }
                playlistState = playlists
            }
        }
    }
}