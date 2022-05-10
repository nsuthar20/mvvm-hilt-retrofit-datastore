package com.apps.mvvm.data.network.retrofit.api

import com.apps.mvvm.data.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {
    @POST("login")
    suspend fun doLogin(@Body body: HashMap<String?, Any?>?): AuthResponse
}