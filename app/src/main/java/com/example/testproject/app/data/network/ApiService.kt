package com.example.testproject.app.data.network

import com.example.testproject.app.data.network.model.ApiResponse
import com.example.testproject.app.data.network.model.ListLvlDto
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    // Beginner exercise API
    @GET("30becb7d-6e44-40a0-a42d-401ac21b3ce4")
    suspend fun getBeginnerInfo(): ApiResponse

    // Continuing exercise API
    @GET("0fb2cfd5-cc14-4463-91b6-48f78c4c2aff")
    suspend fun getContinuingInfo(): ApiResponse

    //Advanced exercise API
    @GET("9fd587a9-dc1c-4daf-ab78-9f127c4707b2")
    suspend fun getAdvancedInfo(): ApiResponse
}