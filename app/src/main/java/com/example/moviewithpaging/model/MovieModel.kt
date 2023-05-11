package com.example.moviewithpaging.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    val id: Int=0,
    @SerializedName("poster_path")
    val posterPath: String="",
    @SerializedName("release_date")
    val releaseDate: String="",
    @SerializedName("title")
    val title: String="",
)