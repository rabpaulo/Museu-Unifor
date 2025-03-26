package com.example.mobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.navigation.Screen
import com.example.mobile.screens.utils.dynamicSpacerHeight
import com.example.mobile.screens.utils.poppinsFamily


@Composable
fun PrimeiraTela(navController: NavController){

    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)){
        Text(stringResource(R.string.splash_name),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Spacer(modifier = Modifier.height(dynamicSpacerHeight(0.04f)))

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
                .align(Alignment.CenterHorizontally)
                .clickable{
                    navController.navigate(Screen.EscolhaVisitaLogin.route)
                })

        }
    }

