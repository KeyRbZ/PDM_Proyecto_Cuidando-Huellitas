package com.pdm0126.cuidandohuellitas.data.auth

import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import kotlinx.coroutines.tasks.await

class UsersFirestoreDao {
    private val db = Firebase.firestore

    suspend fun saveUser(uid: String, name: String, avatar: String, email: String) {
        val userMap = hashMapOf(
            "uid" to uid,
            "name" to name,
            "avatar" to avatar,
            "email" to email
        )
        db.collection("users")
            .document(uid)
            .set(userMap)
            .await()
    }

    suspend fun getUser(uid: String): Map<String, Any>? {
        val doc = db.collection("users").document(uid).get().await()
        return if (doc.exists()) doc.data else null
    }
}