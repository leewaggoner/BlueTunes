package com.wreckingball.bluetunes.models

import android.net.Uri
import com.wreckingball.recyclerviewexpandingadapter.ExpandingData

data class Album (
    var id: Int,
    var name: String,
    var albumArt: Uri?,
    override var children: MutableList<ExpandingData>?,
    override var isParent: Boolean = true,
    override var index: Int = 0
) : ExpandingData