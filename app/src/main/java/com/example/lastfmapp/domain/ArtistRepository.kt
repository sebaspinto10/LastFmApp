package com.example.lastfmapp.domain

import com.example.lastfmapp.data.model.ArtistList
import com.example.lastfmapp.data.model.TopArtist

interface ArtistRepository {

    suspend fun getTopArtists(): TopArtist
}