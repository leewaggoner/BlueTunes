package com.wreckingball.bluetunes.repositories

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.wreckingball.bluetunes.models.Artist

class MusicRepository {
    lateinit var artists: MutableLiveData<List<Artist>>
    val artistList = mutableListOf<Artist>()

    fun fetchArtists(context: Context) {
        val projection = arrayOf("*")
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        ).use { cursor ->
            cursor?.let {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    addArtist(name)
                }
                artistList.sortBy { artist ->  artist.name }
                artists.value = artistList
            }
        }
    }

    fun addArtist(name: String) {
        for(artist in artistList) {
            if (artist.name.equals(name, true)) {
                return
            }
        }
        artistList.add(Artist(name, null))
    }
}