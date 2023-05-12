package com.example.moviewithpaging.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.moviewithpaging.repository.MovieRepository

@ExperimentalPagingApi
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val movieRepository: MovieRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}