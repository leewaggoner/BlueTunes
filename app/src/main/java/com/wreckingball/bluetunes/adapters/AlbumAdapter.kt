package com.wreckingball.bluetunes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.models.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(
    private val list: List<Album>,
    private val context: Context
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Album) {
            itemView.albumName.text = item.name

            if (item.albumArt == null) {
                itemView.albumArt.setImageResource(R.drawable.cocteau_twins_heaven_or_las_vegas)
            } else {
                val picasso = Picasso.get()
                picasso.load(item.albumArt)
                    .placeholder(R.drawable.cocteau_twins_heaven_or_las_vegas)
                    .into(itemView.albumArt)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
