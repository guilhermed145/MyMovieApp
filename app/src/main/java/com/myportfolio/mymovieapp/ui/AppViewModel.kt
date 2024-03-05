package com.myportfolio.mymovieapp.ui

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
            smallPoster = "",
            releaseYear = 0,
            genreIdList = listOf()
        ),
    var currentCastList: List<CastMember> = listOf(),
    var currentGenreList: List<String> = listOf(),
    var popularMovies: List<Media> = listOf(),
    var topRatedMovies: List<Media> = listOf(),
    var upcomingMovies: List<Media> = listOf(),
    var popularSeries: List<Media> = listOf(),
    var topRatedSeries: List<Media> = listOf(),
//    var popularMoviesAdapterUpdate: (List<Media>) -> Unit = {},
//    var topRatedMoviesAdapterUpdate: (List<Media>) -> Unit = {},
//    var upcomingMoviesAdapterUpdate: (List<Media>) -> Unit = {},
//    var popularSeriesAdapterUpdate: (List<Media>) -> Unit = {},
//    var topRatedSeriesAdapterUpdate: (List<Media>) -> Unit = {},
    var genreAdapterUpdate: (List<String>) -> Unit = {},
    var castAdapterUpdate: (List<CastMember>) -> Unit = {},
)

class AppViewModel(private val repository: MovieRepository): ViewModel () {

    val movieCategoryId = "movie"
    val tvCategoryId = "tv"
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
        getPopularSeries()
        getTopRatedSeries()
    }

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
        return if (uiState.value.currentMediaType == movieCategoryId) {
            mediaTitle.plus(" Movies")
        } else {
            mediaTitle.plus(" TV Series")
        }
    }

    fun setCurrentGenreList(newGenreList: List<String>) {
            _uiState.update {
                it.copy(currentGenreList = newGenreList)
            }
            uiState.value.genreAdapterUpdate(newGenreList)
        }

    fun setCurrentCastList(newCastList: List<CastMember>) {
        _uiState.update {
            it.copy(currentCastList = newCastList)
        }
        uiState.value.castAdapterUpdate(newCastList)
    }



//    fun setPopularMoviesAdapterUpdate(adapterUpdateFunction: (List<Media>) -> Unit) {
//        _uiState.update {
//            it.copy(popularMoviesAdapterUpdate = adapterUpdateFunction)
//        }
//    }
//
//    fun setTopRatedMoviesAdapterUpdate(adapterUpdateFunction: (List<Media>) -> Unit) {
//        _uiState.update {
//            it.copy(topRatedMoviesAdapterUpdate = adapterUpdateFunction)
//        }
//    }
//
//    fun setUpcomingMoviesAdapterUpdate(adapterUpdateFunction: (List<Media>) -> Unit) {
//        _uiState.update {
//            it.copy(upcomingMoviesAdapterUpdate = adapterUpdateFunction)
//        }
//    }
//
//    fun setPopularSeriesAdapterUpdate(adapterUpdateFunction: (List<Media>) -> Unit) {
//        _uiState.update {
//            it.copy(popularSeriesAdapterUpdate = adapterUpdateFunction)
//        }
//    }
//
//    fun setTopRatedSeriesAdapterUpdate(adapterUpdateFunction: (List<Media>) -> Unit) {
//        _uiState.update {
//            it.copy(topRatedSeriesAdapterUpdate = adapterUpdateFunction)
//        }
//    }

    fun setGenreAdapterUpdate(adapterUpdateFunction: (List<String>) -> Unit) {
        _uiState.update {
            it.copy(genreAdapterUpdate = adapterUpdateFunction)
        }
    }

    fun setCastAdapterUpdate(adapterUpdateFunction: (List<CastMember>) -> Unit) {
        _uiState.update {
            it.copy(castAdapterUpdate = adapterUpdateFunction)
        }
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
//            uiState.value.popularMoviesAdapterUpdate(uiState.value.popularMovies)
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
//            uiState.value.topRatedMoviesAdapterUpdate(uiState.value.topRatedMovies)
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
//            uiState.value.upcomingMoviesAdapterUpdate(uiState.value.upcomingMovies)
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
//            uiState.value.popularSeriesAdapterUpdate(uiState.value.popularSeries)
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
//            uiState.value.topRatedSeriesAdapterUpdate(uiState.value.topRatedSeries)
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
            uiState.value.genreAdapterUpdate(uiState.value.currentGenreList)
        }
    }

    private fun getCurrentCastList() {
        viewModelScope.launch {
            try {
                val apiData = if (uiState.value.currentMediaType == tvCategoryId) {
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
            uiState.value.castAdapterUpdate(uiState.value.currentCastList)
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