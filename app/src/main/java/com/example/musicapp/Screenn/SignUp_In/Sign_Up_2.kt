package com.example.musicapp.Screenn.SignUp_In

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.musicapp.Composable.*

import com.example.musicapp.R
import com.example.musicapp.Screen.Screen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

@Composable
fun Sign_Up_2(navController: NavController) {
    val auth = Firebase.auth // Khởi tạo Firebase Auth

    // Lấy email từ savedStateHandle
    val initialEmail = navController.previousBackStackEntry?.savedStateHandle?.get<String>("email") ?: ""
    val emailState = remember { mutableStateOf(initialEmail) }
    val passwordState = remember { mutableStateOf("") }
    val rePasswordState = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (img, text_signin, pass, button) = createRefs()

            BackgroundImage(
                imageResId = R.drawable.anh4_signup_in,
                modifier = Modifier
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxSize()
            )

            Text_Sign(
                text = "SIGN UP",
                modifier = Modifier.constrainAs(text_signin) {
                    top.linkTo(parent.top, margin = 100.dp)
                    start.linkTo(parent.start, margin = 40.dp)
                }
            )

            SignInScreen_2(
                modifier = Modifier.constrainAs(pass) {
                    top.linkTo(text_signin.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 40.dp)
                },
                passwordState = passwordState,
                rePasswordState = rePasswordState
            )

            MyButton(
                onClick = {
                    val email = emailState.value
                    val password = passwordState.value
                    val confirmPassword = rePasswordState.value

                    if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userId = auth.currentUser?.uid
                                    // Lưu thông tin email vào Firebase mà không sử dụng đối tượng User
                                    userId?.let { uid ->
                                        FirebaseDatabase.getInstance()
                                            .getReference("users")
                                            .child(uid)
                                            .child("email") // Lưu email trực tiếp
                                            .setValue(email)
                                            .addOnSuccessListener {
                                                navController.navigate(Screen.Home.route)
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("SignUp", "Lỗi lưu user data: ${e.message}")
                                            }
                                    }
                                } else {
                                    Log.e("SignUp", "Lỗi đăng ký: ${task.exception?.message}")
                                }
                            }
                    } else {
                        Log.e("SignUp", "Email rỗng hoặc mật khẩu không khớp")
                    }
                },
                text = "SIGN UP",
                backgroundColor = Color(0xFF9CFF00),
                width = 300.dp,
                height = 55.dp,
                fontSize = 16.sp,
                textColor = Color.Black,
                cornerRadius = 5.dp,
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(pass.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}
