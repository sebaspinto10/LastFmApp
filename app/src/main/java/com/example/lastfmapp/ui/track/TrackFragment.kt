package com.example.lastfmapp.ui.track

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lastfmapp.R
import com.example.lastfmapp.data.remote.RemoteArtistDataSource
import com.example.lastfmapp.databinding.FragmentTrackBinding
import com.example.lastfmapp.domain.ArtistRepositoryImpl
import com.example.lastfmapp.domain.RetrofitClient
import com.example.lastfmapp.presentation.ArtistViewModel
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.core.hide
import com.example.lastfmapp.core.show
import com.example.lastfmapp.presentation.ArtistViewModelFactory
import com.example.lastfmapp.ui.track.adapter.TrackAdapter


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

        showArtistInfo()
        goToArtistView()


        args.mbid.let {
            viewModel.getTop5Tracks(it).observe(viewLifecycleOwner) { result ->
                when(result) {
                    is Result.Loading -> {
                        binding.containerItemViewLoading.show()
                        Log.d("LiveData", "TrackFragment: Loading..")
                    }
                    is Result.Success -> {
                        binding.containerItemViewLoading.hide()
                        Log.d("LiveData", "TrackFragment Success: ${result.data}")
                        if (result.data.toptracks.track.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Ocurrio un error",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@observe
                        }
                        binding.rvTrack.adapter = TrackAdapter(result.data.toptracks.track)
                    }
                    is Result.Failure -> {
                        binding.containerItemViewLoading.hide()
                        Log.d("LiveData", "TrackFragment Error: ${result.exception}")
                    }
                }
            }
        }

    }

    private fun showArtistInfo() {
        Glide.with(requireContext()).load(args.urlPicture).centerCrop().into(binding.imgBackground)
        binding.collapsingToolbar.title = args.artistName

    }

    private fun goToArtistView() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack();
        }
    }
}