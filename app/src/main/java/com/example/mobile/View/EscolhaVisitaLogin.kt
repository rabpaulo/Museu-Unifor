package com.example.mobile.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.Controller.Screen
import com.example.mobile.Utils.dynamicSpacerHeight
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon

@Composable
fun EscolhaVisitaLogin(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomCenter)
        ) {
            OutlinedButton(
                onClick = { navController.navigate(Screen.PrincipalVisitante.route) },
                Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(40.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp) // Adjust padding
            ) {
                Text(
                    stringResource(R.string.botao_visitante),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(dynamicSpacerHeight(0.019f)))
            
            OutlinedButton(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                shape = RoundedCornerShape(42.dp),

                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null, // Decorative
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp)) // Space between icon and text
                    Text(
                        stringResource(R.string.botao_adm),
                        color = Color.White
                    )
                }
            }

            // Spacer for bottom padding
            Spacer(modifier = Modifier.height(dynamicSpacerHeight(0.14f)))
        }
    }
}

