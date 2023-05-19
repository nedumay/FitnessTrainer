package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.BeginnerDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("3a592573-918d-49cd-8cc8-74ec74b9572d")
    suspend fun getBeginnerInfo(): BeginnerDto
}