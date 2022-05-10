package com.apps.mvvm.data.network.retrofit.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class BackendInterceptor @Inject constructor(
    @Named("auth_key") private val auth_key: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "bearer $auth_key")
                .build()
        )
    }
}