package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentAddElementBinding
import com.example.quizapp.databinding.FragmentListQuestionSetsBinding
import com.example.quizapp.view.activity.MainActivity


class AddElementFragment : Fragment() {

    lateinit var binding: FragmentAddElementBinding
    private lateinit var editQuestionSet: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddElementBinding.bind(view)
        editQuestionSet = binding.addElementEdittext


        val submitButton = binding.addElementSubmitButton
        submitButton.setOnClickListener {
            setFragmentResult(
                ListQuestionSetFragment.ADD_QUESTION_SET,
                bundleOf(
                    ListQuestionSetFragment.QUESTION_SET_NAME to editQuestionSet.text.toString()
                )
            )
            val fragment = ListQuestionSetFragment()
            (activity as MainActivity).changeFragment(fragment)
        }
    }
}