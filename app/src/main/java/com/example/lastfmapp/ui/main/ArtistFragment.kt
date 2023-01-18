package com.example.lastfmapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lastfmapp.R
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.core.hide
import com.example.lastfmapp.core.show
import com.example.lastfmapp.data.remote.RemoteArtistDataSource
import com.example.lastfmapp.databinding.FragmentArtistBinding
import com.example.lastfmapp.domain.ArtistRepository
import com.example.lastfmapp.domain.ArtistRepositoryImpl
import com.example.lastfmapp.domain.RetrofitClient
import com.example.lastfmapp.presentation.ArtistViewModel
import com.example.lastfmapp.presentation.ArtistViewModelFactory
import com.example.lastfmapp.ui.main.adapter.ArtistAdapter


class ArtistFragment : Fragment(R.layout.fragment_artist) {

    private lateinit var binding: FragmentArtistBinding

    private val viewModel by viewModels<ArtistViewModel> {
        ArtistViewModelFactory(
            ArtistRepositoryImpl(RemoteArtistDataSource(RetrofitClient.webService))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistBinding.bind(view)

        viewModel.getTopArtists().observe(viewLifecycleOwner) { result ->
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
                            "Ocurrio un erro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.rvArtist.adapter = ArtistAdapter(result.data.topartists.artist)
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
}