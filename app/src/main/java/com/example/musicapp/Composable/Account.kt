package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R

@Composable
fun FeatureItem(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        ) {
            Text(text = icon, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Cá nhân", color = Color.Black, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Row(   modifier = Modifier.padding(end = 10.dp)) {
            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black,   modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(10.dp))
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(10.dp))
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
fun ProfileHeader(name: String, plan: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier // <- Gán ở đây
    ) {
        Image(
            painter = painterResource(id = R.drawable.jack),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = name, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = plan, color = Color.Gray)
        }
    }
}




@Composable
fun ServicesSection(
    modifier: Modifier = Modifier,
    serviceTitle: String,
    serviceItems: List<Pair<ImageVector, String>>,
    personalTitle: String,
    personalItems: List<Pair<ImageVector, String>>,
    securityTitle: String,
    securityItems: List<Pair<ImageVector, String>>
) {
    Column(modifier = modifier) {
        Text(serviceTitle, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        serviceItems.forEach {
            ServiceItem(icon = it.first, label = it.second)

        }

        Spacer(Modifier.height(30.dp))

        Text(personalTitle, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        personalItems.forEach {
            ServiceItem(icon = it.first, label = it.second)
        }
        Spacer(Modifier.height(30.dp))

        Text(securityTitle, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        securityItems.forEach {
            ServiceItem(icon = it.first, label = it.second)
            Spacer(Modifier.height(3.dp))
        }
    }
}

@Composable
fun ServiceItem(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 5.dp)) {
        Icon(icon, contentDescription = null, tint = Color.DarkGray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = Color.DarkGray)
    }
}





