package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.Screen.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.Dp


@Composable
fun BackgroundImage(modifier: Modifier = Modifier, imageResId : Int ) {
    Image(
        painter = painterResource(id = imageResId), // Thay thế R.drawable.background bằng ảnh của bạn
        contentDescription = null,
        modifier = modifier
            .fillMaxSize() // Chiếm toàn bộ không gian
            .graphicsLayer { alpha = 0.8f }, // Điều chỉnh độ mờ (nếu cần)
        contentScale = ContentScale.FillBounds
    )
}
@Composable
fun Text_Sign(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 35.sp, // 👉 tăng kích thước chữ tại đây
        color = Color.White,


    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    textFieldHeight: Dp = 300.dp, // Kích thước chiều cao của TextField

) {
    // Màu trắng nhẹ cho các thành phần
    val lightWhite = Color.White.copy(alpha = 0.8f)

    Box(
        modifier = Modifier
    ) {
        // TextField
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = lightWhite) },
            modifier = Modifier
                .width(textFieldHeight), // Thiết lập chiều cao của TextField
            leadingIcon = leadingIcon,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = lightWhite,
                unfocusedIndicatorColor = lightWhite,
                cursorColor = lightWhite
            )
        )
    }
}


@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        InputField(
            label = "Tên tài khoản",
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                // Đặt icon bên ngoài TextField, ngoài đường gạch dưới
                Icon(Icons.Filled.Email, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Mật khẩu",
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                // Đặt icon bên ngoài TextField, ngoài đường gạch dưới
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            }
        )
    }
}




