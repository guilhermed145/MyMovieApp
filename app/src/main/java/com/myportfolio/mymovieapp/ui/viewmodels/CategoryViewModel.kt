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
import com.myportfolio.mymovieapp.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class CategoryUiState (
    val currentMediaList: List<Media> = listOf(),
)

class CategoryViewModel(private val repository: MovieRepository): ViewModel () {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun getCategoryList(type: String, category: String) {
        val mediaList = mutableListOf<Media>()
        viewModelScope.launch {
            try {
                if (type == Constants.movieTypeId) {
                    for (i in 1..5) {
                        mediaList.addAll(repository.getMovies(category, i))
                    }
                } else {
                    for (i in 1..5) {
                        mediaList.addAll(repository.getSeries(category, i))
                    }
                }
                _uiState.update {
                    it.copy(
                        currentMediaList = mediaList
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCategoryList()", e)
                _uiState.update {
                    it.copy(
                        currentMediaList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCategoryList()", e)
                _uiState.update {
                    it.copy(
                        currentMediaList = listOf()
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
                CategoryViewModel(repository = appRepository)
            }
        }
    }

}