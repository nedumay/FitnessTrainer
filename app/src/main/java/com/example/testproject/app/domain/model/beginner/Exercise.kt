package com.example.testproject.app.domain.model.beginner

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("subtitle")
    var subtitle: String,
    @SerializedName("video")
    var video: String,
    @SerializedName("desc")
    var desc: String,
    @SerializedName("area")
    var area: String,
    @SerializedName("area_img")
    var areaImg: String
)
