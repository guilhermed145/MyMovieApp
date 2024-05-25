package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myportfolio.mymovieapp.Application
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class MovieDetailUiState (
    var castList: List<CastMember> = listOf(),
    var genreList: List<String> = listOf(),
)

class MovieDetailViewModel(private val repository: MovieRepository): ViewModel () {

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
                val genreList: MutableList<String> = mutableListOf()
                for (genreId in media.genreIdList) {
                    for (genre in apiData) {
                        if (genreId == genre.id) {
                            genreList.add(genre.name)
                        }
                    }
                }
                _uiState.update {
                    it.copy(
                        genreList = genreList
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

    /**
     * Factory for the viewModel that takes the repository as a dependency.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as Application)
                val appRepository = application.container.appRepository
                MovieDetailViewModel(repository = appRepository)
            }
        }
    }

}