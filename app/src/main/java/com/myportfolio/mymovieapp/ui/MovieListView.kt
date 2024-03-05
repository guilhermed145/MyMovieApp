package com.myportfolio.mymovieapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.MovieListBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter

class MovieListView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    val adapter = MovieListAdapter()
    val recyclerView = findViewById<RecyclerView>(R.id.category_recycler_view)
    val categoryTitle = findViewById<TextView>(R.id.category_title)

    init {
        inflate(context, R.layout.movie_list, this)
    }

    fun bindList( title: String, mediaList: List<Media>, onTitleClicked: ((View) -> Unit), onMediaClicked: (Media) -> Unit ) {
        findViewById<TextView>(R.id.category_title).text = "$title >"
        findViewById<RecyclerView>(R.id.category_recycler_view).adapter = adapter
        adapter.addData(mediaList)
        adapter.setOnMediaClickListener(onMediaClicked)
        setOnClickListener(onTitleClicked)
    }


}