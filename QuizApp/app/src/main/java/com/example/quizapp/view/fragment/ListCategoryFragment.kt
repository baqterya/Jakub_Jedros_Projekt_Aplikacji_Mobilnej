package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListCategoryBinding
import com.example.quizapp.view.adapter.ListCategoryAdapter
import com.example.quizapp.viewmodel.CategoryViewModel

class ListCategoryFragment : Fragment() {
    private lateinit var binding: FragmentListCategoryBinding
    private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)

        val recyclerView = binding.categoryRecyclerView
        val adapter = ListCategoryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mCategoryViewModel.allCategories.observe(viewLifecycleOwner, Observer { category ->
            adapter.setData(category)
        })

        return binding.root
    }


}