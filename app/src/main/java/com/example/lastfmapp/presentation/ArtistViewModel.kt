package com.example.lastfmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.domain.ArtistRepository
import kotlinx.coroutines.Dispatchers

class ArtistViewModel(private val repo: ArtistRepository) : ViewModel() {

    fun getTopArtists() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        emit(Result.Loading())

        kotlin.runCatching {
            repo.getTopArtists()
        }.onSuccess { topArtist ->
            emit(Result.Success(topArtist))
        }.onFailure { throwable ->
            emit(Result.Failure(Exception(throwable.message)))
        }
    }

    fun getTop5Tracks(mbid: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())

        kotlin.runCatching {
            repo.getTop5Tracks(mbid)
        }.onSuccess { listTrack ->
            emit(Result.Success(listTrack))
        }.onFailure { throwable ->
            emit(Result.Failure(Exception(throwable.message)))
        }
    }

}

class ArtistViewModelFactory(private val repo: ArtistRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ArtistRepository::class.java).newInstance(repo)
    }
}