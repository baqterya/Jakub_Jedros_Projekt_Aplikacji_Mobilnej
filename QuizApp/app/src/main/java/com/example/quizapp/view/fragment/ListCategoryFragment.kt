package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListCategoryBinding
import com.example.quizapp.view.adapter.ListCategoryAdapter
import com.example.quizapp.viewmodel.CategoryViewModel

class ListCategoryFragment : Fragment() {
    private lateinit var binding: FragmentListCategoryBinding
    private lateinit var mCategoryViewModel: CategoryViewModel

    private val args by navArgs<ListCategoryFragmentArgs>()

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
        mCategoryViewModel.getAllCategoriesFromQuestionSet(args.questionSetId).observe(viewLifecycleOwner, Observer { category ->
            adapter.setData(category)
        })

        binding.addCategoryFAB.setOnClickListener {
            val action = ListCategoryFragmentDirections.actionListCategoryFragmentToAddCategoryFragment(args.questionSetId)
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete) {
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mCategoryViewModel.deleteAllCategoriesFromQuestionSet(args.questionSetId)
            Toast.makeText(requireContext(), "All categories successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ALL categories?")
        builder.setMessage("Are you sure you want to delete ALL?")
        builder.create().show()

        // TODO EDIT CATEGORY AND finally finish the database
    }

}