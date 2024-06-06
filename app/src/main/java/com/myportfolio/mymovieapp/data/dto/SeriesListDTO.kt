package com.myportfolio.mymovieapp.data.dto

data class SeriesListDTO(
    val page: Int,
    val results: List<SeriesResultDTO>,
    val total_pages: Int,
    val total_results: Int
)