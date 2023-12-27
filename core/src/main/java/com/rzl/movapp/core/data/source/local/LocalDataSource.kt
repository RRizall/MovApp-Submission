package com.rzl.movapp.core.data.source.local

import androidx.lifecycle.LiveData
import com.rzl.movapp.core.data.source.local.entity.PopularEntity
import com.rzl.movapp.core.data.source.local.room.PopulerDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favDao: PopulerDao) {
    fun getAllPopular(): Flow<List<PopularEntity>> = favDao.getAllPopular()

    fun getFavoritePopular(): Flow<List<PopularEntity>> = favDao.getFavoritePopular()

    suspend fun insertPopular(popularList: List<PopularEntity>) =favDao.insertPopular(popularList)

    fun setFavoritePopular(popular: PopularEntity, newState: Boolean) {
        popular.isFavorite = newState
        favDao.updateFavoritePopular(popular)
    }
}