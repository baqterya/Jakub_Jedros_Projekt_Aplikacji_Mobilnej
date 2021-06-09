package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListQuestionAndAnswerBinding
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
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
        val adapter = ListQuestionAndAnswerAdapter(
            mQuestionAndAnswerViewModel.questionAndAnswerFromCategory,
            mQuestionAndAnswerViewModel
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mQuestionAndAnswerViewModel.questionAndAnswerFromCategory.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })

        binding.addQuestionAndAnswerFAB.setOnClickListener {
            addQuestionAndAnswerDialog()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbar_menu, menu)
        menu.findItem(R.id.menuSettings).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDelete) {
            deleteAll()
        }
        if (item.itemId == R.id.menuSettings) {
            findNavController().navigate(R.id.action_listQuestionAndAnswerFragment_to_settingsFragment)
        }
        if (item.itemId == R.id.menuHelp) {
            val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.dialog_help)
            dialog.findViewById<Button>(R.id.helpGotItButton).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
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

    private fun addQuestionAndAnswerDialog() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_add_question_and_answer)

        dialog.findViewById<Button>(R.id.dialogAddQuestionAndAnswerButton).setOnClickListener {
            val questionText = dialog.findViewById<EditText>(R.id.dialogAddQuestionEditText).text.toString()
            val answerText = dialog.findViewById<EditText>(R.id.dialogAddAnswerEditText).text.toString()
            if (inputCheck(questionText, answerText)) {
                val question = Question(0, questionText, args.categoryId, args.questionSetId)
                val answer = Answer(0, answerText, questionText, args.categoryId, args.questionSetId)
                mQuestionAndAnswerViewModel.insertQuestionAndAnswer(question, answer)
                Toast.makeText(requireContext(), "Question and Answer added!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter the name", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun inputCheck(name1: String, name2: String): Boolean {
        return !(TextUtils.isEmpty(name1) && TextUtils.isEmpty(name2))
    }
}