package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.myportfolio.mymovieapp.databinding.FragmentMovieDetailBinding
import com.myportfolio.mymovieapp.ui.adapters.MovieCastListAdapter
import com.myportfolio.mymovieapp.ui.adapters.MovieGenreListAdapter
import com.myportfolio.mymovieapp.ui.viewmodels.AppViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()

    private val genreAdapter = MovieGenreListAdapter()
    private val castAdapter = MovieCastListAdapter()

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
        binding.fragmentMovieDetailGenreList.adapter = genreAdapter
        binding.fragmentMovieDetailCastList.adapter = castAdapter
        loadMovieData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sharedViewModel.setCurrentGenreList(listOf())
        sharedViewModel.setCurrentCastList(listOf())
    }

    private fun loadMovieData() {
        Picasso.get()
            .load(sharedViewModel.getCurrentMedia().bigPoster)
            .fit().centerCrop()
            .into(binding.fragmentMovieDetailImage)

        binding.fragmentMovieDetailTitle.text = sharedViewModel.getCurrentMedia().mediaName
        binding.fragmentMovieDetailSubtitle.text = sharedViewModel.getCurrentMedia().releaseYear.toString()
        binding.fragmentMovieDetailSynopsis.text = sharedViewModel.getCurrentMedia().synopsis

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect {
                    genreAdapter.addData(it.currentGenreList.toMutableList())
                    castAdapter.addData(it.currentCastList.toMutableList())
                }
            }
        }
    }

}