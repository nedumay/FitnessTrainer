package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.BeginnerDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("5739db8d-eb9b-456b-a8fd-9d9707fbf698")
    suspend fun getBeginnerInfo(): BeginnerDto
}