package com.example.quizapp.view.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizTimeBinding
import com.example.quizapp.model.relations.QuestionAndAnswer


class QuizTimeFragment : Fragment() {
    lateinit var binding: FragmentQuizTimeBinding
    private lateinit var listButtons: ArrayList<Button>
    private lateinit var listQuestionsAndAnswer: ArrayList<QuestionAndAnswer>
    private lateinit var currentQuestion: QuestionAndAnswer

    private lateinit var pointCounter: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var timer: CountDownTimer

    private var listAnswers = arrayListOf<String>()
    private var answerCounter = 0
    private var secondsPassed = 0

    private val args by navArgs<QuizTimeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizTimeBinding.inflate(inflater, container, false)

        progressBar = binding.quizTimeProgressBar
        progressBar.progress = 0

        timer = MyTimer(10000, 100)
        timer.start()

        listQuestionsAndAnswer = args.listQuestionsAndAnswers.toCollection(ArrayList())
        listQuestionsAndAnswer.forEach {
            listAnswers.add(it.answer.answerText)
        }

        pointCounter = binding.quizTimePointCounter
        listButtons = arrayListOf(
            binding.quizTimeAnswer1, binding.quizTimeAnswer2,
            binding.quizTimeAnswer3, binding.quizTimeAnswer4
        )

        setupQuestion()

        binding.quizTimeAnswer1.setOnClickListener {
            checkAnswer(binding.quizTimeAnswer1.text.toString())
        }
        binding.quizTimeAnswer2.setOnClickListener {
            checkAnswer(binding.quizTimeAnswer2.text.toString())
        }
        binding.quizTimeAnswer3.setOnClickListener {
            checkAnswer(binding.quizTimeAnswer3.text.toString())
        }
        binding.quizTimeAnswer4.setOnClickListener {
            checkAnswer(binding.quizTimeAnswer4.text.toString())
        }

        return binding.root
    }

    private fun setupQuestion() {
        for (button in listButtons) {
            button.text = ""
        }

        val tempArray = arrayListOf<String>()
        listAnswers.forEach {
            tempArray.add(it)
        }

        currentQuestion = listQuestionsAndAnswer.random()
        binding.quizTimeQuestion.text = currentQuestion.question.questionText
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
        // może niech to sie jakoś skaluje z czasem
        if (buttonText == currentQuestion.answer.answerText) {
            pointCounter.text = (pointCounter.text.toString().toInt() + 1).toString()
            timer.cancel()
            timer = MyTimer(10000, 100)
            timer.start()
        }
        setupQuestion()
    }

    private fun finishQuizTime() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_finish_quiz)

        val string = "You got ${binding.quizTimePointCounter.text} points in $secondsPassed seconds"
        dialog.findViewById<TextView>(R.id.dialogFinishQuizTextView2).text = string
        dialog.findViewById<Button>(R.id.dialogFinishQuizButtonReturn).setOnClickListener {
            val action = QuizTimeFragmentDirections.actionQuizTimeFragmentToListQuestionSetFragment()
            dialog.dismiss()
            findNavController().navigate(action)
        }

        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    inner class MyTimer(var time: Long, interval: Long) : CountDownTimer(time, interval) {
        override fun onTick(millisUntilFinished: Long) {
            val progress = (millisUntilFinished/100).toInt()
            progressBar.progress = progress
        }

        override fun onFinish() {
            finishQuizTime()
        }

    }

}