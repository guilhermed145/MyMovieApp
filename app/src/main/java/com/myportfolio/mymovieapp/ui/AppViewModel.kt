package com.myportfolio.mymovieapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.mymovieapp.model.Media

class AppViewModel: ViewModel () {

    private var currentCategoryTitle: String = ""
    private var currentMediaType: String = ""
    private var currentMedia: Media = Media("", "", 0)

    var mediaListTests: List<Media> = listOf(
        Media("Shrek", "", 2001),
        Media("Shrek 2", "", 2002),
        Media("Shrek 3", "", 2003),
        Media("Shrek 4", "", 2004),
        Media("Shrek 5", "", 2005),
        Media("Shrek 6", "", 2006),
    )

    fun setCurrentCategory(categoryTitle: String) {
        currentCategoryTitle = categoryTitle
    }

    fun setCurrentMediaType(mediaType: String) {
        currentMediaType = mediaType
    }

    fun setCurrentMedia(media: Media) {
        currentMedia = media
    }

    fun getCurrentMedia(): Media {
        return currentMedia
    }

    fun getCategoryFragmentTitle(): String {
        return currentCategoryTitle.plus(" $currentMediaType")
    }

}