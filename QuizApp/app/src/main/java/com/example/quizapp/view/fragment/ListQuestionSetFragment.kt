package com.example.quizapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.FragmentListQuestionSetsBinding
import com.example.quizapp.view.activity.MainActivity
import com.example.quizapp.view.adapter.ListQuestionSetAdapter

class ListQuestionSetFragment : Fragment() {

    lateinit var binding: FragmentListQuestionSetsBinding
    private lateinit var communicator: FragmentCommunicator
    var questionSetName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        questionSetName = arguments?.getString("QuestionSetList")
        return inflater.inflate(R.layout.fragment_list_question_sets, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListQuestionSetsBinding.bind(view)
        val recyclerview = binding.questionSetRecyclerview
        val adapter = ListQuestionSetAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)

        if (questionSetName != null)
            Toast.makeText(context, questionSetName.toString(), Toast.LENGTH_SHORT).show()

        binding.fabAddQuestionSet.setOnClickListener {
            val fragment = AddElementFragment()
            (activity as MainActivity).changeFragment(fragment)
        }
    }

    // TODO

}