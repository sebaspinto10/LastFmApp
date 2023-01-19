package com.example.lastfmapp.data.model

import com.google.gson.annotations.SerializedName

data class Artist(
    val name: String = "",
    val listeners: String = "",
    val mbid: String = "",
    val url: String = "",
    val image: List<Image> = listOf()
)

data class ArtistList(val artist: List<Artist> = listOf())

data class TopArtist(val topartists: ArtistList)

data class Image(
    @SerializedName("#text")
    val textval: String = "",
    val size: String = ""
)

data class Track(
    val name: String = "",
    val listeners: String = "",
    val mbid: String = "",
    val url: String = "",
    val image: List<Image> = listOf()
)

data class TrackList(val track: List<Track> = listOf())

data class TopTracks(val toptracks: TrackList)