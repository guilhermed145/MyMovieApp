package com.myportfolio.mymovieapp.data.dto

data class MovieListDTO(
    val dates: DatesDTO,
    val page: Int,
    val results: List<MovieResultDTO>,
    val total_pages: Int,
    val total_results: Int
)