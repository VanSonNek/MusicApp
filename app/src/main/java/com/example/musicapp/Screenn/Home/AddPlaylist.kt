package com.example.musicapp.Screenn.Home

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.R
import com.example.musicapp.ViewModel.PlaylistViewModel
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylist(navController: NavController) {
    val context = LocalContext.current
    val playlistViewModel: PlaylistViewModel = viewModel()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunghiepchuong),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Tạo playlist mới",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Playlist Cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.jack),
                    contentDescription = "Add Photo",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tên playlist", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DB954),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Mô tả", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1DB954),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Công khai",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = isPublic,
                onCheckedChange = { isPublic = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF1DB954),
                    checkedTrackColor = Color(0xFF1DB954).copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (title.isBlank()) {
                    Toast.makeText(context, "Vui lòng nhập tên playlist", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isUploading = true

                // Upload ảnh nếu có
                if (imageUri != null) {
                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("playlist_covers/${UUID.randomUUID()}")

                    imageRef.putFile(imageUri!!)
                        .addOnSuccessListener {
                            imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                createPlaylist(
                                    playlistViewModel,
                                    title,
                                    description,
                                    downloadUrl.toString(),
                                    isPublic,
                                    navController,
                                    context
                                )
                                isUploading = false
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Lỗi khi tải ảnh lên", Toast.LENGTH_SHORT).show()
                            isUploading = false
                        }
                } else {
                    // Sử dụng ảnh mặc định nếu không chọn ảnh
                    createPlaylist(
                        playlistViewModel,
                        title,
                        description,
                        "https://i.imgur.com/zhA67L9.png", // Ảnh mặc định
                        isPublic,
                        navController,
                        context
                    )
                    isUploading = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1DB954)
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !isUploading
        ) {
            if (isUploading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Tạo playlist",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun createPlaylist(
    playlistViewModel: PlaylistViewModel,
    title: String,
    description: String,
    coverImageUrl: String,
    isPublic: Boolean,
    navController: NavController,
    context: android.content.Context
) {
    playlistViewModel.createPlaylist(
        title = title,
        description = description,
        coverImageUrl = coverImageUrl,
        isPublic = isPublic
    ) { success, message ->
        if (success) {
            Toast.makeText(context, "Đã tạo playlist thành công", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        } else {
            Toast.makeText(context, "Lỗi: $message", Toast.LENGTH_SHORT).show()
        }
    }
}
