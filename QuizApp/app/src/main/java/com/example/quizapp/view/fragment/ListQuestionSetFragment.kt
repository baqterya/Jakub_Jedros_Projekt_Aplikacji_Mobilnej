package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentListQuestionSetBinding
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Category
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.view.adapter.ListQuestionSetAdapter
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel
import com.example.quizapp.viewmodel.QuestionSetViewModel

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

        // QuestionSetViewModel
        mQuestionSetViewModel = ViewModelProvider(this).get(QuestionSetViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        // Recycler View
        val recyclerView = binding.questionSetRecyclerView
        val adapter = ListQuestionSetAdapter(
            mQuestionSetViewModel.allQuestionSets, mQuestionSetViewModel,
            mCategoryViewModel, mQuestionAndAnswerViewModel
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mQuestionSetViewModel.allQuestionSets.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()

            // example
            if (it.isEmpty() and notPopulated()) {
                populateDatabase()
                saveData()
            }
        })

        binding.addQuestionSetFAB.setOnClickListener {
            addQuestionSetDialog()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbar_menu, menu)
        menu.findItem(R.id.menuSettings).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete) {
            deleteAll()
        }
        if (item.itemId == R.id.menuSettings) {
            findNavController().navigate(R.id.action_listQuestionSetFragment_to_settingsFragment)
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

    private fun addQuestionSetDialog() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_add_question_set)

        dialog.findViewById<Button>(R.id.dialogAddQuestionSetButton).setOnClickListener {
            val questionSetName = dialog.findViewById<EditText>(R.id.dialogAddQuestionSetEditText).text.toString()
            if (inputCheck(questionSetName)) {
                val questionSet = QuestionSet(0, questionSetName)
                mQuestionSetViewModel.insertQuestionSet(questionSet)
                Toast.makeText(requireContext(), "Question Set added!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please enter the name", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    private fun notPopulated() : Boolean {
        val sharedPreferences = context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val notPopulated = sharedPreferences?.getBoolean("IS_NOT_POPULATED", true)
        return notPopulated ?: true
    }

    private fun saveData() {
        val notPopulated = false
        val sharedPreferences = context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putBoolean("IS_NOT_POPULATED", notPopulated)
        }?.apply()
    }

    private fun populateDatabase() {
        val japanese = QuestionSet(1, "Japanese (example)")

        val categoriesJapanese = mutableListOf(
            Category(1, "Verbs", japanese.questionSetId),
            Category(2, "Food", japanese.questionSetId),
        )
        val questionsAndAnswersJapanese = mutableListOf(
            QuestionAndAnswer(
                Question(0, "のむ", 1, 1),
                Answer(0, "to drink", "のむ", 1, 1)
            ),
            QuestionAndAnswer(
                Question(1, "たべる", 1, 1),
                Answer(1, "to eat", "たべる", 1, 1)
            ),
            QuestionAndAnswer(
                Question(2, "あける", 1, 1),
                Answer(2, "to open", "あける", 1, 1)
            ),
            QuestionAndAnswer(
                Question(3, "かく", 1, 1),
                Answer(3, "to write", "かく", 1, 1)
            ),
            QuestionAndAnswer(
                Question(4, "つくる", 1, 1),
                Answer(4, "to make", "つくる", 1, 1)
            ),
            QuestionAndAnswer(
                Question(5, "はしる", 1, 1),
                Answer(5, "to run", "はしる", 1, 1)
            ),
            QuestionAndAnswer(
                Question(6, "えび", 2, 1),
                Answer(6, "shrimp", "えび", 2, 1)
            ),
            QuestionAndAnswer(
                Question(7, "りんご", 2, 1),
                Answer(7, "apple", "りんご", 2, 1)
            )
        )

        mQuestionSetViewModel.insertQuestionSet(japanese)
        categoriesJapanese.forEach {
            mCategoryViewModel.insertCategory(it)
        }
        questionsAndAnswersJapanese.forEach {
            mQuestionAndAnswerViewModel.insertQuestionAndAnswer(it.question, it.answer)
        }
    }
}