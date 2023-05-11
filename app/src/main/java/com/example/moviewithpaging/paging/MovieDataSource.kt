package com.example.moviewithpaging.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.utility.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDataSource(
    private val apiService: MovieDBInterface,
) : PagingSource<Int, MovieModel>() {

    private val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val page = params.key ?: 1
        networkState.postValue(NetworkState.Loading)

        return try {
            val response = apiService.getPopularMovies(page).blockingGet()
            val movies = response.results
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (movies.isEmpty()) null else page + 1

            networkState.postValue(NetworkState.Loaded)
            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            networkState.postValue(NetworkState.Failed)
            LoadResult.Error(e)
        }
    }


}