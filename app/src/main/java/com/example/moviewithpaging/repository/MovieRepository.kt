package com.example.moviewithpaging.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.paging.MovieDataSource
import com.example.moviewithpaging.paging.MovieRemoteMediator

import com.example.moviewithpaging.storage.MovieDatabase
import com.example.moviewithpaging.utility.Constraints
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class MovieRepository(private val apiService:MovieDBInterface,private val db:MovieDatabase) {
    fun getMovieList() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = MovieRemoteMediator(apiService,db),
        pagingSourceFactory = {db.movieDao().getMovie()}
    ).liveData
}