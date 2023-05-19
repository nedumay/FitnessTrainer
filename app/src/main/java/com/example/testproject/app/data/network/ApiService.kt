package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.BeginnerDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("2851420f-ea0f-4f4f-9247-6e780bb94f18")
    suspend fun getBeginnerInfo(): BeginnerDto
}