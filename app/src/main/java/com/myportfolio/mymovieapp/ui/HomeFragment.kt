package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentHomeBinding
import com.myportfolio.mymovieapp.model.Media
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels(factoryProducer = {AppViewModel.Factory})

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
            sharedViewModel.uiState.collect {
                bindMovieListView(
                    view = binding.movieList1.root,
                    title = getString(R.string.popular),
                    mediaType = sharedViewModel.movieCategoryId,
                    list = it.popularMovies,
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.uiState.collect {
                bindMovieListView(
                    view = binding.movieList2.root,
                    title = getString(R.string.top_rated),
                    mediaType = sharedViewModel.movieCategoryId,
                    list = it.topRatedMovies,
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.uiState.collect {
                bindMovieListView(
                    view = binding.movieList3.root,
                    title = getString(R.string.upcoming),
                    mediaType = sharedViewModel.movieCategoryId,
                    list = it.upcomingMovies,
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.uiState.collect {
                bindMovieListView(
                    view = binding.seriesList1.root,
                    title = getString(R.string.popular),
                    mediaType = sharedViewModel.tvCategoryId,
                    list = it.popularSeries,
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.uiState.collect {
                bindMovieListView(
                    view = binding.seriesList2.root,
                    title = getString(R.string.top_rated),
                    mediaType = sharedViewModel.tvCategoryId,
                    list = it.topRatedSeries,
                )
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