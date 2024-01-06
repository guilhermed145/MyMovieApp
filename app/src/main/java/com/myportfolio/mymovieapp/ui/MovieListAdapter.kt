package com.myportfolio.mymovieapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.Media

class MovieListAdapter(private val mediaList: List<Media>, private val onItemClicked: (Media) -> Unit):
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val image: ImageView = view.findViewById(R.id.movie_image)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieReleaseYear: TextView = view.findViewById(R.id.movie_release_year)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        // Setup custom accessibility delegate to set the text read
        return MovieListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val media: Media = mediaList[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.movieTitle.text = media.mediaName
        holder.movieReleaseYear.text = media.releaseYear.toString()
        holder.itemView.setOnClickListener {
            onItemClicked(media)
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

}