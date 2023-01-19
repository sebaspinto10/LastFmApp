package com.example.lastfmapp.domain

import com.example.lastfmapp.data.model.TopArtist
import com.example.lastfmapp.data.model.TopTracks

interface ArtistRepository {

    suspend fun getTop10Artists(): TopArtist
    suspend fun getTop5Tracks(mbid: String): TopTracks
}