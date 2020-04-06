package com.wreckingball.bluetunes.repositories

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.wreckingball.bluetunes.models.Album

class AlbumRepository {
    lateinit var albums: MutableLiveData<List<Album>>
    private val albumList = mutableListOf<Album>()

    fun fetchAlbums(context: Context, artistName: String) {
        val projection = arrayOf("*")
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
                MediaStore.Audio.Media.ARTIST + "='" + artistName + "'"

        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        ).use { cursor ->
            cursor?.let {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    addAlbum(id, name)
                }
            }
        }

        albums.value = albumList
    }
    fun addAlbum(id: Int, name: String) {
        for (album in albumList) {
            if (album.id == id) {
                return
            }
        }

        val sArtworkUri: Uri = Uri.parse("content://media/external/audio/albumart")
        val imageUri: Uri = Uri.withAppendedPath(sArtworkUri, java.lang.String.valueOf(id))

        albumList.add(Album(id, name, null, imageUri))
    }
}