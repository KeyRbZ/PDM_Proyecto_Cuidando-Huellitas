package com.pdm0126.cuidandohuellitas.Data.remote.firebase.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import android.util.Base64

class StorageDao {

    suspend fun uploadPhoto(uri: Uri, context: Context): String {
        return withContext(Dispatchers.IO) {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

            // redimensiona a 200x200 para foto de perfil
            val resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true)

            val stream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 40, stream)

            Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        }
    }
}