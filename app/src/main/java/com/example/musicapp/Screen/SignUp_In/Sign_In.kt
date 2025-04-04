package com.example.musicapp.Screen.SignUp_In

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.musicapp.Composable.BackgroundImage
import com.example.musicapp.Composable.SignInScreen
import com.example.musicapp.Composable.Text_Sign
import com.example.musicapp.R

@Composable
fun Sign_in(){
Surface(modifier = Modifier.fillMaxSize()) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            ) {

        val(img , text_signin, email_pass, forget, button, do_not ) =createRefs()
        BackgroundImage(
            imageResId = R.drawable.imgtest,
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
                    top.linkTo(text_signin.bottom, margin = 10.dp)
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
//    Sign_in()
//}
