package com.myportfolio.mymovieapp.ui

import androidx.lifecycle.ViewModel
import com.myportfolio.mymovieapp.model.Movie

class AppViewModel: ViewModel () {

    var movieListTest: List<Movie> = listOf(
        Movie("Shrek", "", 2001),
        Movie("Shrek 2", "", 2002),
        Movie("Shrek 3", "", 2003)
    )

}