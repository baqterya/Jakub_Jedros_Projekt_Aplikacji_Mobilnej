package com.example.quizapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.QuestionSetApplication
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.FragmentListQuestionSetsBinding
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.view.activity.MainActivity
import com.example.quizapp.view.adapter.ListQuestionSetAdapter
import com.example.quizapp.viewmodel.QuestionSetViewModel
import com.example.quizapp.viewmodel.QuestionSetViewModelFactory

class ListQuestionSetFragment : Fragment() {

    lateinit var binding: FragmentListQuestionSetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_question_sets, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListQuestionSetsBinding.bind(view)
        val recyclerview = binding.questionSetRecyclerview
        val adapter = ListQuestionSetAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)

        setFragmentResultListener(ADD_QUESTION_SET) {_, result ->
            val name = result.getString(QUESTION_SET_NAME)
            if (!name.isNullOrEmpty()) {
                val questionSet = QuestionSet(0, name)
                (activity as MainActivity).questionSetViewModel.allQuestionSets.observe(viewLifecycleOwner, Observer {
                        questionSets -> questionSets.let { adapter.submitList(it) }
                })
            }
        }

        binding.fabAddQuestionSet.setOnClickListener {
            val fragment = AddElementFragment()
            (activity as MainActivity).changeFragment(fragment)
        }
    }




    companion object {
        const val ADD_QUESTION_SET = "add_question_set"
        const val QUESTION_SET_NAME = "question_set_name"
    }

}