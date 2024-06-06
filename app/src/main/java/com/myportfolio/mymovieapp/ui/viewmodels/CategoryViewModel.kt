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

data class CategoryUiState (
    val currentMediaList: List<Media> = listOf(),
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun getCategoryList(type: String, category: String) {
        val mediaList = mutableListOf<Media>()
        viewModelScope.launch {
            try {
                if (type == Constants.movieTypeId) {
                    for (i in 1..5) {
                        mediaList.addAll(repository.getMoviesInCategory(category, i))
                    }
                } else {
                    for (i in 1..5) {
                        mediaList.addAll(repository.getSeriesInCategory(category, i))
                    }
                }
                _uiState.update {
                    it.copy(
                        currentMediaList = mediaList
                    )
                }
            } catch (e: Exception) {
                Log.e("getCategoryList()", e.stackTraceToString())
                _uiState.update {
                    it.copy(
                        currentMediaList = listOf()
                    )
                }
            }
        }
    }

}