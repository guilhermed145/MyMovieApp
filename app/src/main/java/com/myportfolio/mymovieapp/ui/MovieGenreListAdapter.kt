package com.myportfolio.mymovieapp.ui

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myportfolio.mymovieapp.R

class MovieGenreListAdapter(private val genreList: List<String>):
    RecyclerView.Adapter<MovieGenreListAdapter.MovieGenreViewHolder>() {

    class MovieGenreViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val genreName: TextView = view.findViewById(R.id.movie_genre_list_item_text)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_genre_list_item, parent, false)
        return MovieGenreViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.genreName.text = genreList[position]
    }

}