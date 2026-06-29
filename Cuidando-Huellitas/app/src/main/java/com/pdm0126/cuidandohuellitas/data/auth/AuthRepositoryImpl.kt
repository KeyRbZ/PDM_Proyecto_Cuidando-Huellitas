package com.pdm0126.cuidandohuellitas.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> =
        try { firebaseAuth.signInWithEmailAndPassword(email, password).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(e) }

    override suspend fun register(email: String, password: String): Result<Unit> =
        try { firebaseAuth.createUserWithEmailAndPassword(email, password).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(e) }

    override suspend fun sendPasswordReset(email: String): Result<Unit> =
        try { firebaseAuth.sendPasswordResetEmail(email).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(e) }

    override fun logout() = firebaseAuth.signOut()

    override fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
}