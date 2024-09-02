package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class containing all values the HomeFragment needs to display information to the user.
data class HomeUiState (
    var popularMovies: List<Media> = listOf(),
    var topRatedMovies: List<Media> = listOf(),
    var upcomingMovies: List<Media> = listOf(),
    var popularSeries: List<Media> = listOf(),
    var topRatedSeries: List<Media> = listOf(),
)

// This ViewModel is responsible for returning a movie or tv series list for each of the categories
// available in the app.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

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
                val apiData = repository.getMoviesInCategory(Constants.popularCategoryId, 1)
                _uiState.update {
                    it.copy(
                        popularMovies = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getPopularMovies()", e.stackTraceToString())
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
                val apiData = repository.getMoviesInCategory(Constants.topRatedCategoryId, 1)
                _uiState.update {
                    it.copy(
                        topRatedMovies = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e( "getTopRatedMovies()", e.stackTraceToString())
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
                val apiData = repository.getMoviesInCategory(Constants.upcomingCategoryId, 1)
                _uiState.update {
                    it.copy(
                        upcomingMovies = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getUpcomingMovies()", e.stackTraceToString())
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
                val apiData = repository.getSeriesInCategory(Constants.popularCategoryId, 1)
                _uiState.update {
                    it.copy(
                        popularSeries = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getPopularSeries()", e.stackTraceToString())
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
                val apiData = repository.getSeriesInCategory(Constants.topRatedCategoryId, 1)
                _uiState.update {
                    it.copy(
                        topRatedSeries = apiData
                    )
                }
            } catch (e: Exception) {
                Log.e("getTopRatedSeries()", e.stackTraceToString())
                _uiState.update {
                    it.copy(
                        topRatedSeries = listOf()
                    )
                }
            }
        }
    }

}