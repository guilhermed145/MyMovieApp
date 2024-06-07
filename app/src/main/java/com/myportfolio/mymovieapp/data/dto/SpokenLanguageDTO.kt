package com.myportfolio.mymovieapp.data.dto

import com.google.gson.annotations.SerializedName

data class SpokenLanguageDTO(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String
)