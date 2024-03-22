package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myportfolio.mymovieapp.App
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class HomeUiState(
    var popularMovies: List<Media> = listOf(),
    var topRatedMovies: List<Media> = listOf(),
    var upcomingMovies: List<Media> = listOf(),
    var popularSeries: List<Media> = listOf(),
    var topRatedSeries: List<Media> = listOf(),
)

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
        getPopularSeries()
        getTopRatedSeries()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val apiData = repository.getPopularMovies()
                _uiState.update {
                    it.copy(
                        popularMovies = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getPopularMovies()", e)
                _uiState.update {
                    it.copy(
                        popularMovies = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getPopularMovies()", e)
                _uiState.update {
                    it.copy(
                        popularMovies = listOf()
                    )
                }
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                val apiData = repository.getTopRatedMovies()
                _uiState.update {
                    it.copy(
                        topRatedMovies = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getTopRatedMovies()", e)
                _uiState.update {
                    it.copy(
                        topRatedMovies = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getTopRatedMovies()", e)
                _uiState.update {
                    it.copy(
                        topRatedMovies = listOf()
                    )
                }
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                val apiData = repository.getUpcomingMovies()
                _uiState.update {
                    it.copy(
                        upcomingMovies = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getUpcomingMovies()", e)
                _uiState.update {
                    it.copy(
                        upcomingMovies = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getUpcomingMovies()", e)
                _uiState.update {
                    it.copy(
                        upcomingMovies = listOf()
                    )
                }
            }
        }
    }

    private fun getPopularSeries() {
        viewModelScope.launch {
            try {
                val apiData = repository.getPopularSeries()
                _uiState.update {
                    it.copy(
                        popularSeries = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getPopularSeries()", e)
                _uiState.update {
                    it.copy(
                        popularSeries = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getPopularSeries()", e)
                _uiState.update {
                    it.copy(
                        popularSeries = listOf()
                    )
                }
            }
        }
    }

    private fun getTopRatedSeries() {
        viewModelScope.launch {
            try {
                val apiData = repository.getTopRatedSeries()
                _uiState.update {
                    it.copy(
                        topRatedSeries = apiData
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getTopRatedMovies()", e)
                _uiState.update {
                    it.copy(
                        topRatedSeries = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getTopRatedMovies()", e)
                _uiState.update {
                    it.copy(
                        topRatedSeries = listOf()
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
                HomeViewModel(repository = appRepository)
            }
        }
    }

}