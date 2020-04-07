package com.wreckingball.bluetunes.models

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

data class Song (
    var name: String,
    var length: String,
    var songId: Long
) {
    fun getSongUri() : Uri {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId)
    }
}