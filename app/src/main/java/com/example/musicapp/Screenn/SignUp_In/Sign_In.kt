package com.example.musicapp.Screenn.SignUp_In

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.Composable.BackgroundImage
import com.example.musicapp.Composable.MyButton
import com.example.musicapp.Composable.SignInScreen
import com.example.musicapp.Composable.SignUpText
import com.example.musicapp.Composable.Text_Sign
import com.example.musicapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.musicapp.Screen.Screen
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
@Composable
fun Sign_in(navController: NavController) {
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (img, text_signin, email_pass, button, sign_text) = createRefs()

            BackgroundImage(
                imageResId = R.drawable.anh2_signup_in,
                modifier = Modifier.constrainAs(img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxSize()
            )

            Text_Sign(
                text = "SIGN IN",
                modifier = Modifier.constrainAs(text_signin) {
                    top.linkTo(parent.top, margin = 100.dp)
                    start.linkTo(parent.start, margin = 40.dp)
                }
            )

            SignInScreen(
                modifier = Modifier.constrainAs(email_pass) {
                    top.linkTo(text_signin.bottom, margin = 70.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                },
                username = email,
                password = password,
                onUsernameChange = { email = it },
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
            )

            MyButton(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Log.e("Login", "Email và mật khẩu không được để trống")
                        return@MyButton
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Log.e("Login", "Email không hợp lệ")
                        return@MyButton
                    }

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Không kiểm tra xác thực email nữa
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Sign_In.route) { inclusive = true }
                                }
                            } else {
                                when (task.exception) {
                                    is FirebaseAuthInvalidUserException ->
                                        Log.e("Login", "Tài khoản không tồn tại")
                                    is FirebaseAuthInvalidCredentialsException ->
                                        Log.e("Login", "Sai email hoặc mật khẩu")
                                    else ->
                                        Log.e("Login", "Lỗi đăng nhập: ${task.exception?.message}")
                                }
                            }
                        }
                },
                text = "SIGN IN",
                backgroundColor = Color(0xFF9CFF00),
                width = 300.dp,
                height = 55.dp,
                fontSize = 16.sp,
                textColor = Color.Black,
                cornerRadius = 5.dp,
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(email_pass.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
            )

            SignUpText(
                onSignUpClick = { navController.navigate(Screen.Sign_Up.route) },
                modifier = Modifier.constrainAs(sign_text) {
                    top.linkTo(button.bottom, margin = 70.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}
