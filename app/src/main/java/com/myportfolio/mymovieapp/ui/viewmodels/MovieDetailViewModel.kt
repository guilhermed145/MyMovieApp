package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre
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
    var genreList: List<MediaGenre> = listOf(),
    var isInFavorites: Boolean = false
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentGenreList(newGenreList: List<MediaGenre>) {
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
                val apiData = repository.getMediaGenreList(media)
                _uiState.update {
                    it.copy(
                        genreList = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getGenreList()", e.stackTraceToString())
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
                val apiData = repository.getCastList(media)
                _uiState.update {
                    it.copy(
                        castList = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getCastList()", e.stackTraceToString())
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
            } catch (e: Exception) {
                Log.e("checkIfMediaIsInFavorites()", e.stackTraceToString())
                _uiState.update {
                    it.copy(
                        isInFavorites = false
                    )
                }
            }
        }
    }

    fun addMediaToFavorites(currentMedia: Media) {
        currentMedia.genreList = uiState.value.genreList
        currentMedia.castList = uiState.value.castList
        viewModelScope.launch {
            try {
                repository.addToFavorites(currentMedia)
                _uiState.update {
                    it.copy(
                        isInFavorites = true
                    )
                }
            } catch (e: Exception) {
                Log.e("addMediaToFavorites()", e.stackTraceToString())
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
            } catch (e: Exception) {
                Log.e("removeMediaFromFavorites()", e.stackTraceToString())
            }
        }
    }

}