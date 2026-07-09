package com.pdm0126.cuidandohuellitas.Data.remote.firebase.User

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import android.util.Log

class UsersFirestoreDao {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun saveUser(uid: String, name: String, avatar: String, email: String) {
        try {
            Log.d("UsersFirestoreDao", "Guardando usuario: $uid")
            val userMap = hashMapOf(
                "uid" to uid,
                "name" to name,
                "avatar" to avatar,
                "email" to email
            )
            db.collection("users").document(uid).set(userMap).await()
            Log.d("UsersFirestoreDao", "Usuario guardado exitosamente")
        } catch (e: Exception) {
            Log.e("UsersFirestoreDao", "Error guardando usuario: ${e.message}")
            throw e
        }
    }

    suspend fun getUser(): Map<String, Any>? {
        try {
            val uid = auth.currentUser?.uid ?: return null
            val doc = db.collection("users").document(uid).get().await()
            return if (doc.exists()) doc.data else null
        } catch (e: Exception) {
            Log.e("UsersFirestoreDao", "Error obteniendo usuario: ${e.message}")
            return null
        }
    }

    suspend fun updateAvatar(avatar: String) {
        try {
            val uid = auth.currentUser?.uid ?: return
            db.collection("users").document(uid).update("avatar", avatar).await()
            Log.d("UsersFirestoreDao", "Avatar actualizado")
        } catch (e: Exception) {
            Log.e("UsersFirestoreDao", "Error actualizando avatar: ${e.message}")
            throw e
        }
    }

    suspend fun countPetsByUser(): Int {
        return try {
            val uid = auth.currentUser?.uid ?: return 0
            val snapshot = db.collection("pets").whereEqualTo("userId", uid).get().await()
            snapshot.size()
        } catch (e: Exception) {
            Log.e("UsersFirestoreDao", "Error contando mascotas: ${e.message}")
            0
        }
    }
}