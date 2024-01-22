package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentHomeBinding
import com.myportfolio.mymovieapp.model.Media

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()

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
        bindMovieListView(
            view = binding.movieList1.root,
            title = getString(R.string.popular),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = binding.movieList2.root,
            title = getString(R.string.top_rated),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = binding.movieList3.root,
            title = getString(R.string.upcoming),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = binding.seriesList1.root,
            title = getString(R.string.popular),
            mediaType = getString(R.string.series),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = binding.seriesList2.root,
            title = getString(R.string.top_rated),
            mediaType = getString(R.string.series),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = binding.seriesList3.root,
            title = getString(R.string.upcoming),
            mediaType = getString(R.string.series),
            list = sharedViewModel.mediaListTests,
        )
    }

    private fun bindMovieListView(
        view: MovieListView, title: String, mediaType: String, list: List<Media>
    ) {
        val adapter = MovieListAdapter(list) {
            sharedViewModel.setCurrentMedia(it)
            view.findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment)
        }
        val onClick: (View) -> Unit = {
            sharedViewModel.setCurrentCategory(title)
            sharedViewModel.setCurrentMediaType(mediaType)
            view.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        view.bindList(title = title, adapter = adapter, onClick = onClick)
    }

}