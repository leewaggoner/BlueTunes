package com.wreckingball.bluetunes.di

import com.wreckingball.bluetunes.components.BTModel
import com.wreckingball.bluetunes.components.MusicPlayer
import com.wreckingball.bluetunes.repositories.AlbumRepository
import com.wreckingball.bluetunes.repositories.MusicRepository
import com.wreckingball.bluetunes.ui.album.AlbumViewModel
import com.wreckingball.bluetunes.ui.home.HomeViewModel
import com.wreckingball.bluetunes.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {
    viewModel { HomeViewModel(get()) }
    viewModel { AlbumViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    single { MusicPlayer() }
    single { BTModel() }
    single { MusicRepository() }
    factory { AlbumRepository() }
}