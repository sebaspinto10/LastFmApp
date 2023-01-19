package com.example.lastfmapp.data.remote

import com.example.lastfmapp.application.AppConstants
import com.example.lastfmapp.data.model.TopArtist
import com.example.lastfmapp.data.model.TopTracks
import com.example.lastfmapp.domain.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteArtistDataSource(private val webService: WebService) {

    suspend fun getTop10Artists(): TopArtist =
        withContext(Dispatchers.IO) {
            webService.getTopArtists(
                AppConstants.API_METHOD_GEO,
                AppConstants.API_COUNTRY,
                AppConstants.API_KEY,
                AppConstants.API_FORMAT,
                "10"
            )
        }

    suspend fun getTop5Tracks(mbid: String): TopTracks = withContext(Dispatchers.IO) {
        webService.getTopTracks(
            AppConstants.API_METHOD_ARIST,
            mbid,
            "5",
            AppConstants.API_KEY,
            AppConstants.API_FORMAT
        )
    }

}