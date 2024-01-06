package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.myportfolio.mymovieapp.R

class MovieDetailFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.fragment_movie_detail_title)
            .text = sharedViewModel.getCurrentMedia().mediaName
        view.findViewById<TextView>(R.id.fragment_movie_detail_release_year)
            .text = sharedViewModel.getCurrentMedia().releaseYear.toString()
    }

}