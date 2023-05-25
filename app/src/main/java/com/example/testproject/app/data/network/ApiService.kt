package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ListLvlDto
import retrofit2.http.GET

interface ApiService {

    // Beginner exercise API
    @GET("1b5ea983-7dd7-40c9-889e-c36ac835d2c7")
    suspend fun getBeginnerInfo(): ListLvlDto

    // Continuing exercise API
    @GET("9454d6a3-cc2a-49a1-92f3-bb785af3a27d")
    suspend fun getContinuingInfo(): ListLvlDto

    //Advanced exercise API
    @GET("eee2b1ce-1fd7-4b4c-8156-eda8a4ea16d7")
    suspend fun getAdvancedInfo(): ListLvlDto
}