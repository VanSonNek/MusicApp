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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.tooling.preview.Preview
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
        fontSize = 40.sp, // 👉 tăng kích thước chữ tại đây
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
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldHeight: Dp = 300.dp,
) {
    val lightWhite = Color.White.copy(alpha = 0.8f)

    Box(
        modifier = Modifier
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = lightWhite) },
            modifier = Modifier
                .width(textFieldHeight),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = lightWhite,
                unfocusedIndicatorColor = lightWhite,
                cursorColor = lightWhite,
            )
        )
    }
}



@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(1.dp)) {
        InputField(
            label = "Tên tài khoản",
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        InputField(
            label = "Mật khẩu",
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            },
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val desc = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = desc, tint = Color.White)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
}
@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(1.dp)) {
        InputField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                // Đặt icon bên ngoài TextField, ngoài đường gạch dưới
                Icon(Icons.Filled.Email, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            }
        )


    }
}
@OptIn(ExperimentalTextApi::class)
@Composable
fun SignUpText(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    // Thêm tham số modifier
) {
    val text = buildAnnotatedString {
        pushStyle(SpanStyle(color = Color.White))
        append("Don't have an account? ")
        pushStyle(SpanStyle(color = Color.Yellow)) // Màu của chữ "Sign Up"
        withAnnotation(tag = "SIGN_UP", annotation = "signUp") {
            append("Sign Up")
        }
        pop()
    }

    // Hiển thị văn bản với liên kết
    ClickableText(
        text = text,
        onClick = { offset ->
            text.getStringAnnotations(tag = "SIGN_UP", start = offset, end = offset)
                .firstOrNull()?.let {
                    if (it.item == "signUp") {
                        onSignUpClick() // Gọi callback khi nhấn vào "Sign Up"
                    }
                }
        },
        modifier = modifier // Dùng modifier từ tham số truyền vào
    )
}

//@Preview
//@Composable
//fun SignUpTextPreview() {
//    SignUpText(onSignUpClick = {})
//}
//
//
//@Preview
//@Composable
//fun SignUpTextPreview2() {
//    SignInScreen(modifier = Modifier)
//}



