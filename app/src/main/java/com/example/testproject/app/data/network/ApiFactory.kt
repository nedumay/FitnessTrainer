package com.example.testproject.app.data.network

import com.example.testproject.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Nedumayy (Samim)
 * Объект api для получения данных с сервера
 *
 */

object ApiFactory {

    private const val BASE_URL = "https://api.myjson.online/v2/records/"

    // 1. Создаём логгер OkHttp
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY  // Логируем тело запроса/ответа
        } else {
            HttpLoggingInterceptor.Level.NONE  // В релизе отключаем
        }
    }

    // 2. Создаём OkHttpClient с интерцептором
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .connectTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    // 3. Создаём Retrofit с нашим OkHttpClient
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()

    // 4. Создаём сервис
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}