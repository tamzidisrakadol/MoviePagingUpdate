package com.example.moviewithpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val previousKey: Int?,
    val nextKey: Int?
)