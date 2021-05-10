package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListQuestionAndAnswerBinding
import com.example.quizapp.view.adapter.ListQuestionAndAnswerAdapter
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel

class ListQuestionAndAnswerFragment : Fragment() {
    private lateinit var binding: FragmentListQuestionAndAnswerBinding
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    private val args by navArgs<ListQuestionAndAnswerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentListQuestionAndAnswerBinding.inflate(inflater, container, false)

        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)
        mQuestionAndAnswerViewModel.setCategory(args.categoryId)

        val recyclerView = binding.questionAndAnswerRecyclerView
        val adapter = ListQuestionAndAnswerAdapter(mQuestionAndAnswerViewModel.questionAndAnswerFromCategory)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mQuestionAndAnswerViewModel.questionAndAnswerFromCategory.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })

        binding.addQuestionAndAnswerFAB.setOnClickListener {
            val action = ListQuestionAndAnswerFragmentDirections.actionListQuestionAndAnswerFragmentToAddQuestionAndAnswerFragment(args.categoryId, args.questionSetId)
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDelete) {
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mQuestionAndAnswerViewModel.deleteQuestionsAndAnswersFromCategory(args.categoryId)
            Toast.makeText(requireContext(), "All questions successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ALL questions?")
        builder.setMessage("Are you sure you want to delete ALL?")
        builder.create().show()
    }
}