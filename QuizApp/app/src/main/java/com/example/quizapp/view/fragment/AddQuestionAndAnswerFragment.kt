package com.example.quizapp.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.databinding.FragmentAddQuestionAndAnswerBinding
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel


class AddQuestionAndAnswerFragment : Fragment() {
    private lateinit var binding: FragmentAddQuestionAndAnswerBinding
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    private val args by navArgs<AddQuestionAndAnswerFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddQuestionAndAnswerBinding.inflate(inflater, container, false)

        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        binding.addQuestionAndAnswerButton.setOnClickListener {
            insertQuestionAndAnswerToDatabase()
        }

        return binding.root
    }


    /**
     * TODO jest jakiś problem z tym, że jak dodaję do bazy questionandanswer to się wywala
     * dlaczego? jeszcze nie wiem ale zostawiam to sobie z przyszłości
     */
    private fun insertQuestionAndAnswerToDatabase() {
        val questionText = binding.addQuestionEditText.text.toString()
        val answerText = binding.addAnswerEditText.text.toString()
        if (inputCheck(questionText, answerText)) {
            val question = Question(0, questionText, args.categoryId, args.questionSetId)
            val answer = Answer(0, answerText, questionText, args.categoryId, args.questionSetId)

            mQuestionAndAnswerViewModel.insertQuestionAndAnswer(question, answer)
            Toast.makeText(requireContext(), "YATTA", Toast.LENGTH_SHORT).show()
            val action = AddQuestionAndAnswerFragmentDirections.actionAddQuestionAndAnswerFragmentToListQuestionAndAnswerFragment(args.categoryId, args.questionSetId)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please enter the name", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(name1: String, name2: String): Boolean {
        return !(TextUtils.isEmpty(name1) && TextUtils.isEmpty(name2))
    }
}