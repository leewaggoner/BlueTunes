package com.wreckingball.bluetunes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.models.Album
import com.wreckingball.bluetunes.models.AlbumSong
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_song.view.*

private const val TYPE_ALBUM = 1
private const val TYPE_SONG = 2

class AlbumAdapter(
    private val list: List<Album>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        lateinit var albumSongList: MutableList<AlbumSong>
        lateinit var albumAdapter: AlbumAdapter
    }

    init {
        albumAdapter = this
        albumSongList = mutableListOf()
        for (album in list) {
            albumSongList.add(AlbumSong(album.name, album.albumArt, null, null,
                null, true))
        }
    }

    class AlbumViewHolder(itemView: View, private val list: List<Album>) : RecyclerView.ViewHolder(itemView) {
        var albumExpanded = -1

        fun bindView(item: AlbumSong) {
            itemView.albumName.text = item.albumName

            if (item.albumArtUri == null) {
                itemView.albumArt.setImageResource(R.drawable.cocteau_twins_heaven_or_las_vegas)
            } else {
                val picasso = Picasso.get()
                picasso.load(item.albumArtUri)
                    .placeholder(R.drawable.cocteau_twins_heaven_or_las_vegas)
                    .into(itemView.albumArt)
            }

            itemView.setOnClickListener {
                if (albumExpanded == -1) {
                    expandAlbum()
                } else if (albumExpanded != layoutPosition) {
                    collapseAlbum(albumExpanded)
                    expandAlbum()
                } else if (albumExpanded == layoutPosition) {
                    collapseAlbum(albumExpanded)
                }
            }
        }

        private fun expandAlbum() {
            val songs = list[layoutPosition].songs
            if (!songs.isNullOrEmpty()) {
                albumExpanded = layoutPosition
                for ((songIndex, i) in (layoutPosition + 1 until layoutPosition + 1 + songs.size).withIndex()) {
                    val song = songs[songIndex]
                    albumSongList.add(i, AlbumSong(null, null, song.name,
                        song.length, song.getSongUri(), false))
                }
                albumAdapter.notifyItemRangeInserted(layoutPosition + 1, songs.size);
            }
        }

        private fun collapseAlbum(openAlbum: Int) {
            val songs = list[openAlbum].songs
            if (!songs.isNullOrEmpty()) {
                albumExpanded = -1
                for (i in (openAlbum + 1 until openAlbum + 1 + songs.size)) {
                    albumSongList.removeAt(openAlbum + 1)
                }
                albumAdapter.notifyItemRangeRemoved(layoutPosition + 1, songs.size);
            }
        }
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: AlbumSong) {
            itemView.songName.text = item.songName
            itemView.songLength.text = item.songLength
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ALBUM) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
            AlbumViewHolder(view, list)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
            SongViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = albumSongList[position]
        if (item.isAlbum) {
            (holder as AlbumViewHolder).bindView(item)
        } else {
            (holder as SongViewHolder).bindView(item)
        }
    }

    override fun getItemCount(): Int {
        return albumSongList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (albumSongList[position].isAlbum) {
            return TYPE_ALBUM
        } else {
            return TYPE_SONG
        }
    }
}
