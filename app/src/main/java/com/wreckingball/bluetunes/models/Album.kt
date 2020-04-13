package com.wreckingball.bluetunes.models

import android.net.Uri

class Album (
    var id: Int,
    var name: String,
    var albumArt: Uri?,
    var childList: MutableList<Song>?
) : ExpandingData(true, childList)