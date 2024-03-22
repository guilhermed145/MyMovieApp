package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myportfolio.mymovieapp.App
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AppUiState (
    val currentCategoryTitle: String = "",
    val currentMediaType: String = "",
    val currentMediaList: List<Media> = listOf(),
    val currentMedia: Media =
        Media(
            id = 0,
            mediaName ="",
            bigPoster = "",
            mediumPoster = "",
            smallPoster = "",
            releaseYear = 0,
            genreIdList = listOf(),
            mediaType = Constants.movieTypeId
        ),
    var currentCastList: List<CastMember> = listOf(),
    var currentGenreList: List<String> = listOf(),
)

class AppViewModel(private val repository: MovieRepository): ViewModel () {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentCategory(categoryTitle: String) {
        _uiState.update {
            it.copy(currentCategoryTitle = categoryTitle)
        }
    }

    fun setCurrentMediaType(mediaType: String) {
        _uiState.update {
            it.copy(currentMediaType = mediaType)
        }
    }

    fun setCurrentMediaList(list: List<Media>) {
        _uiState.update {
            it.copy(currentMediaList = list)
        }
    }

    fun setCurrentMedia(media: Media) {
        _uiState.update {
            it.copy(currentMedia = media)
        }
        getCurrentCastList()
        getCurrentGenreList()
    }

    fun getCurrentMedia(): Media {
        return uiState.value.currentMedia
    }

    fun getCategoryFragmentTitle(): String {
        val mediaTitle: String = uiState.value.currentCategoryTitle
        return if (uiState.value.currentMediaType == Constants.movieTypeId) {
            mediaTitle.plus(" Movies")
        } else {
            mediaTitle.plus(" TV Series")
        }
    }

    fun setCurrentGenreList(newGenreList: List<String>) {
        _uiState.update {
            it.copy(currentGenreList = newGenreList)
        }
    }

    fun setCurrentCastList(newCastList: List<CastMember>) {
        _uiState.update {
            it.copy(currentCastList = newCastList)
        }
    }

    private fun getCurrentGenreList() {
        viewModelScope.launch {
            try {
                val apiData = repository.getGenreList(uiState.value.currentMediaType)
                val genreList: MutableList<String> = mutableListOf()
                for (genreId in uiState.value.currentMedia.genreIdList) {
                    for (genre in apiData) {
                        if (genreId == genre.id) {
                            genreList.add(genre.name)
                        }
                    }
                }
                _uiState.update {
                    it.copy(
                        currentGenreList = genreList
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCurrentGenreList()", e)
                _uiState.update {
                    it.copy(
                        currentGenreList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCurrentGenreList()", e)
                _uiState.update {
                    it.copy(
                        currentGenreList = listOf()
                    )
                }
            }
        }
    }

    private fun getCurrentCastList() {
        viewModelScope.launch {
            try {
                val apiData = if (uiState.value.currentMediaType == Constants.seriesTypeId) {
                    repository.getSeriesCast(uiState.value.currentMedia.id)
                } else {
                    repository.getMovieCast(uiState.value.currentMedia.id)
                }
                _uiState.update {
                    it.copy(
                        currentCastList = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCurrentCastList()", e)
                _uiState.update {
                    it.copy(
                        currentCastList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCurrentCastList()", e)
                _uiState.update {
                    it.copy(
                        currentCastList = listOf()
                    )
                }
            }
        }
    }

    /**
     * Factory for the viewModel that takes the repository as a dependency.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as App)
                val appRepository = application.container.appRepository
                AppViewModel(repository = appRepository)
            }
        }
    }

}