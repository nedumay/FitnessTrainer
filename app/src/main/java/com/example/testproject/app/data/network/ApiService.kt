package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ListLvlDto
import retrofit2.http.GET

interface ApiService {

    // Beginner exercise API
    @GET("6ad2502f-fdf6-4abc-b0d9-39203e57d47b")
    suspend fun getBeginnerInfo(): ListLvlDto

    // Continuing exercise API
    @GET("6ae172a3-9979-4d10-9116-272413b87404")
    suspend fun getContinuingInfo(): ListLvlDto

    //Advanced exercise API
    @GET("383b441a-98d7-46de-a83c-a33e2c6fdd69")
    suspend fun getAdvancedInfo(): ListLvlDto
}