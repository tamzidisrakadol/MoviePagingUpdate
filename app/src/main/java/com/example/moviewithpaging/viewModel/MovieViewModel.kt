package com.example.moviewithpaging.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.moviewithpaging.model.MovieResponse
import com.example.moviewithpaging.repository.MovieRepository
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MovieViewModel(private val movieRepository: MovieRepository):ViewModel() {
    val movieList = movieRepository.getMovieList().cachedIn(viewModelScope)
}