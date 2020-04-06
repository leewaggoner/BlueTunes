package com.wreckingball.bluetunes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.models.Artist
import com.wreckingball.bluetunes.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistAdapter(
    private val list: List<Artist>,
    private val context: Context) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Artist) {
            itemView.artistName.text = item.name

            if (item.image == null) {
                itemView.artistPic.setImageResource(R.drawable.generic_band)
            } else {
                itemView.artistPic.setImageBitmap(item.image)
            }

            itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(item.name)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false)
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
