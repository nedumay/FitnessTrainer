package com.example.testproject.app.data.network.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class ApiResponse(
    @SerializedName("list_lvl")
    val listLvl: List<ListLvlDto> = emptyList()
) : Parcelable

