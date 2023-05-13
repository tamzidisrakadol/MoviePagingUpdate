package com.example.moviewithpaging.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviewithpaging.api.MovieDBInterface
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.model.MovieRemoteKeys
import com.example.moviewithpaging.storage.MovieDatabase


@ExperimentalPagingApi
class MovieRemoteMediator(private val api:MovieDBInterface, private val db:MovieDatabase):RemoteMediator<Int,MovieModel>() {

    private val movieDao = db.movieDao()
    private val remoteDao = db.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {
        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1)?:1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstTime(state)
                    val prev = remoteKeys?.previousKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prev
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nxtPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys!=null
                        )
                    nxtPage
                }
            }

            val response = api.getPopularMovies(currentPage)
            val endOfPaginationReached = response.blockingGet().totalPages == currentPage   // single
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            db.withTransaction {
                movieDao.addMovie(response.blockingGet().results)  // single
                val keys = response.blockingGet().results.map { // single
                    MovieRemoteKeys(
                        id = it.id,
                        nextKey = nextPage,
                        previousKey = prevPage
                    )
                }
                remoteDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e:Exception){
            MediatorResult.Error(e)
        }
    }


    private  fun getRemoteKeyForLastItem(state: PagingState<Int, MovieModel>): MovieRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            remoteDao.getRemoteKeys(id = it.id)
        }
    }

    private  fun getRemoteKeysForFirstTime(state: PagingState<Int, MovieModel>): MovieRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { quote ->
                remoteDao.getRemoteKeys(id = quote.id)
            }
    }
    private  fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieModel>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteDao.getRemoteKeys(id = id)
            }
        }
    }


}