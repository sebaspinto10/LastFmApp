package com.example.lastfmapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lastfmapp.R
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.core.hide
import com.example.lastfmapp.core.show
import com.example.lastfmapp.data.model.Artist
import com.example.lastfmapp.data.remote.RemoteArtistDataSource
import com.example.lastfmapp.databinding.FragmentArtistBinding
import com.example.lastfmapp.domain.ArtistRepositoryImpl
import com.example.lastfmapp.domain.RetrofitClient
import com.example.lastfmapp.presentation.ArtistViewModel
import com.example.lastfmapp.presentation.ArtistViewModelFactory
import com.example.lastfmapp.ui.main.adapter.ArtistAdapter
import com.example.lastfmapp.ui.main.adapter.OnArtistClickListener


class ArtistFragment : Fragment(R.layout.fragment_artist), OnArtistClickListener {

    private lateinit var binding: FragmentArtistBinding

    private val viewModel by viewModels<ArtistViewModel> {
        ArtistViewModelFactory(
            ArtistRepositoryImpl(RemoteArtistDataSource(RetrofitClient.webService))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistBinding.bind(view)

        viewModel.getTop10Artists().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    Log.d("LiveData Artist", "Loading...")
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    Log.d("LiveData Artist", "Result: ${result.data.topartists}")
                    if (result.data.topartists.artist.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Ocurrio un error",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@observe
                    }
                    binding.rvArtist.adapter = ArtistAdapter(result.data.topartists.artist, this)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Log.d("LiveData Artist", "Error: ${result.exception}")
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un erro: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onArtistClick(artist: Artist) {
        val action = ArtistFragmentDirections.actionArtistFragmentToTrackFragment(artist.mbid, artist.image[2].textval, artist.name)
        findNavController().navigate(action)
        Log.d("Artist", "onArtistClick: $artist")
    }
}