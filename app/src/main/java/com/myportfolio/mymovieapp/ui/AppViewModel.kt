package com.myportfolio.mymovieapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media

class AppViewModel: ViewModel () {

    private var currentCategoryTitle: String = ""
    private var currentMediaType: String = ""
    private var currentMedia: Media =
        Media("", "", 0, genreList = listOf(), castList = listOf())

    var genreListTest = listOf("Romance", "Action", "Comedy", "Fantasy", "Fiction")
    var castListTest = listOf(
        CastMember("", "Mike Myers", "Shrek"),
        CastMember("", "Eddie Murphy", "Donkey"),
        CastMember("", "Chris miller", "Geppetto"),
        CastMember("", "Conrad Vernon", "Gingerbread Man"),
        CastMember("", "John Lithgow", "Lord Farquaad"),
    )
    var mediaListTests: List<Media> = listOf(
        Media("Shrek", "", 2001, genreList = genreListTest, castList = castListTest),
        Media("Shrek 2", "", 2002, genreList = genreListTest, castList = castListTest),
        Media("Shrek 3", "", 2003, genreList = genreListTest, castList = castListTest),
        Media("Shrek 4", "", 2004, genreList = genreListTest, castList = castListTest),
        Media("Shrek 5", "", 2005, genreList = genreListTest, castList = castListTest),
        Media("Shrek 6", "", 2006, genreList = genreListTest, castList = castListTest),
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