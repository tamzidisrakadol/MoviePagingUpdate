package com.example.moviewithpaging.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviewithpaging.model.MovieModel
import com.example.moviewithpaging.model.MovieRemoteKeys

@Database(entities = [MovieModel::class,MovieRemoteKeys::class], version = 1)
abstract class MovieDatabase():RoomDatabase() {

    abstract fun movieDao():MovieDao
    abstract fun remoteKeyDao():RemoteDao

    companion object{

        @Volatile
        private var INSTANCE:MovieDatabase? = null

        fun getDatabase(context: Context):MovieDatabase{
            if (INSTANCE ==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        MovieDatabase::class.java,"MOVIEDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}