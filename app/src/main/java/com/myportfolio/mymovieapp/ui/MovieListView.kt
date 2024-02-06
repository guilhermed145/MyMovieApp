package com.myportfolio.mymovieapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter

class MovieListView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    fun bindList(title: String, adapter: MovieListAdapter, onClick: ((View) -> Unit)) {
        findViewById<TextView>(R.id.category_title)?.text = "$title >"
        findViewById<RecyclerView>(R.id.home_recycler_view).adapter = adapter
        setOnClickListener(onClick)
    }


}