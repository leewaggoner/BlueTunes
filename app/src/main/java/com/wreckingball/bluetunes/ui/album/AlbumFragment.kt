package com.wreckingball.bluetunes.ui.album

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.adapters.AlbumAdapter
import kotlinx.android.synthetic.main.fragment_album.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val model: AlbumViewModel by viewModel()
    private val args: AlbumFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getAlbums(requireContext(), args.artistName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artistName.text = args.artistName

        model.albums.observe(viewLifecycleOwner, Observer { albums ->
            recyclerViewAlbum.adapter = AlbumAdapter(albums, requireContext())
        })
    }
}
