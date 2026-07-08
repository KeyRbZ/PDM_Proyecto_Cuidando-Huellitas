package com.pdm0126.cuidandohuellitas.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> =
        try { firebaseAuth.signInWithEmailAndPassword(email, password).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(Exception(mapFirebaseError(e))) }

    override suspend fun register(email: String, password: String): Result<Unit> =
        try { firebaseAuth.createUserWithEmailAndPassword(email, password).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(Exception(mapFirebaseError(e))) }

    override suspend fun sendPasswordReset(email: String): Result<Unit> =
        try { firebaseAuth.sendPasswordResetEmail(email).await(); Result.success(Unit) }
        catch (e: Exception) { Result.failure(Exception(mapFirebaseError(e))) }

    override fun logout() = firebaseAuth.signOut()

    override fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    private fun mapFirebaseError(e: Exception): String = when (e) {
        is FirebaseAuthInvalidCredentialsException -> "El correo o la contraseña no son válidos"
        is FirebaseAuthInvalidUserException -> "No existe una cuenta con ese correo"
        is FirebaseAuthUserCollisionException -> "Ya existe una cuenta registrada con ese correo"
        is FirebaseAuthWeakPasswordException -> "La contraseña es muy débil, usa al menos 6 caracteres"
        else -> "Ocurrió un error, intenta de nuevo"
    }
}