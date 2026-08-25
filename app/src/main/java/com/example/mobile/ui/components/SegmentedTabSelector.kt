package com.example.mobile.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.util.poppinsFamily

@Composable
fun SegmentedTabSelector(
    selectedTab: String, // "Autores" or "Obras"
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            val isAutores = selectedTab == "Autores"
            val autoresBgColor by animateColorAsState(
                targetValue = if (isAutores) MaterialTheme.colorScheme.primary else Color.Transparent,
                label = "autoresBg"
            )
            val autoresTextColor by animateColorAsState(
                targetValue = if (isAutores) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "autoresText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(autoresBgColor)
                    .clickable { onTabSelected("Autores") }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Autores",
                    fontFamily = poppinsFamily,
                    fontWeight = if (isAutores) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp,
                    color = autoresTextColor,
                    textAlign = TextAlign.Center
                )
            }

            val isObras = selectedTab == "Obras"
            val obrasBgColor by animateColorAsState(
                targetValue = if (isObras) MaterialTheme.colorScheme.primary else Color.Transparent,
                label = "obrasBg"
            )
            val obrasTextColor by animateColorAsState(
                targetValue = if (isObras) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "obrasText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(obrasBgColor)
                    .clickable { onTabSelected("Obras") }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Obras",
                    fontFamily = poppinsFamily,
                    fontWeight = if (isObras) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp,
                    color = obrasTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
