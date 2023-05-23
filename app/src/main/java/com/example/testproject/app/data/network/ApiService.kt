package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ListLvlDto
import retrofit2.http.GET

interface ApiService {

    // Beginner exercise API
    @GET("1a87b2f5-0ff1-4ea9-ade7-a87e16c953fa")
    suspend fun getBeginnerInfo(): ListLvlDto

    // Continuing exercise API
    @GET("315426f0-e218-485b-a5b1-ece6b0361d9f")
    suspend fun getContinuingInfo(): ListLvlDto
}