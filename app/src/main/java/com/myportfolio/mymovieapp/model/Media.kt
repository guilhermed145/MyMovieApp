package com.myportfolio.mymovieapp.model

data class Media(
    var mediaName: String,
    var mediaPicture: String,
    var releaseYear: Int,
    var genreList: List<String>,
    var castList: List<CastMember>
)