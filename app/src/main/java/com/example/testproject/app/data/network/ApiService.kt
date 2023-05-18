package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.BeginnerDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("771be8e0-fa5d-489a-80b6-c5c66463a64f")
    suspend fun getBeginnerInfo(): BeginnerDto
}