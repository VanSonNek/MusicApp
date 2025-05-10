package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaylistItem(
    title: String,
    artist: String,
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiá»ƒn thá»‹ áº£nh playlist
        Card(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Playlist image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Hiá»ƒn thá»‹ thĂ´ng tin playlist
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = artist,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // NĂºt menu
        IconButton(onClick = { /* Xá»­ lĂ½ khi nháº¥n nĂºt menu */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Gray
            )
        }
    }
}