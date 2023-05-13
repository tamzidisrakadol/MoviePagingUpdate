package com.example.moviewithpaging.api

import com.example.moviewithpaging.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResponse


}