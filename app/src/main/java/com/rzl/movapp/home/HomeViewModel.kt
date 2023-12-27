package com.rzl.movapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rzl.movapp.core.domain.usecase.PopularUseCase

class HomeViewModel(popularUseCase: PopularUseCase) : ViewModel() {
    val popular = popularUseCase.getPopular().asLiveData()
}