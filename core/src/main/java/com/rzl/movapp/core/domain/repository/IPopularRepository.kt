package com.rzl.movapp.core.domain.repository

import com.rzl.movapp.core.data.Resource
import com.rzl.movapp.core.domain.model.Popular
import kotlinx.coroutines.flow.Flow

interface IPopularRepository {
    fun getPopular():Flow<Resource<List<Popular>>>

    fun getFavoritePopular(): Flow<List<Popular>>

    fun setFavoritePopular(popular: Popular, state: Boolean)

}