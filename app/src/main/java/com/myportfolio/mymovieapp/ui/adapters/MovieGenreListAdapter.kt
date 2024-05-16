package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R

class MovieGenreListAdapter:
    RecyclerView.Adapter<MovieGenreListAdapter.MovieGenreViewHolder>() {

    private val differCallBack = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallBack)

    inner class MovieGenreViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val genreName: TextView = view.findViewById(R.id.movie_genre_list_item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_genre_list_item, parent, false)
        return MovieGenreViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.genreName.text = differ.currentList[position]
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addData(genreList: List<String>) {
        differ.submitList(genreList)
    }

}