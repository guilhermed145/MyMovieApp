package com.myportfolio.mymovieapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.MovieListBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter

class MovieListView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val adapter = MovieListAdapter()

    private val binding: MovieListBinding by lazy {
        MovieListBinding.inflate(LayoutInflater.from(context), this, true)
    }

    // Binds all media data to this view.
    fun bindList(
        title: String,
        mediaList: List<Media>,
        onTitleClicked: ((View) -> Unit),
        onMediaClicked: (Media) -> Unit
    ) {
        binding.categoryTitle.text = context.getString(R.string.movie_list_view_title, title)
        binding.categoryTitle.setOnClickListener(onTitleClicked)
        binding.homeRecyclerView.adapter = adapter
        adapter.addData(mediaList)
        adapter.setOnMediaClickListener(onMediaClicked)
    }

}