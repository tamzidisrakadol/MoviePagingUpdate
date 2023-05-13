package com.example.moviewithpaging.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.paging.MovieDataSource
import com.example.moviewithpaging.paging.MovieRemoteMediator
import com.example.moviewithpaging.storage.MovieDatabase
import com.example.moviewithpaging.utility.Constraints


@ExperimentalPagingApi
class MovieRepository(private val apiService:MovieDBInterface,private val db:MovieDatabase) {
    fun getMovieList() = Pager(
        config = PagingConfig(pageSize = Constraints.POST_PER_PAGE, maxSize = 100),
        remoteMediator = MovieRemoteMediator(apiService,db),
        pagingSourceFactory = {MovieDataSource(apiService)}
    ).liveData

}