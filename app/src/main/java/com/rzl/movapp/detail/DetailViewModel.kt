package com.rzl.movapp.detail

import androidx.lifecycle.ViewModel
import com.rzl.movapp.core.domain.model.Popular
import com.rzl.movapp.core.domain.usecase.PopularUseCase

class DetailViewModel(private val popularUseCase: PopularUseCase) : ViewModel() {
    fun setFavoritePopular(popular: Popular, newStatus:Boolean) =
        popularUseCase.setFavoritePopular(popular, newStatus)

}