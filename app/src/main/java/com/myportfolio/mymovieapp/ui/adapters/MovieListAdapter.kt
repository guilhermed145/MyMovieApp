package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.Media
import com.squareup.picasso.Picasso


class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    enum class MediaPosterSize {
        SMALL, BIG
    }

    private var mediaPosterSize: MediaPosterSize = MediaPosterSize.SMALL
    private var onItemClicked: ((Media) -> Unit)? = null

    private val differCallBack = object: DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallBack)

    inner class MovieListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.movie_image)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieReleaseYear: TextView = view.findViewById(R.id.movie_release_year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        if (mediaPosterSize == MediaPosterSize.BIG) {
            val mediaCard = layout.findViewById<CardView>(R.id.movie_image_card)
            mediaCard.layoutParams.width = 492
            mediaCard.layoutParams.height = 640
            mediaCard.radius = 20f
        }
        // Setup custom accessibility delegate to set the text read
        return MovieListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val media: Media = differ.currentList[position]
        val mediaPoster: String = if (mediaPosterSize == MediaPosterSize.BIG) {
            media.mediumPoster
        } else {
            media.smallPoster
        }
        if (mediaPoster.trim().isEmpty()) {
            holder.image.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Picasso.get()
                .load(mediaPoster)
                .into(holder.image)
        }

        holder.movieTitle.text = media.mediaName
        holder.movieReleaseYear.text = media.releaseYear.toString()
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(media)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addData(mediaList: List<Media>) {
        differ.submitList(mediaList)
    }

    fun setOnMediaClickListener(listener: (Media) -> Unit) {
        onItemClicked = listener
    }

    fun setMediaPosterSizeToBig() {
        mediaPosterSize = MediaPosterSize.BIG
    }

}