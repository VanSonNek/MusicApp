package com.example.musicapp.Screenn.SignUp_In

import android.util.Log
import android.util.Patterns
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
import com.example.musicapp.Composable.BackgroundImage
import com.example.musicapp.Composable.MyButton
import com.example.musicapp.Composable.SignInScreen
import com.example.musicapp.Composable.SignUpScreen
import com.example.musicapp.Composable.SignUpText
import com.example.musicapp.Composable.Text_Sign
import com.example.musicapp.Model.User
import com.example.musicapp.R
import com.example.musicapp.Screen.Screen
import com.google.firebase.database.FirebaseDatabase

@Composable
fun Sign_Up(navController: NavController){
    val emailState = remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val(img , text_signin, email,  button) =createRefs()
            BackgroundImage(
                imageResId = R.drawable.anh4_signup_in,
                modifier = Modifier
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxSize() // Đảm bảo ảnh chiếm toàn bộ diện tích
            )
            Text_Sign(
                text = "SIGN UP",
                modifier = Modifier
                    .constrainAs(text_signin){
                        top.linkTo(parent.top, margin = 100.dp)
                        start.linkTo(parent.start, margin = 40.dp)
                    }
            )
            SignUpScreen(
                emailState = emailState,
                modifier = Modifier.constrainAs(email) {
                    top.linkTo(text_signin.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 40.dp)
                }
            )
            MyButton(
                onClick = {
                    val email = emailState.value
                    Log.d("SignUp", "Email nhập vào: $email")
                    if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        // Lưu email vào savedStateHandle để truyền qua màn hình tiếp theo
                        navController.currentBackStackEntry?.savedStateHandle?.set("email", email)
                        navController.navigate(Screen.Sign_Up_2.route)
                    } else {
                        Log.e("SignUp", "Email không hợp lệ hoặc trống")
                    }


                },
                text = "SIGN UP",
                backgroundColor = Color(0xFF9CFF00),
                width = 300.dp,
                height = 55.dp,
                fontSize = 16.sp,
                textColor = Color.Black,
                cornerRadius = 5.dp,
                modifier = Modifier
                    .constrainAs(button){
                        top.linkTo(email.bottom, margin = 100.dp)
                        start.linkTo(parent.start, margin = 1.dp)
                        end.linkTo(parent.end)
                    }
            )

        }
    }

}
