package com.example.testproject.app.data.network.model

import com.google.gson.annotations.SerializedName


data class ApiResponse(
    @SerializedName("data") val data: ListLvlDto,
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("version") val version: Int
)

