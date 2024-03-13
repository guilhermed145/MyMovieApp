package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.CastMember
import com.squareup.picasso.Picasso

class MovieCastListAdapter:
    RecyclerView.Adapter<MovieCastListAdapter.MovieCastViewHolder>() {

    private val differCallBack = object: DiffUtil.ItemCallback<CastMember>() {
        override fun areItemsTheSame(oldItem: CastMember, newItem: CastMember): Boolean {
            return oldItem.actorName == newItem.actorName
        }

        override fun areContentsTheSame(oldItem: CastMember, newItem: CastMember): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallBack)

    inner class MovieCastViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val actorImage: ImageView = view.findViewById(R.id.movie_cast_list_item_image)
        val actorName: TextView = view.findViewById(R.id.movie_cast_list_item_title)
        val characterName: TextView = view.findViewById(R.id.movie_cast_list_item_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_cast_list_item, parent, false)
        return MovieCastViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        if (differ.currentList[position].actorImage.trim().isEmpty()) {
            holder.actorImage.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Picasso.get()
                .load(differ.currentList[position].actorImage)
                .into(holder.actorImage)
        }
        holder.actorName.text = differ.currentList[position].actorName
        holder.characterName.text = differ.currentList[position].characterName
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addData(castList: List<CastMember>) {
        differ.submitList(castList)
    }

}