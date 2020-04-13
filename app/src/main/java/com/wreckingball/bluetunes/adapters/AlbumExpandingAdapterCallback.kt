package com.wreckingball.bluetunes.adapters

import android.view.View
import com.squareup.picasso.Picasso
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.models.Album
import com.wreckingball.bluetunes.models.Song
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_song.view.*

class AlbumExpandingAdapterCallback : ExpandingAdapterCallback {
    override fun onBindParentView(itemView: View, item: ExpandingData) {
        val album = item as Album

        itemView.tag = album.name

        itemView.albumName.text = album.name

        if (album.albumArt == null) {
            itemView.albumArt.setImageResource(R.drawable.cocteau_twins_heaven_or_las_vegas)
        } else {
            val picasso = Picasso.get()
            picasso.load(album.albumArt)
                .placeholder(R.drawable.cocteau_twins_heaven_or_las_vegas)
                .into(itemView.albumArt)
        }
    }

    override fun onParentExpand(itemView: View) {
        val tag = itemView.tag
        itemView.arrow.setImageResource(R.drawable.up)
    }

    override fun onParentCollapse(itemView: View) {
        val tag = itemView.tag
        itemView.arrow.setImageResource(R.drawable.down)
    }

    override fun onBindChildView(itemView: View, item: ExpandingData) {
        val song = item as Song
        itemView.songName.text = song.name
        itemView.songLength.text = song.length
    }

    override fun onChildClick(itemView: View, item: ExpandingData) {
        //play song
    }
}