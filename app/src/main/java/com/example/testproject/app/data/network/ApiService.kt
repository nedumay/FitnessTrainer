package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ListLvlDto
import retrofit2.http.GET

interface ApiService {

    // Beginner exercise API
    @GET("6cdda04a-2ace-4338-a4be-a93a09d0552f")
    suspend fun getBeginnerInfo(): ListLvlDto

    // Continuing exercise API
    @GET("7f19581d-524a-4585-84be-2a24f0ea4b33")
    suspend fun getContinuingInfo(): ListLvlDto

    //Advanced exercise API
    @GET("7dff7fce-8272-492b-b837-431fd2f2220a")
    suspend fun getAdvancedInfo(): ListLvlDto
}