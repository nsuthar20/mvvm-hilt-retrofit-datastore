package com.apps.mvvm.di

import com.apps.mvvm.BASE_URL
import com.apps.mvvm.data.datastore.DataPreferences
import com.apps.mvvm.data.network.retrofit.api.MyApi
import com.apps.mvvm.data.network.retrofit.interceptors.BackendInterceptor
import com.apps.mvvm.data.network.retrofit.interceptors.NoConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        backendInterceptor: BackendInterceptor,
        noConnectionInterceptor: NoConnectionInterceptor,
    ): okhttp3.Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(backendInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(noConnectionInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideMyApi(
        callFactory: okhttp3.Call.Factory
    ): MyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(callFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    @Provides
    @Named("auth_key")
    fun provideAuthToken(appPreferences: DataPreferences): String {
        return runBlocking { appPreferences.authKey.first() }
    }
}