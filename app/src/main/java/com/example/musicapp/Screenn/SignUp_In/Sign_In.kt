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
import com.example.musicapp.Screen.Screen

@Composable
fun Sign_in(navController: NavController){
Surface(modifier = Modifier.fillMaxSize()) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            ) {

        val(img , text_signin, email_pass, forget, button, sign_text ) =createRefs()
        BackgroundImage(
            imageResId = R.drawable.jackandvirut,
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
            text = "SIGN IN",
            modifier = Modifier
                .constrainAs(text_signin){
                    top.linkTo(parent.top, margin = 100.dp)
                    start.linkTo(parent.start, margin = 40.dp)
                }
        )
        SignInScreen(
            modifier = Modifier
                .constrainAs(email_pass){
                    top.linkTo(text_signin.bottom, margin = 70.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
        )
        MyButton(
            onClick = {navController.navigate(Screen.YeuThich.route)},
            text = "SIGN IN",
            backgroundColor = Color(0xFF9CFF00),
            width = 300.dp,
            height = 55.dp,
            fontSize = 16.sp,
            textColor = Color.Black,
            cornerRadius = 5.dp,
            modifier = Modifier
                .constrainAs(button){
                    top.linkTo(email_pass.bottom, margin = 100.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
        )
        SignUpText(
            onSignUpClick = {  navController.navigate(Screen.Sign_Up.route)},
            modifier = Modifier
                .constrainAs(sign_text){
                    top.linkTo(button.bottom, margin = 70.dp)
                    start.linkTo(parent.start, margin = 1.dp)
                    end.linkTo(parent.end)
                }
        )

    }

}
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewSignIn() {
//    val navController = rememberNavController()
//    Sign_in(navController = navController)
//}
