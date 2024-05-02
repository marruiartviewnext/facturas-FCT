package com.marinaruiz.facturas_fct.data.network.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.marinaruiz.facturas_fct.data.network.firebase.model.LoginResult
import kotlinx.coroutines.tasks.await

class AuthService(private val firebase: FirebaseService = FirebaseService.getInstance()) {

    companion object {

        private const val TAG = "VIEWNEXT AuthService"

        private var _INSTANCE: AuthService? = null

        fun getInstance(): AuthService {
            return _INSTANCE ?: AuthService()
        }
    }

    private val _uid = MutableLiveData<String?>()
    val uid: LiveData<String?>
        get() = _uid

    init {
        firebase.client.auth.addAuthStateListener { auth ->
            Log.i(TAG, "Listening uid: ${auth.currentUser?.uid}")
            _uid.value = auth.currentUser?.uid
        }
    }

    /**
     * Performs user authentication using the provided email and password.
     *
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A LoginResult object representing the result of the authentication attempt.
     *         This result encapsulates either a successful authentication or an error.
     * @throws Exception If an error occurs during the authentication process.
     */
    suspend fun login(email: String, password: String): LoginResult = runCatching {
        firebase.client.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    /**
     * Creates a new user account with the provided email and password.
     *
     * @param email    The email address for the new user account.
     * @param password The password for the new user account.
     * @return An AuthResult object representing the result of the account creation attempt.
     *         This result encapsulates either a successful creation or an error.
     * @throws Exception If an error occurs during the account creation process.
     */
    suspend fun createAccount(email: String, password: String): AuthResult? {
        return firebase.client.auth.createUserWithEmailAndPassword(email, password).await()
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebase.client.auth.currentUser
    }

    fun logout() = runCatching {
        firebase.logout()
    }

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResult.Error
        else -> {
            checkNotNull(result.user)
            LoginResult.Success
        }
    }

    fun resetPassword(email: String): Task<Void> {
        return firebase.resetPassword(email)
    }

}