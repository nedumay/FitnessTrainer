package com.example.testproject.app.domain.model.beginner

import com.google.gson.annotations.SerializedName

data class Workout(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("picture")
    var picture: String,
    @SerializedName("exercise")
    var exercise: List<Exercise>
)
