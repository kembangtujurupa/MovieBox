package com.majorik.moviebox.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class CredentialsPrefsManager(context: Context) {
    companion object {
        const val SP_NAME = "moviebox"

        const val TMDB_LOGIN_STATUS = "tmdb_login_status"

        const val TMDB_SESSION_ID = "tmdb_session_id"

        const val TMDB_GUEST_SESSION_ID = "tmdb_guest_session_id"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun saveTmdbSession(isLogged: Boolean, sessionID: String?) {
        sharedPrefs.edit {
            putBoolean(TMDB_LOGIN_STATUS, isLogged)
            putString(TMDB_SESSION_ID, sessionID)
        }
    }

    fun saveLoginStatus(isLogged: Boolean) {
        sharedPrefs.edit { putBoolean(TMDB_LOGIN_STATUS, isLogged) }
    }

    fun saveTmdbSessionID(sessionID: String?) {
        sharedPrefs.edit { putString(TMDB_SESSION_ID, sessionID) }
    }

    fun saveGuestSessionID(guestSessionID: String?) {
        sharedPrefs.edit {
            putString(TMDB_GUEST_SESSION_ID, guestSessionID)
        }
    }

    fun getTmdbLoggedStatus(): Boolean = sharedPrefs.getBoolean(TMDB_LOGIN_STATUS, false)

    fun getTmdbSessionID(): String? = sharedPrefs.getString(TMDB_SESSION_ID, null)

    fun getTmdbGuestSessionID(): String? = sharedPrefs.getString(TMDB_GUEST_SESSION_ID, null)

    fun clearAll() {
        sharedPrefs.edit().clear().apply()
    }
}
