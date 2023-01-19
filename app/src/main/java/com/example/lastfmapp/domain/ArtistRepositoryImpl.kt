package com.example.lastfmapp.domain

import com.example.lastfmapp.data.model.TopArtist
import com.example.lastfmapp.data.model.TopTracks
import com.example.lastfmapp.data.remote.RemoteArtistDataSource

class ArtistRepositoryImpl(private val dataSourceRemote: RemoteArtistDataSource) :
    ArtistRepository {
    override suspend fun getTop10Artists(): TopArtist = dataSourceRemote.getTop10Artists()
    override suspend fun getTop5Tracks(mbid: String): TopTracks = dataSourceRemote.getTop5Tracks(mbid)
}