package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentCategoryBinding
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()

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
        binding.categoryFragmentTitle.text = sharedViewModel.getCategoryFragmentTitle()
        val mediaList = sharedViewModel.uiState.value.currentMediaList
        val adapter = MovieListAdapter(mediaList.toMutableList()) {
            sharedViewModel.setCurrentMedia(it)
            val action = R.id.action_categoryFragment_to_movieDetailFragment
            this.findNavController().navigate(action)
        }
        binding.categoryRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}