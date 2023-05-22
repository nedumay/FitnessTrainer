package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.BeginnerDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("d8c1a02d-cb90-43ee-bf93-d07d915f41fa")
    suspend fun getBeginnerInfo(): BeginnerDto
}