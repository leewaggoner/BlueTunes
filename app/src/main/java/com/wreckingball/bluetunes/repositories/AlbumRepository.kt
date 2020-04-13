package com.wreckingball.bluetunes.repositories

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.wreckingball.bluetunes.models.Album
import com.wreckingball.bluetunes.models.Song

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
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val songLength = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val songId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    addAlbum(id, name, songName, songLength, songId)
                }
            }
        }

        albums.value = albumList
    }

    private fun addAlbum(id: Int, name: String, songName: String, songLength: Long, songId: Long) {
        val length = calcLength(songLength)
        for (album in albumList) {
            if (album.id == id) {
                album.childList?.add(Song(songName, length, songId))
                return
            }
        }

        val sArtworkUri: Uri = Uri.parse("content://media/external/audio/albumart")
        val imageUri: Uri = Uri.withAppendedPath(sArtworkUri, java.lang.String.valueOf(id))

        albumList.add(Album(id, name, imageUri, mutableListOf(Song(songName, length, songId))))
    }

    private fun calcLength(length: Long) : String {
        var seconds = length / 1000
        val minutes = seconds / 60
        seconds %= 60
        return if (seconds < 10) {
            "$minutes:0$seconds"
        } else {
            "$minutes:$seconds"
        }
    }
}