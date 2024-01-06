package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.model.Media

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories(view)
    }

    private fun loadCategories(view: View) {
        bindMovieListView(
            view = view.findViewById(R.id.movie_list1),
            title = getString(R.string.popular),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = view.findViewById(R.id.movie_list2),
            title = getString(R.string.top_rated),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = view.findViewById(R.id.movie_list3),
            title = getString(R.string.upcoming),
            mediaType = getString(R.string.movies),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = view.findViewById(R.id.series_list1),
            title = getString(R.string.popular),
            mediaType = getString(R.string.series),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = view.findViewById(R.id.series_list2),
            title = getString(R.string.top_rated),
            mediaType = getString(R.string.series),
            list = sharedViewModel.mediaListTests,
        )
        bindMovieListView(
            view = view.findViewById(R.id.series_list3),
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