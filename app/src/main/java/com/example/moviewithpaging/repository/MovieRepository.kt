package com.example.moviewithpaging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.paging.MovieDataSource
import com.example.moviewithpaging.utility.Constraints
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieRepository(private val apiService:MovieDBInterface) {

    fun getMovieList() = Pager(
        config = PagingConfig(pageSize = Constraints.POST_PER_PAGE, maxSize = 100),
        pagingSourceFactory = {MovieDataSource(apiService)}
    ).liveData
}