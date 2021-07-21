package com.infinum.isa.playground.lecturesix.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiModule {

    private const val BASE_URL = "https://tv-shows.infinum.academy"

    private fun getOkhttp(interceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(interceptor)
        .build()

    fun getRetrofit(
        interceptor: Interceptor = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().build()
            )
        }
    ): ShowsApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(getOkhttp(interceptor))
            .build()
            .create(ShowsApiService::class.java)
}