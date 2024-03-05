package com.myportfolio.mymovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.CastMember
import com.squareup.picasso.Picasso

class MovieCastListAdapter(private val castList: MutableList<CastMember>):
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
        if (castList[position].actorImage.trim().isEmpty()) {
            holder.actorImage.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Picasso.get()
                .load(castList[position].actorImage)
                .into(holder.actorImage)
        }
        holder.actorName.text = castList[position].actorName
        holder.characterName.text = castList[position].characterName
    }

    fun updateCastListItems(newCastList: List<CastMember>) {
        val diffCallback = CastDiffCallback(this.castList, newCastList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.castList.clear()
        this.castList.addAll(newCastList)
        diffResult.dispatchUpdatesTo(this)
    }

}

class CastDiffCallback(oldCastList: List<CastMember>, newCastList: List<CastMember>) :
    DiffUtil.Callback() {

    private val myOldCastList: List<CastMember>
    private val myNewCastList: List<CastMember>

    init {
        myOldCastList = oldCastList
        myNewCastList = newCastList
    }

    override fun getOldListSize(): Int {
        return myOldCastList.size
    }

    override fun getNewListSize(): Int {
        return myNewCastList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return myOldCastList[oldItemPosition] == myNewCastList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val hasSameName = myOldCastList[oldItemPosition].actorName ==
                myNewCastList[newItemPosition].actorName
        val isSameCharacter = myOldCastList[oldItemPosition].characterName ==
                myNewCastList[newItemPosition].characterName
        return hasSameName && isSameCharacter
    }

}