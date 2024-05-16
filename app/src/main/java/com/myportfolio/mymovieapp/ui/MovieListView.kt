package com.myportfolio.mymovieapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter

class MovieListView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val adapter = MovieListAdapter()

    fun bindList(
        title: String,
        mediaList: List<Media>,
        onTitleClicked: ((View) -> Unit),
        onMediaClicked: (Media) -> Unit
    ) {
        context.getString(R.string.movies)
        findViewById<TextView>(R.id.category_title).text = context.getString(R.string.movie_list_view_title, title)
        findViewById<RecyclerView>(R.id.home_recycler_view).adapter = adapter
        adapter.addData(mediaList)
        adapter.setOnMediaClickListener(onMediaClicked)
        setOnClickListener(onTitleClicked)
    }

}