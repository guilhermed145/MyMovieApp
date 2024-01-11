package com.myportfolio.mymovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.myportfolio.mymovieapp.R
import com.myportfolio.mymovieapp.databinding.FragmentCategoryBinding
import com.myportfolio.mymovieapp.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    private var binding: FragmentCategoryBinding? = null
    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.categoryFragmentTitle?.text = sharedViewModel.getCategoryFragmentTitle()
        val adapter = MovieListAdapter(sharedViewModel.mediaListTests) {
            sharedViewModel.setCurrentMedia(it)
            val action = R.id.action_categoryFragment_to_movieDetailFragment
            this.findNavController().navigate(action)
        }
        binding?.categoryRecyclerView?.adapter = adapter
//        view.findViewById<RecyclerView>(R.id.category_recycler_view).adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}