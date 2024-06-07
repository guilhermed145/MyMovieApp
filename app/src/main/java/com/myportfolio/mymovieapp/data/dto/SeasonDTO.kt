package com.myportfolio.mymovieapp.data.dto

import com.google.gson.annotations.SerializedName

data class SeasonDTO(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: Any,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("vote_average")
    val voteAverage: Double
)