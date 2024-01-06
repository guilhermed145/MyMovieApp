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

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    private val sharedViewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.category_fragment_title)
            .text = sharedViewModel.getCategoryFragmentTitle()
        val adapter = MovieListAdapter(sharedViewModel.mediaListTests) {
            sharedViewModel.setCurrentMedia(it)
            val action = R.id.action_categoryFragment_to_movieDetailFragment
            this.findNavController().navigate(action)
        }
        view.findViewById<RecyclerView>(R.id.category_recycler_view).adapter = adapter
    }

}