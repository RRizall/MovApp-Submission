package com.rzl.movapp.core.domain.usecase

import com.rzl.movapp.core.domain.model.Popular
import com.rzl.movapp.core.domain.repository.IPopularRepository

class PopularInteractor(private val iPopularRespository: IPopularRepository) : PopularUseCase {

    override fun getPopular () = iPopularRespository.getPopular()
    override fun getFavoritePopular() = iPopularRespository.getFavoritePopular()
    override fun setFavoritePopular(dataDetail: Popular, state: Boolean) =iPopularRespository.setFavoritePopular(dataDetail,state)
}