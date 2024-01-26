package com.myportfolio.mymovieapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.CastMember

class MovieCastListAdapter(private val castList: List<CastMember>):
    RecyclerView.Adapter<MovieCastListAdapter.MovieCastViewHolder>() {

    class MovieCastViewHolder(view: View): RecyclerView.ViewHolder(view) {
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

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.actorImage.setImageResource(R.drawable.ic_launcher_background)
        holder.actorName.text = castList[position].actorName
        holder.characterName.text = castList[position].characterName
    }

}