package com.rzl.movapp.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rzl.movapp.core.data.source.local.entity.PopularEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PopulerDao{

    @Query("SELECT * FROM favorite")
    fun getAllPopular(): Flow<List<PopularEntity>>

    @Query("SELECT * FROM favorite where isFavorite = 1")
    fun getFavoritePopular(): Flow<List<PopularEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular(popular: List<PopularEntity>)

    @Update
    fun updateFavoritePopular(popular: PopularEntity)

}