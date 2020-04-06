package com.wreckingball.bluetunes.di

import com.wreckingball.bluetunes.repositories.MusicRepository
import com.wreckingball.bluetunes.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {
    viewModel { HomeViewModel(get()) }
    single { MusicRepository() }
}