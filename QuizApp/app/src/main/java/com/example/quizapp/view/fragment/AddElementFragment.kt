package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentAddElementBinding
import com.example.quizapp.databinding.FragmentListQuestionSetsBinding


class AddElementFragment : Fragment() {

    lateinit var binding: FragmentAddElementBinding
    private lateinit var editQuestionSet: EditText
    private lateinit var communicator: FragmentCommunicator

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

        communicator = activity as FragmentCommunicator
        // TODO change interface to viewmodel

        val submitButton = binding.addElementSubmitButton
        submitButton.setOnClickListener {
            val listQuestionSetFragment = ListQuestionSetFragment()
            communicator.passDataCom(binding.addElementEdittext.text.toString())
        }
    }
}