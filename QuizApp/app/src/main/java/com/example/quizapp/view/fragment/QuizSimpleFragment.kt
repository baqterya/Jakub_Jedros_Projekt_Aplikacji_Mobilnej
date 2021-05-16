package com.example.quizapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.DialogFinishQuizBinding
import com.example.quizapp.databinding.FragmentQuizSimpleBinding
import com.example.quizapp.model.Answer
import com.example.quizapp.model.relations.QuestionAndAnswer

class QuizSimpleFragment : Fragment() {
    lateinit var binding: FragmentQuizSimpleBinding
    private lateinit var listButtons: ArrayList<Button>
    private lateinit var listQuestionsAndAnswer: ArrayList<QuestionAndAnswer>
    private lateinit var currentQuestion: QuestionAndAnswer

    private lateinit var pointCounter: TextView

    private var listAnswers = arrayListOf<String>()
    private var answerCounter = 0

    private val args by navArgs<QuizSimpleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizSimpleBinding.inflate(inflater, container, false)

        listQuestionsAndAnswer = args.listQuestionsAndAnswers.toCollection(ArrayList())
        checkAmount()

        listQuestionsAndAnswer.forEach {
            listAnswers.add(it.answer.answerText)
        }

        pointCounter = binding.quizSimplePointCounter
        listButtons = arrayListOf(
            binding.quizSimpleAnswer1, binding.quizSimpleAnswer2,
            binding.quizSimpleAnswer3, binding.quizSimpleAnswer4
        )

        setupQuestion()

        binding.quizSimpleAnswer1.setOnClickListener {
            checkAnswer(binding.quizSimpleAnswer1.text.toString())
        }
        binding.quizSimpleAnswer2.setOnClickListener {
            checkAnswer(binding.quizSimpleAnswer2.text.toString())
        }
        binding.quizSimpleAnswer3.setOnClickListener {
            checkAnswer(binding.quizSimpleAnswer3.text.toString())
        }
        binding.quizSimpleAnswer4.setOnClickListener {
            checkAnswer(binding.quizSimpleAnswer4.text.toString())
        }

        return binding.root
    }

    private fun setupQuestion() {
        for (button in listButtons) {
            button.text = ""
        }

        // kotlin copies array list by reference :(
        val tempArray = arrayListOf<String>()
        listAnswers.forEach {
            tempArray.add(it)
        }

        currentQuestion = listQuestionsAndAnswer.random()
        binding.quizSimpleQuestion.text = currentQuestion.question.questionText
        tempArray.remove(currentQuestion.answer.answerText)

        listButtons.shuffle()
        listButtons[0].text = currentQuestion.answer.answerText

        for (i in 1..3) {
            val randomAnswer = tempArray.random()
            tempArray.remove(randomAnswer)

            listButtons[i].text = randomAnswer
        }
    }

    private fun checkAnswer(buttonText: String) {
        if (buttonText == currentQuestion.answer.answerText) {
            pointCounter.text = (pointCounter.text.toString().toInt() + 1).toString()
        }

        if (++answerCounter >= 6) {
            finishQuizSimple()
        }
        else {
            setupQuestion()
        }
    }

    private fun finishQuizSimple() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_finish_quiz)

        val string = "You got ${pointCounter.text} points"
        dialog.findViewById<TextView>(R.id.dialogFinishQuizTextView2).text = string
        dialog.findViewById<Button>(R.id.dialogFinishQuizButtonReturn).setOnClickListener {
            val action = QuizSimpleFragmentDirections.actionQuizSimpleFragmentToListQuestionSetFragment()
            findNavController().navigate(action)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun checkAmount() {
        if (listQuestionsAndAnswer.size < 5) {
            binding.quizSimpleToFewTextView.visibility = View.VISIBLE
            binding.quizSimpleQuestion.visibility = View.INVISIBLE
            binding.quizSimpleAnswer1.visibility = View.INVISIBLE
            binding.quizSimpleAnswer2.visibility = View.INVISIBLE
            binding.quizSimpleAnswer3.visibility = View.INVISIBLE
            binding.quizSimpleAnswer4.visibility = View.INVISIBLE
        }
    }
}