package com.rzl.movapp.di

import com.rzl.movapp.core.domain.usecase.PopularInteractor
import com.rzl.movapp.core.domain.usecase.PopularUseCase
import com.rzl.movapp.detail.DetailViewModel
import com.rzl.movapp.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<PopularUseCase>{ PopularInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}

