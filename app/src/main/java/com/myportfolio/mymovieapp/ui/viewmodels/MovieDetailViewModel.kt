package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class MovieDetailUiState (
    var castList: List<CastMember> = listOf(),
    var genreList: List<String> = listOf(),
    var isInFavorites: Boolean = false
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentGenreList(newGenreList: List<String>) {
        _uiState.update {
            it.copy(genreList = newGenreList)
        }
    }

    fun setCurrentCastList(newCastList: List<CastMember>) {
        _uiState.update {
            it.copy(castList = newCastList)
        }
    }

    fun getGenreList(media: Media) {
        viewModelScope.launch {
            try {
                val apiData = repository.getGenreList(media.mediaType)
                val genreNameList: MutableList<String> = mutableListOf()
                for (genreId in media.genreIdList) {
                    for (genre in apiData) {
                        if (genreId == genre.id) {
                            genreNameList.add(genre.name)
                        }
                    }
                }
                media.genreNameList = genreNameList
                _uiState.update {
                    it.copy(
                        genreList = genreNameList
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCurrentGenreList()", e)
                _uiState.update {
                    it.copy(
                        genreList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCurrentGenreList()", e)
                _uiState.update {
                    it.copy(
                        genreList = listOf()
                    )
                }
            }
        }
    }

    fun getCastList(media: Media) {
        viewModelScope.launch {
            try {
                val apiData = repository.getCastList(media.mediaType, media.id)
                media.castList = apiData
                _uiState.update {
                    it.copy(
                        castList = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCurrentCastList()", e)
                _uiState.update {
                    it.copy(
                        castList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCurrentCastList()", e)
                _uiState.update {
                    it.copy(
                        castList = listOf()
                    )
                }
            }
        }
    }

    fun checkIfMediaIsInFavorites(currentMedia: Media) {
        viewModelScope.launch {
            try {
                var isInFavorites = false
                if (repository.isMediaInFavorites(currentMedia.id) == 1) {
                    isInFavorites = true
                }
                _uiState.update {
                    it.copy(
                        isInFavorites = isInFavorites
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "checkIfMediaIsInFavorites()", e)
                _uiState.update {
                    it.copy(
                        isInFavorites = false
                    )
                }
            }
        }
    }

    fun addMediaToFavorites(currentMedia: Media) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(currentMedia)
                _uiState.update {
                    it.copy(
                        isInFavorites = true
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "addMediaToFavorites()", e)
            }
        }
    }

    fun removeMediaFromFavorites(currentMedia: Media) {
        viewModelScope.launch {
            try {
                repository.removeFromFavorites(currentMedia)
                _uiState.update {
                    it.copy(
                        isInFavorites = false
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "removeMediaFromFavorites()", e)
            }
        }
    }

}