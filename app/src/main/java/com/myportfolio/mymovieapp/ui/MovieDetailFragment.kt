package com.myportfolio.mymovieapp.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentMovieDetailBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieCastListAdapter
import com.myportfolio.mymovieapp.ui.adapters.MovieGenreListAdapter
import com.myportfolio.mymovieapp.ui.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private val genreAdapter = MovieGenreListAdapter()
    private val castAdapter = MovieCastListAdapter()
    private var currentMedia = Media()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        @Suppress("DEPRECATION")
        currentMedia = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable("currentMedia", Media::class.java) ?: Media()
        else
            arguments?.getSerializable("currentMedia") as Media
        movieDetailViewModel.getGenreList(currentMedia)
        movieDetailViewModel.getCastList(currentMedia)
        movieDetailViewModel.checkIfMediaIsInFavorites(currentMedia)

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
        loadAddToFavoritesButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieDetailViewModel.setCurrentGenreList(listOf())
        movieDetailViewModel.setCurrentCastList(listOf())
    }

    private fun loadMovieData() {
        if (currentMedia.bigPoster != "") {
            Picasso.get()
                .load(currentMedia.bigPoster)
                .fit().centerCrop()
                .into(binding.fragmentMovieDetailImage)
        }

        binding.fragmentMovieDetailTitle.text = currentMedia.mediaName
        binding.fragmentMovieDetailSubtitle.text = currentMedia.releaseYear.toString()
        binding.fragmentMovieDetailSynopsis.text = currentMedia.synopsis

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.uiState.collect {
                    genreAdapter.addData(it.genreList.toMutableList())
                    castAdapter.addData(it.castList.toMutableList())
                }
            }
        }
    }

    private fun loadAddToFavoritesButton() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.uiState.collect {
                    if (movieDetailViewModel.uiState.value.isInFavorites) {
                        binding.fragmentMovieDetailFavoriteFab.setImageResource(R.drawable.ic_favorite)
                    } else {
                        binding.fragmentMovieDetailFavoriteFab.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
            }
        }

        binding.fragmentMovieDetailFavoriteFab.setOnClickListener {
            if (movieDetailViewModel.uiState.value.isInFavorites) {
                movieDetailViewModel.removeMediaFromFavorites(currentMedia)
//                binding.fragmentMovieDetailFavoriteFab.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(context, "Removed from favorites!", Toast.LENGTH_SHORT).show()
            } else{
                movieDetailViewModel.addMediaToFavorites(currentMedia)
//                binding.fragmentMovieDetailFavoriteFab.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}