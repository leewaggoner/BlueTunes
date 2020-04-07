package com.wreckingball.bluetunes.models

import android.net.Uri

data class Album (
    var id: Int,
    var name: String,
    var songs: MutableList<Song>?,
    var albumArt: Uri?
)