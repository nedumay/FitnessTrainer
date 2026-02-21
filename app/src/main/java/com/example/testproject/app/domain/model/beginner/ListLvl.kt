package com.example.testproject.app.domain.model.beginner

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ListLvl(
    @SerializedName("list_lvl")
    var listLvl: List<Workout>

)