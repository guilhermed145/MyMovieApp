package com.myportfolio.mymovieapp.data.dto

data class CastListDTO(
    val cast: List<CastDTO>,
    val crew: List<CrewDTO>,
    val id: Int
)