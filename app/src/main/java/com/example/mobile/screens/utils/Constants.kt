package com.example.mobile.screens.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mobile.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
fun dynamicSpacerHeight(fraction: Float): Dp {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    return (screenHeightDp * fraction).dp
}


val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_thin, FontWeight.Thin)

)

@Composable
fun DifferentFonts(){
    Column(modifier  = Modifier.background(color = Color.White)){
        Text(text = "Poppins Black", fontFamily = poppinsFamily, fontWeight = FontWeight.Black)
        Text(text = "Poppins Light", fontFamily = poppinsFamily, fontWeight = FontWeight.Light)
        Text(text = "Poppins ExtraBold", fontFamily = poppinsFamily, fontWeight = FontWeight.ExtraBold)
        Text(text = "Poppins SemiBold", fontFamily = poppinsFamily, fontWeight = FontWeight.SemiBold)
        Text(text = "Poppins Medium", fontFamily = poppinsFamily, fontWeight = FontWeight.Medium)
        Text(text = "Poppins Regular", fontFamily = poppinsFamily, fontWeight = FontWeight.Normal)
        Text(text = "Poppins Regular", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
    }
}
