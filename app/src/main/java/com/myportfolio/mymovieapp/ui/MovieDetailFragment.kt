package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.myportfolio.mymovieapp.databinding.FragmentMovieDetailBinding
import com.myportfolio.mymovieapp.ui.adapters.MovieCastListAdapter
import com.myportfolio.mymovieapp.ui.adapters.MovieGenreListAdapter
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
            .load(sharedViewModel.getCurrentMedia().bigPoster)
            .fit().centerCrop()
            .into(binding.fragmentMovieDetailImage)
        binding.fragmentMovieDetailTitle.text = sharedViewModel.getCurrentMedia().mediaName
        binding.fragmentMovieDetailSubtitle.text = sharedViewModel.getCurrentMedia().releaseYear.toString()
        binding.fragmentMovieDetailSynopsis.text = sharedViewModel.getCurrentMedia().synopsis
        val genreAdapter =
            MovieGenreListAdapter(sharedViewModel.uiState.value.currentGenreList.toMutableList())
        binding.fragmentMovieDetailGenreList.adapter = genreAdapter
        sharedViewModel.setGenreAdapterUpdate(genreAdapter::updateGenreListItems)
        val castAdapter =
            MovieCastListAdapter(sharedViewModel.uiState.value.currentCastList.toMutableList())
        binding.fragmentMovieDetailCastList.adapter = castAdapter
        sharedViewModel.setCastAdapterUpdate(castAdapter::updateCastListItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sharedViewModel.setCurrentGenreList(listOf())
        sharedViewModel.setCurrentCastList(listOf())
    }

}