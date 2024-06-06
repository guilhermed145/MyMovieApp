package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.myportfolio.mymovieapp.MainActivity
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentHomeBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.viewmodels.HomeViewModel
import com.myportfolio.mymovieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        (activity as MainActivity).showFavoritesButton()

        context?.deleteDatabase("movie_database") // remove later

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories()
    }

    override fun onDestroyView() {
        (activity as MainActivity).hideFavoritesButton()
        super.onDestroyView()
        _binding = null
    }

    private fun loadCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.movieList1,
                        category = Constants.popularCategoryId,
                        mediaType = Constants.movieTypeId,
                        list = it.popularMovies,
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.movieList2,
                        category = Constants.topRatedCategoryId,
                        mediaType = Constants.movieTypeId,
                        list = it.topRatedMovies,
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.movieList3,
                        category = Constants.upcomingCategoryId,
                        mediaType = Constants.movieTypeId,
                        list = it.upcomingMovies,
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.seriesList1,
                        category = Constants.popularCategoryId,
                        mediaType = Constants.seriesTypeId,
                        list = it.popularSeries,
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.seriesList2,
                        category = Constants.topRatedCategoryId,
                        mediaType = Constants.seriesTypeId,
                        list = it.topRatedSeries,
                    )
                }
            }
        }
    }

    private fun bindMovieListView(
        view: MovieListView, category: String, mediaType: String, list: List<Media>
    ) {
        val categoryTitle = when (category) {
            Constants.popularCategoryId -> getString(R.string.popular)
            Constants.topRatedCategoryId -> getString(R.string.top_rated)
            else -> getString(R.string.upcoming)
        }
        val onMediaClicked: (Media) -> Unit = {
            val movieDetailFragmentBundle = Bundle()
            movieDetailFragmentBundle.putSerializable("currentMedia", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, movieDetailFragmentBundle)
        }
        val onCategoryTitleClicked: (View) -> Unit = {
            val categoryFragmentBundle = Bundle()
            categoryFragmentBundle.putString("categoryTitle", categoryTitle)
            categoryFragmentBundle.putString("currentMediaCategory", category)
            categoryFragmentBundle.putString("currentMediaType", mediaType)
            view.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, categoryFragmentBundle)
        }
        view.bindList(
            title = categoryTitle,
            mediaList = list,
            onTitleClicked = onCategoryTitleClicked,
            onMediaClicked = onMediaClicked
        )
    }
}