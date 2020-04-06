package com.wreckingball.bluetunes.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wreckingball.bluetunes.R
import com.wreckingball.bluetunes.adapters.ArtistAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    val model: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.getArtists(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.artists.observe(viewLifecycleOwner, Observer { artists ->
            recyclerViewArtist.adapter = ArtistAdapter(artists, requireContext())
        })
    }
}
