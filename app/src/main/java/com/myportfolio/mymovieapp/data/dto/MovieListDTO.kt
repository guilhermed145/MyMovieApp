package com.myportfolio.mymovieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieListDTO(
    val dates: DatesDTO,
    val page: Int,
    val results: List<MovieResultDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)