package com.example.testproject.app.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Nedumayy (Samim)
 * Create ApiFactory for work with Api
 */

object ApiFactory {

    private const val BASE_URL = "https://api.myjson.online/v1/records/"


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}