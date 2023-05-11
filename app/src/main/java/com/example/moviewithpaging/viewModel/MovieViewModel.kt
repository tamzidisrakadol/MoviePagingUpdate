package com.example.moviewithpaging.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviewithpaging.repository.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository):ViewModel() {

    val movieList = movieRepository.getMovieList().cachedIn(viewModelScope)
}