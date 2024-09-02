package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentCategoryBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter
import com.myportfolio.mymovieapp.ui.viewmodels.CategoryViewModel
import com.myportfolio.mymovieapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// A Fragment class responsible of properly providing the UI that shows all movies and tv series
// available in a specific category.
@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val categoryViewModel: CategoryViewModel by viewModels()

    private val adapter = MovieListAdapter()

    private var categoryTitle = ""
    private var currentCategory = ""
    private var currentMediaType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryTitle = arguments?.getString("categoryTitle") ?: getString(R.string.movies)
        currentCategory = arguments?.getString("currentMediaCategory") ?: Constants.popularCategoryId
        currentMediaType = arguments?.getString("currentMediaType") ?: Constants.movieTypeId
        categoryViewModel.getCategoryList(currentMediaType, currentCategory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sends the user to the MovieDetailFragment when selecting one of the medias.
        // The selected media data will be send to the fragment as well, so it can display all
        // information regarding the media that was chosen by the user.
        val onMediaClicked: (Media) -> Unit = {
            val movieDetailFragmentBundle = Bundle()
            movieDetailFragmentBundle.putSerializable("currentMedia", it)
            view.findNavController().navigate(R.id.action_categoryFragment_to_movieDetailFragment, movieDetailFragmentBundle)
        }
        binding.categoryRecyclerView.adapter = adapter
        adapter.setOnMediaClickListener(onMediaClicked)
        adapter.setMediaPosterSizeToBig()

        loadCategoryData()
    }

    // Displays the title accordingly to the media type and the category specified.
    // Adds the proper media list to the MovieListAdapter used in this fragment.
    private fun loadCategoryData() {
        binding.categoryFragmentTitle.text = if (currentMediaType == Constants.movieTypeId) {
            categoryTitle.plus(" " + getString(R.string.movies))
        } else {
            categoryTitle.plus(" " + getString(R.string.series))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.uiState.collect {
                    adapter.addData(it.currentMediaList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}