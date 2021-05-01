package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListQuestionSetBinding
import com.example.quizapp.view.adapter.ListQuestionSetAdapter
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel
import com.example.quizapp.viewmodel.QuestionSetViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListQuestionSetFragment : Fragment() {
    private lateinit var binding: FragmentListQuestionSetBinding

    private lateinit var mQuestionSetViewModel: QuestionSetViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListQuestionSetBinding.inflate(inflater, container, false)

        // TODO recycler view nie update'uje sie wtedy kiedy powinien

        // Recycler View
        val recyclerView = binding.questionSetRecyclerView
        val adapter = ListQuestionSetAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // QuestionSetViewModel
        mQuestionSetViewModel = ViewModelProvider(this).get(QuestionSetViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        mQuestionSetViewModel.allQuestionSets.observe(viewLifecycleOwner, Observer { questionSet ->
            adapter.setData(questionSet)
        })

        binding.addQuestionSetFAB.setOnClickListener {
            findNavController().navigate(R.id.action_listQuestionSetFragment_to_addQuestionSetFragment)
        }

        // Thread.sleep(500)
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
            mQuestionSetViewModel.deleteAllQuestionSets()
            mCategoryViewModel.deleteAllCategories()
            mQuestionAndAnswerViewModel.deleteAllQuestionsAndAnswers()
            Toast.makeText(requireContext(), "All sets successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ALL sets?")
        builder.setMessage("Are you sure you want to delete ALL?")
        builder.create().show()
    }

}