package com.wreckingball.bluetunes.ui.album

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wreckingball.bluetunes.models.Album
import com.wreckingball.bluetunes.repositories.AlbumRepository

class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {
    var albums = MutableLiveData<List<Album>>()

    init {
        albumRepository.albums = albums
    }

    fun getAlbums(context: Context, artistName: String) {
        albumRepository.fetchAlbums(context, artistName)
    }
}