package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.Media
import com.squareup.picasso.Picasso


class MovieListAdapter(
    private val mediaList: MutableList<Media>,
    private val onItemClicked: (Media) -> Unit,
):
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
        if (media.smallPoster.trim().isEmpty()) {
            holder.image.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Picasso.get()
                .load(media.smallPoster)
                .into(holder.image)
        }
        holder.movieTitle.text = media.mediaName
        holder.movieReleaseYear.text = media.releaseYear.toString()
        holder.itemView.setOnClickListener {
            onItemClicked(media)
        }
    }
    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun updateMediaListItems(newMediaList: List<Media>) {
        val diffCallback = MediaDiffCallback(this.mediaList, newMediaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mediaList.clear()
        this.mediaList.addAll(newMediaList)
        diffResult.dispatchUpdatesTo(this)
    }

}

class MediaDiffCallback(oldMediaList: List<Media>, newMediaList: List<Media>) :
    DiffUtil.Callback() {

    private val myOldMediaList: List<Media>
    private val myNewMediaList: List<Media>

    init {
        myOldMediaList = oldMediaList
        myNewMediaList = newMediaList
    }

    override fun getOldListSize(): Int {
        return myOldMediaList.size
    }

    override fun getNewListSize(): Int {
        return myNewMediaList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return myOldMediaList[oldItemPosition] == myNewMediaList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMedia: Media = myOldMediaList[oldItemPosition]
        val newMedia: Media = myNewMediaList[newItemPosition]
        return oldMedia.id == newMedia.id
    }

}
