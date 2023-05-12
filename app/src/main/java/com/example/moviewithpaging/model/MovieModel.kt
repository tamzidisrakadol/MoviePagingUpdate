package com.example.moviewithpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MovieTable")
data class MovieModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int=0,
    @SerializedName("poster_path")
    val posterPath: String="",
    @SerializedName("release_date")
    val releaseDate: String="",
    @SerializedName("title")
    val title: String="",
)