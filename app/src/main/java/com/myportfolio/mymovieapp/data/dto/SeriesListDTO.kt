package com.myportfolio.mymovieapp.data.dto

import com.google.gson.annotations.SerializedName

data class SeriesListDTO(
    val page: Int,
    val results: List<SeriesResultDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)