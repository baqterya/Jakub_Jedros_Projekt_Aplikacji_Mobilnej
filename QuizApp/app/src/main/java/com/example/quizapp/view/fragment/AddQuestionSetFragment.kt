package com.example.quizapp.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentAddQuestionSetBinding
import com.example.quizapp.databinding.FragmentListQuestionSetBinding
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.viewmodel.QuestionSetViewModel

class AddQuestionSetFragment : Fragment() {
    private lateinit var binding: FragmentAddQuestionSetBinding

    private lateinit var mQuestionSetViewModel: QuestionSetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddQuestionSetBinding.inflate(inflater, container, false)

        mQuestionSetViewModel = ViewModelProvider(this).get(QuestionSetViewModel::class.java)

        binding.addQuestionSetButton.setOnClickListener {
            insertQuestionSetToDatabase()
        }

        return binding.root
    }

    private fun insertQuestionSetToDatabase() {
        val questionSetName = binding.addQuestionSetEditText.text.toString()

        if (inputCheck(questionSetName)) {
            val questionSet = QuestionSet(0, questionSetName)
            mQuestionSetViewModel.insertQuestionSet(questionSet)
            Toast.makeText(requireContext(), "YATTA", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addQuestionSetFragment_to_listQuestionSetFragment)
        } else {
            Toast.makeText(requireContext(), "Please enter the name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }
}