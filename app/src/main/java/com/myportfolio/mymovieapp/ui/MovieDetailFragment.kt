package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.myportfolio.mymovieapp.databinding.FragmentMovieDetailBinding

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
        binding.fragmentMovieDetailTitle.text = sharedViewModel.getCurrentMedia().mediaName
        binding.fragmentMovieDetailSubtitle.text = sharedViewModel.getCurrentMedia().releaseYear.toString()
        binding.fragmentMovieDetailGenreList.adapter =
            MovieGenreListAdapter(sharedViewModel.getCurrentMedia().genreList)
        binding.fragmentMovieDetailCastList.adapter =
            MovieCastListAdapter(sharedViewModel.getCurrentMedia().castList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}