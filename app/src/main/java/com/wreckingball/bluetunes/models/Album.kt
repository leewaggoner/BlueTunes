package com.wreckingball.bluetunes.models

import android.net.Uri
import com.wreckingball.recyclerviewexpandingadapter.ExpandingData

class Album (
    var id: Int,
    var name: String,
    var albumArt: Uri?,
    var childList: MutableList<Song>?
) : ExpandingData(true, childList)