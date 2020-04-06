package com.wreckingball.bluetunes.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wreckingball.bluetunes.models.Artist
import com.wreckingball.bluetunes.repositories.MusicRepository

class HomeViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    var artists = MutableLiveData<List<Artist>>()

    init {
        musicRepository.artists = artists
    }

    fun getArtists(context: Context) {
        musicRepository.fetchArtists(context)
    }
}