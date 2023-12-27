package com.rzl.movapp.core.utils

import com.rzl.movapp.core.data.source.local.entity.PopularEntity
import com.rzl.movapp.core.data.source.remote.response.PopularResponse
import com.rzl.movapp.core.domain.model.Popular


object DataMapper {
    fun mapResponsesToEntities(input: List<PopularResponse>): List<PopularEntity> {
        val popularList = ArrayList<PopularEntity>()
        input.map {
            val popular = PopularEntity(
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                id = it.id,
                isFavorite = false
            )
            popularList.add(popular)
        }
        return popularList
    }


    fun mapEntitiesToDomain(input: List<PopularEntity>): List<Popular> =
        input.map {
            Popular(
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                id = it.id,
                isFavorite = it.isFavorite

            )
        }

    fun mapDomainToEntity(input: Popular) = PopularEntity(
        overview = input.overview,
        title = input.title,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        id = input.id,
        isFavorite = input.isFavorite
    )


}