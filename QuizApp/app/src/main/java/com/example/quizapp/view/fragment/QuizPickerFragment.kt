package com.example.quizapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel


class QuizPickerFragment : Fragment() {
    lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    lateinit var listQuestionAndAnswer: Array<QuestionAndAnswer>
    private val args by navArgs<QuizPickerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        if (args.categoryId == -1) {
            mQuestionAndAnswerViewModel.getAllQuestionsAndAnswersFromQuestionSet(args.questionSetId)

            mQuestionAndAnswerViewModel.questionAndAnswerFromQuestionSet.observe(viewLifecycleOwner, { list ->
                listQuestionAndAnswer = list.toTypedArray()
                val action = QuizPickerFragmentDirections.actionQuizPickerFragmentToQuizSimpleFragment(listQuestionAndAnswer)
                findNavController().navigate(action)
            })
        }
        else {
            mQuestionAndAnswerViewModel.setCategory(args.categoryId)

            mQuestionAndAnswerViewModel.questionAndAnswerFromCategory.observe(viewLifecycleOwner, { list ->
                listQuestionAndAnswer = list.toTypedArray()
                val action = QuizPickerFragmentDirections.actionQuizPickerFragmentToQuizSimpleFragment(listQuestionAndAnswer)
                findNavController().navigate(action)
            })
        }

        return inflater.inflate(R.layout.fragment_quiz_picker, container, false)
    }

}