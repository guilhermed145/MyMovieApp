package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentHomeBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.viewmodels.AppViewModel
import com.myportfolio.mymovieapp.ui.viewmodels.HomeViewModel
import com.myportfolio.mymovieapp.util.Constants
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels(factoryProducer = { AppViewModel.Factory})
    private val homeViewModel: HomeViewModel by viewModels(factoryProducer = { HomeViewModel.Factory})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect {
                    bindMovieListView(
                        view = binding.movieList1.root,
                        title = getString(R.string.popular),
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
                        view = binding.movieList2.root,
                        title = getString(R.string.top_rated),
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
                        view = binding.movieList3.root,
                        title = getString(R.string.upcoming),
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
                        view = binding.seriesList1.root,
                        title = getString(R.string.popular),
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
                        view = binding.seriesList2.root,
                        title = getString(R.string.top_rated),
                        mediaType = Constants.seriesTypeId,
                        list = it.topRatedSeries,
                    )
                }
            }
        }
    }

    private fun bindMovieListView(
        view: MovieListView, title: String, mediaType: String, list: List<Media>
    ) {
        val onMediaClicked: (Media) -> Unit = {
            sharedViewModel.setCurrentMediaType(mediaType)
            sharedViewModel.setCurrentMedia(it)
            view.findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment)
        }
        val onTitleClicked: (View) -> Unit = {
            sharedViewModel.setCurrentCategory(title)
            sharedViewModel.setCurrentMediaType(mediaType)
            sharedViewModel.setCurrentMediaList(list)
            view.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        view.bindList(title = title, mediaList = list, onTitleClicked = onTitleClicked, onMediaClicked = onMediaClicked)
    }
}