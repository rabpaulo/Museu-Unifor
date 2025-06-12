package com.example.mobile.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

// Function to resize the Bitmap
fun resizeBitmap(source: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val aspectRatio = source.width.toFloat() / source.height.toFloat()
    val targetWidth: Int
    val targetHeight: Int

    if (aspectRatio > 1) {
        targetWidth = maxWidth
        targetHeight = (maxWidth / aspectRatio).toInt()
    } else {
        targetHeight = maxHeight
        targetWidth = (maxHeight * aspectRatio).toInt()
    }

    return Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true)
}

// Function to convert Bitmap to Base64 string with compression
fun bitmapToBase64(bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 80): String {
    val resizedBitmap = resizeBitmap(bitmap, maxWidth = 800, maxHeight = 800) // Resize to 800x800 or smaller
    val byteArrayOutputStream = ByteArrayOutputStream()
    // Compress the Bitmap with the specified format and quality
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
    return try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@Composable
fun SelectableImage(
    labelName: String,
    context: Context,
    onImageSelected: (String) -> Unit // Callback for the Base64 string
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to pick image
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = labelName)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display selected image and handle conversion
        selectedImageUri?.let {
            uri ->
            val bitmap = uriToBitmap(context, uri)
            if (bitmap != null) {
                base64String = bitmapToBase64(bitmap)
                onImageSelected(base64String) // Pass the Base64 string back via the callback

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Image successfully converted to Base64!")
            } else {
                Text(text = "Failed to convert image.")
            }
        }
    }
}
@Composable
fun ImagePicker(context: Context, labelName:String): String {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf("") }
    var uploadStatus by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = labelName)
        }

        selectedImageUri?.let { uri ->
            val bitmap = uriToBitmap(context, uri)
            if (bitmap != null) {
                base64String = bitmapToBase64(bitmap)
                Text(text = "Image converted to Base64!")
            } else {
                Text(text = "Failed to convert image.")
            }
        }

        uploadStatus?.let {
            Text(text = it)
        }
    }
    return base64String
}