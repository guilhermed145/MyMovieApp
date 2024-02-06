package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R

class MovieGenreListAdapter(private val genreList: MutableList<String>):
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

    fun updateGenreListItems(newGenreList: List<String>) {
        val diffCallback = GenreDiffCallback(this.genreList, newGenreList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.genreList.clear()
        this.genreList.addAll(newGenreList)
        diffResult.dispatchUpdatesTo(this)
    }

}


class GenreDiffCallback(oldGenreList: List<String>, newGenreList: List<String>) :
    DiffUtil.Callback() {

    private val myOldGenreList: List<String>
    private val myNewGenreList: List<String>

    init {
        myOldGenreList = oldGenreList
        myNewGenreList = newGenreList
    }

    override fun getOldListSize(): Int {
        return myOldGenreList.size
    }

    override fun getNewListSize(): Int {
        return myNewGenreList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return myOldGenreList[oldItemPosition] == myNewGenreList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return myOldGenreList[oldItemPosition] == myNewGenreList[newItemPosition]
    }

}
