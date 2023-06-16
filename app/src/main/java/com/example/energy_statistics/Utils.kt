package com.example.energy_statistics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class Utils {

    companion object {
        fun convertBitmaptoBase64(bitmap: Bitmap): String? {

            val output = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output)

            val byteArrayImage = output.toByteArray()
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT) ?: ""
        }

        fun convertBase64toBitmap(encodedImage: String): Bitmap? {
            val imageBytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertStringToDate(value: String, format: String): LocalDate? {
            val format = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
            return LocalDate.parse(value, format)
        }
    }

}