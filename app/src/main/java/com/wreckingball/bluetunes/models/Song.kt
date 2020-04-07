package com.wreckingball.bluetunes.models

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.wreckingball.recyclerviewexpandingadapter.ExpandingData

data class Song (
    var name: String,
    var length: String,
    var songId: Long,
    override var children: MutableList<ExpandingData>? = null,
    override var isParent: Boolean = false,
    override var index: Int = 0
) : ExpandingData {
    fun getSongUri() : Uri {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId)
    }
}