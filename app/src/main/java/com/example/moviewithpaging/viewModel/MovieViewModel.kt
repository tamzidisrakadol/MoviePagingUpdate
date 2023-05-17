package com.example.moviewithpaging.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.model.MovieResponse
import com.example.moviewithpaging.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MovieViewModel(private val movieRepository: MovieRepository):ViewModel() {
    val movielist = movieRepository.getMovieList().cachedIn(viewModelScope)
}