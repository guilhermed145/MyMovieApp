package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myportfolio.mymovieapp.Application
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class FavoritesUiState (
    val favoriteMediasList: List<Media> = listOf(),
)

class FavoritesViewModel(private val repository: MovieRepository): ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    fun getFavoriteMedias() {
        val mediaList = mutableListOf<Media>()
        viewModelScope.launch {
            try {
                mediaList.addAll(repository.getFavorites())
                _uiState.update {
                    it.copy(
                        favoriteMediasList = mediaList
                    )
                }
            } catch (e: IOException) {
                Log.e("IO EXCEPTION", "getCategoryList()", e)
                _uiState.update {
                    it.copy(
                        favoriteMediasList = listOf()
                    )
                }
            } catch (e: HttpException) {
                Log.e("HTTP EXCEPTION", "getCategoryList()", e)
                _uiState.update {
                    it.copy(
                        favoriteMediasList = listOf()
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
                FavoritesViewModel(repository = appRepository)
            }
        }
    }

}