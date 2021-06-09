package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizWritingBinding
import com.example.quizapp.model.relations.QuestionAndAnswer
import kotlin.collections.ArrayList


class QuizWritingFragment : Fragment() {
    lateinit var binding: FragmentQuizWritingBinding
    private lateinit var listQuestionsAndAnswer: ArrayList<QuestionAndAnswer>
    private lateinit var currentQuestion: QuestionAndAnswer
    private lateinit var pointCounter: TextView

    private var answerCounter = 0

    private val args by navArgs<QuizSimpleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizWritingBinding.inflate(inflater, container, false)

        pointCounter = binding.quizWritingPointCounter
        listQuestionsAndAnswer = args.listQuestionsAndAnswers.toCollection(ArrayList())

        setupQuestion()

        binding.quizWritingCheckButton.setOnClickListener {
            checkAnswer()
        }

        return binding.root
    }

    private fun setupQuestion() {
        currentQuestion = listQuestionsAndAnswer.random()
        binding.quizWritingQuestion.text = currentQuestion.question.questionText
        binding.quizWritingEditText.text.clear()
    }

    private fun checkAnswer() {
        if (binding.quizWritingEditText.text.toString().equals(currentQuestion.answer.answerText, ignoreCase = true)) {
            pointCounter.text = (pointCounter.text.toString().toInt() + 1).toString()
        }

        if (++answerCounter >= args.questionAmount) {
            finishQuizWriting()
        }
        else {
            setupQuestion()
        }
    }

    private fun finishQuizWriting() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_finish_quiz)

        val string = "You got ${pointCounter.text} points"
        dialog.findViewById<TextView>(R.id.dialogFinishQuizTextView2).text = string
        dialog.findViewById<Button>(R.id.dialogFinishQuizButtonReturn).setOnClickListener {
            val action = QuizWritingFragmentDirections.actionQuizWritingFragmentToListQuestionSetFragment()
            dialog.dismiss()
            findNavController().navigate(action)
        }

        dialog.show()
    }
}