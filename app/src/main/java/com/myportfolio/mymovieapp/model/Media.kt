package com.myportfolio.mymovieapp.model

import com.myportfolio.mymovieapp.util.Constants

data class Media(
    var id: Int = 0,
    var mediaName: String = "",
    var bigPoster: String = "",
    var mediumPoster: String = "",
    var smallPoster: String = "",
    var releaseYear: Int = 0,
    var synopsis: String = "",
    var genreIdList: List<Int> = listOf(),
    var mediaType: String = Constants.movieTypeId
)