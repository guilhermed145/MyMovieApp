package com.myportfolio.mymovieapp.model

data class Media(
    var id: Int = 0,
    var mediaName: String = "",
    var bigPoster: String = "",
    var smallPoster: String = "",
    var releaseYear: Int = 0,
    var synopsis: String = "",
    var genreIdList: List<Int> = listOf()
)