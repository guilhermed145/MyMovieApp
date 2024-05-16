package com.myportfolio.mymovieapp.data.dto

data class SerieListDTO(
    val page: Int,
    val results: List<SerieResultDTO>,
    val total_pages: Int,
    val total_results: Int
)