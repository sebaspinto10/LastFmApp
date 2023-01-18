package com.example.lastfmapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lastfmapp.R
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.data.remote.RemoteArtistDataSource
import com.example.lastfmapp.domain.ArtistRepository
import com.example.lastfmapp.domain.ArtistRepositoryImpl
import com.example.lastfmapp.domain.RetrofitClient
import com.example.lastfmapp.presentation.ArtistViewModel
import com.example.lastfmapp.presentation.ArtistViewModelFactory


class ArtistFragment : Fragment(R.layout.fragment_artist) {

    private val viewModel by viewModels<ArtistViewModel> {
        ArtistViewModelFactory(
            ArtistRepositoryImpl(RemoteArtistDataSource(RetrofitClient.webService))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTopArtists().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("LiveData Artist", "Loading...")
                }
                is Result.Success -> {
                    Log.d("LiveData Artist", "Result: ${result.data.topartists}")
                }
                is Result.Failure -> {
                    Log.d("LiveData Artist", "Error: ${result.exception}")
                }
            }
        }
    }
}