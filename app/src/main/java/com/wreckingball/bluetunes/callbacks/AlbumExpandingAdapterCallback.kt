package com.wreckingball.bluetunes.callbacks

import android.view.View
import com.squareup.picasso.Picasso
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.components.MusicPlayer
import com.wreckingball.bluetunes.models.Album
import com.wreckingball.bluetunes.models.Song
import com.wreckingball.recyclerviewexpandingadapter.ExpandingAdapterCallback
import com.wreckingball.recyclerviewexpandingadapter.ExpandingData
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_song.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AlbumExpandingAdapterCallback(private val fragmentCallback: FragmentCallback) : ExpandingAdapterCallback, KoinComponent {
    val musicPlayer: MusicPlayer by inject()

    override fun onBindParentView(itemView: View, item: ExpandingData) {
        val album = item as Album

        itemView.albumName.text = album.name

        if (album.albumArt == null) {
            itemView.albumArt.setImageResource(R.drawable.cocteau_twins_heaven_or_las_vegas)
        } else {
            val picasso = Picasso.get()
            picasso.load(album.albumArt)
                .placeholder(R.drawable.cocteau_twins_heaven_or_las_vegas)
                .into(itemView.albumArt)
        }

        if (item.isExpanded) {
            itemView.arrow.setImageResource(R.drawable.up)
        } else {
            itemView.arrow.setImageResource(R.drawable.down)
        }
    }

    override fun onParentExpand(itemView: View) {
        itemView.arrow.setImageResource(R.drawable.up)
    }

    override fun onParentCollapse(itemView: View) {
        itemView.arrow.setImageResource(R.drawable.down)
    }

    override fun onBindChildView(itemView: View, item: ExpandingData) {
        val song = item as Song
        itemView.songName.text = song.name
        itemView.songLength.text = song.length
    }

    override fun onChildClick(itemView: View, item: ExpandingData) {
        if (item is Song) {
            if (!musicPlayer.isPlaying()) {
                musicPlayer.playSong(itemView.context, item)
                fragmentCallback.showMusicControlBar(true)
            } else {
                musicPlayer.stopSong()
                fragmentCallback.showMusicControlBar(false)
            }
        }
    }
}