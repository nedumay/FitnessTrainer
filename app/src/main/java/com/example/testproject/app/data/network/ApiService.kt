package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ListLvlDto
import retrofit2.http.GET

interface ApiService {

    // Beginner exercise API
    @GET("4e4399ba-7eef-4fdc-b877-f457037e0fb9")
    suspend fun getBeginnerInfo(): ListLvlDto

    // Continuing exercise API
    @GET("f5732aed-48f0-450c-a9f9-24bdb06c5b7a")
    suspend fun getContinuingInfo(): ListLvlDto

    //Advanced exercise API
    @GET("fbf0dbb0-57d1-4d43-bd56-c4e1f7fd4e29")
    suspend fun getAdvancedInfo(): ListLvlDto
}