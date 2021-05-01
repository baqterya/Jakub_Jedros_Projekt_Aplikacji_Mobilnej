package com.example.quizapp.view.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentAddCategoryBinding
import com.example.quizapp.model.Category
import com.example.quizapp.viewmodel.CategoryViewModel

class AddCategoryFragment : Fragment() {
    private lateinit var binding: FragmentAddCategoryBinding
    private lateinit var mCategoryViewModel: CategoryViewModel

    private val args by navArgs<AddCategoryFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        binding.addCategoryButton.setOnClickListener {
            insertCategoryToDatabase()
        }

        return binding.root
    }

    private fun insertCategoryToDatabase() {
        val categoryName = binding.addCategoryEditText.text.toString()
        if (inputCheck(categoryName)) {
            val category = Category(0, categoryName, args.questionSetId)
            mCategoryViewModel.insertCategory(category)
            Toast.makeText(requireContext(), "YATTA", Toast.LENGTH_SHORT).show()
            val action = AddCategoryFragmentDirections.actionAddCategoryFragmentToListCategoryFragment(args.questionSetId)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please enter the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}