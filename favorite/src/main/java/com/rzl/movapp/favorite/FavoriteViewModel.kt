package com.rzl.movapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rzl.movapp.core.domain.usecase.PopularUseCase

class FavoriteViewModel(popularUseCase: PopularUseCase) : ViewModel() {
    val favoritePopuler = popularUseCase.getFavoritePopular().asLiveData()
}