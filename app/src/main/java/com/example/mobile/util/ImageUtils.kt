package com.example.mobile.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun resizeBitmap(source: Bitmap, maxWidth: Int = 800, maxHeight: Int = 800): Bitmap {
        val aspectRatio = source.width.toFloat() / source.height.toFloat()
        val targetWidth: Int
        val targetHeight: Int

        if (aspectRatio > 1) {
            targetWidth = maxWidth
            targetHeight = (maxWidth / aspectRatio).toInt().coerceAtLeast(1)
        } else {
            targetHeight = maxHeight
            targetWidth = (maxHeight * aspectRatio).toInt().coerceAtLeast(1)
        }

        return Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true)
    }

    fun bitmapToBase64(
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        quality: Int = 80
    ): String {
        val resizedBitmap = resizeBitmap(bitmap, maxWidth = 800, maxHeight = 800)
        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(format, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        if (base64String.isBlank()) return null
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }

    fun getDrawableResByName(context: Context, name: String): Int? {
        val cleanName = name.removePrefix("drawable:").trim()
        val resId = context.resources.getIdentifier(cleanName, "drawable", context.packageName)
        return if (resId != 0) resId else null
    }
}

@Composable
fun MuseumImage(
    imageSource: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: Int = R.drawable.pfp
) {
    val context = LocalContext.current
    val bitmap = remember(imageSource) {
        ImageUtils.base64ToBitmap(imageSource)
    }
    val drawableRes = remember(imageSource) {
        ImageUtils.getDrawableResByName(context, imageSource)
    }

    when {
        bitmap != null -> {
            Image(
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        drawableRes != null -> {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        else -> {
            Image(
                painter = painterResource(id = placeholderRes),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
    }
}
