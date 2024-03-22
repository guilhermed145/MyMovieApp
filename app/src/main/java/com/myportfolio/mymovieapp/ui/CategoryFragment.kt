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
import com.myportfolio.mymovieapp.databinding.FragmentCategoryBinding
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.ui.adapters.MovieListAdapter
import com.myportfolio.mymovieapp.ui.viewmodels.AppViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AppViewModel by activityViewModels()

    private val adapter = MovieListAdapter()

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
        binding.categoryRecyclerView.adapter = adapter

        val onMediaClicked: (Media) -> Unit = {
            sharedViewModel.setCurrentMedia(it)
            view.findNavController().navigate(R.id.action_categoryFragment_to_movieDetailFragment)
        }
        adapter.setOnMediaClickListener(onMediaClicked)
        adapter.setMediaPosterSizeToBig()

        loadCategoryData()
    }

    private fun loadCategoryData() {
        binding.categoryFragmentTitle.text = sharedViewModel.getCategoryFragmentTitle()
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.uiState.collect {
                adapter.addData(it.currentMediaList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}