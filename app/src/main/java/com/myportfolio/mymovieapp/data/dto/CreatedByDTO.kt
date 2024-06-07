package com.myportfolio.mymovieapp.data.dto

import com.google.gson.annotations.SerializedName

data class CreatedByDTO(
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("profile_path")
    val profilePath: Any
)