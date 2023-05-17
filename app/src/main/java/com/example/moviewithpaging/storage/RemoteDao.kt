package com.example.moviewithpaging.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviewithpaging.model.MovieRemoteKeys

@Dao
interface RemoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(list:List<MovieRemoteKeys>)

    @Query("SELECT * FROM MovieRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id:Int):MovieRemoteKeys

    @Query("DELETE FROM MovieRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}