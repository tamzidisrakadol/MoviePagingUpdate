package com.example.moviewithpaging.api

import com.example.moviewithpaging.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page")page:Int): Single<MovieResponse>
}