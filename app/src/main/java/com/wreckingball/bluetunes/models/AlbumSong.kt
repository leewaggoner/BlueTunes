package com.wreckingball.bluetunes.models

import android.net.Uri

data class AlbumSong (
    var albumName: String?,
    var albumArtUri: Uri?,
    var songName: String?,
    var songLength: String?,
    var songUri: Uri?,
    var isAlbum: Boolean
)