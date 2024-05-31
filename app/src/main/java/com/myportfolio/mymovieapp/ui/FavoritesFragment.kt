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
import com.myportfolio.mymovieapp.ui.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private val adapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel.getFavoriteMedias()
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

        val onMediaClicked: (Media) -> Unit = {
            val movieDetailFragmentBundle = Bundle()
            movieDetailFragmentBundle.putSerializable("currentMedia", it)
            view.findNavController().navigate(R.id.action_favoritesFragment_to_movieDetailFragment, movieDetailFragmentBundle)
        }
        binding.categoryRecyclerView.adapter = adapter
        adapter.setOnMediaClickListener(onMediaClicked)
        adapter.setMediaPosterSizeToBig()

        loadFavoriteMedias()
    }

    private fun loadFavoriteMedias() {

        binding.categoryFragmentTitle.text = getString(R.string.favorites_fragment_title)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.uiState.collect {
                    adapter.addData(it.favoriteMediasList)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getFavoriteMedias()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}