package com.example.lastfmapp.domain

import com.example.lastfmapp.data.model.TopArtist
import com.example.lastfmapp.data.remote.RemoteArtistDataSource

class ArtistRepositoryImpl(private val dataSourceRemote: RemoteArtistDataSource) :
    ArtistRepository {
    override suspend fun getTopArtists(): TopArtist = dataSourceRemote.getTopArtists()
}