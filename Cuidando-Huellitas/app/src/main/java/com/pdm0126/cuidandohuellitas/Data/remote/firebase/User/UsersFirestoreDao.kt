package com.pdm0126.cuidandohuellitas.Data.remote.firebase.User

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UsersFirestoreDao {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun saveUser(name: String, avatar: String, email: String) {
        val uid = auth.currentUser?.uid ?: return
        val userMap = hashMapOf(
            "uid" to uid,
            "name" to name,
            "avatar" to avatar,
            "email" to email
        )
        db.collection("users").document(uid).set(userMap).await()
    }

    suspend fun getUser(): Map<String, Any>? {
        val uid = auth.currentUser?.uid ?: return null
        val doc = db.collection("users").document(uid).get().await()
        return if (doc.exists()) doc.data else null
    }

    suspend fun updateAvatar(avatar: String) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).update("avatar", avatar).await()
    }

    suspend fun countPetsByUser(): Int {
        val uid = auth.currentUser?.uid ?: return 0
        return db.collection("pets").whereEqualTo("userId", uid).get().await().size()
    }
}