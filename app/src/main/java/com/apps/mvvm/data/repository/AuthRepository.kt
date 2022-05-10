package com.apps.mvvm.data.repository

import com.apps.mvvm.data.datastore.DataPreferences
import com.apps.mvvm.data.network.retrofit.api.MyApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: MyApi,
    private val dataPreferences: DataPreferences
) : BaseRepository() {

    suspend fun doLogin(
        map: HashMap<String?, Any?>
    ) = safeApiCall {
        api.doLogin(map)
    }

    suspend fun saveAuthKey(authToken: String) {
        dataPreferences.saveAuthKey(authToken)
    }
}