package com.myportfolio.mymovieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class containing all values the FavoritesFragment needs to display information to the user.
data class FavoritesUiState (
    val favoriteMediasList: List<Media> = listOf()
)

// This ViewModel is responsible for returning a list of all movies and tv series that has been
// marked as favorite by the user.
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFavoriteMedias()
    }

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
            } catch (e: Exception) {
                Log.e("getCategoryList()", e.stackTraceToString())
                _uiState.update {
                    it.copy(
                        favoriteMediasList = listOf()
                    )
                }
            }
        }
    }

}