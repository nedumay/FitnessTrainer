package com.example.testproject.app.data.network.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ExerciseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("subtitle")
    val subtitle: String,

    @SerializedName("video")
    val video: String,

    @SerializedName("desc")
    val desc: String,

    @SerializedName("area")
    val area: String,

    @SerializedName("area_img")
    val areaImg: String
) : Parcelable
