package com.example.jsonplaceholdermvvm.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wraps EncryptedSharedPreferences (backed by the Android Keystore) to store
 * the logged-in session securely on device. The Android Keystore means the
 * actual encryption key never leaves secure hardware — even a rooted device
 * reading the raw prefs file on disk sees only ciphertext.
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveSession(mobileNumber: String, fullName: String, authToken: String) {
        prefs.edit()
            .putString(KEY_MOBILE_NUMBER, mobileNumber)
            .putString(KEY_FULL_NAME, fullName)
            .putString(KEY_AUTH_TOKEN, authToken)
            .apply()
    }

    fun getAuthToken(): String? = prefs.getString(KEY_AUTH_TOKEN, null)
    fun getMobileNumber(): String? = prefs.getString(KEY_MOBILE_NUMBER, null)
    fun getFullName(): String? = prefs.getString(KEY_FULL_NAME, null)

    fun isLoggedIn(): Boolean = getAuthToken() != null

    fun setBiometricEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply()
    }

    fun isBiometricEnabled(): Boolean = prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_MOBILE_NUMBER = "mobile_number"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
    }
}