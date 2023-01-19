package com.example.lastfmapp.domain

import com.example.lastfmapp.application.AppConstants
import com.example.lastfmapp.data.model.ArtistList
import com.example.lastfmapp.data.model.TopArtist
import com.example.lastfmapp.data.model.TopTracks
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET(".")
    suspend fun getTopArtists(
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String
    ): TopArtist

    @GET(".")
    suspend fun getTopTracks(
        @Query("method") method: String,
        @Query("mbid") mbid: String,
        @Query("limit ") limit: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String
    ) : TopTracks

}

object RetrofitClient {
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}