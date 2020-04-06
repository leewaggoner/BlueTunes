package com.wreckingball.bluetunes.models

import android.net.Uri

data class Album (
    var id: Int,
    var name: String,
    var songs: List<Song>?,
    var albumArt: Uri?
)