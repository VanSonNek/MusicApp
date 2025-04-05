package com.example.musicapp.Composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String, // Văn bản hiển thị trong nút
    width: Dp , // Chiều rộng của nút
    height: Dp , // Chiều cao của nút
    fontSize: TextUnit , // Kích thước chữ
    backgroundColor: Color  , // Màu nền của nút
    textColor: Color, // Màu chữ của nút
    cornerRadius: Dp  // Góc bo tròn của nút
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width) // Chiều rộng của nút
            .height(height), // Chiều cao của nút
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor // Màu nền của nút
        ),
        shape = RoundedCornerShape(cornerRadius) // Góc bo tròn cho nút
    ) {
        Text(
            text = text, // Văn bản hiển thị trong nút
            fontSize = fontSize, // Kích thước chữ
            color = textColor // Màu chữ của nút
        )
    }
}

