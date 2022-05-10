package com.apps.mvvm.data.datastore

import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferenceKey {
        val authKey = preferencesKey<String>("auth_key")
    }

    suspend fun saveAuthKey(authToken: String) {
        dataStore.edit { preference ->
            preference[PreferenceKey.authKey] = authToken
        }
    }

    val authKey: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKey.authKey] ?: "none"
        }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}