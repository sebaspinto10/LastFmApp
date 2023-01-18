package com.example.lastfmapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.lastfmapp.core.Result
import com.example.lastfmapp.domain.ArtistRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

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

}

class ArtistViewModelFactory(private val repo: ArtistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ArtistRepository::class.java).newInstance(repo)
    }
}