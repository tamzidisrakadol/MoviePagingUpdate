package com.example.moviewithpaging.storage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviewithpaging.model.MovieModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieList:List<MovieModel>)

    @Query("SELECT * FROM MovieTable")
    fun getMovie():PagingSource<Int,MovieModel>

}