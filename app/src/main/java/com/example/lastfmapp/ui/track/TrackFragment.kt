package com.example.lastfmapp.ui.track

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.lastfmapp.R
import com.example.lastfmapp.data.remote.RemoteArtistDataSource
import com.example.lastfmapp.databinding.FragmentTrackBinding
import com.example.lastfmapp.domain.ArtistRepositoryImpl
import com.example.lastfmapp.domain.RetrofitClient
import com.example.lastfmapp.presentation.ArtistViewModel
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.presentation.ArtistViewModelFactory


class TrackFragment : Fragment(R.layout.fragment_track) {

    private lateinit var binding: FragmentTrackBinding
    private val args by navArgs<TrackFragmentArgs>()
    private val viewModel by viewModels<ArtistViewModel> {
        ArtistViewModelFactory(
            ArtistRepositoryImpl(RemoteArtistDataSource(RetrofitClient.webService))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        args.mbid.let {
            viewModel.getTop5Tracks(it).observe(viewLifecycleOwner) { result ->
                when(result) {
                    is Result.Loading -> {
                        Log.d("LiveData", "TrackFragment: Loading..")
                    }
                    is Result.Success -> {
                        Log.d("LiveData", "TrackFragment Success: ${result.data}")
                    }
                    is Result.Failure -> {
                        Log.d("LiveData", "TrackFragment Error: ${result.exception}")
                    }
                }
            }
        }

    }
}