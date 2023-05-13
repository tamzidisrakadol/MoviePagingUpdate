package com.example.moviewithpaging.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.model.MovieModel


class MovieDataSource(
    private val apiService: MovieDBInterface,
) : PagingSource<Int, MovieModel>() {



    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getPopularMovies(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page==response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}