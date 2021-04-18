package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentEditQuestionSetBinding
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.viewmodel.QuestionSetViewModel


class EditQuestionSetFragment : Fragment() {
    private lateinit var binding: FragmentEditQuestionSetBinding
    private lateinit var mQuestionSetViewModel: QuestionSetViewModel

    private val args by navArgs<EditQuestionSetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditQuestionSetBinding.inflate(inflater, container, false)
        mQuestionSetViewModel = ViewModelProvider(this).get(QuestionSetViewModel::class.java)

        binding.editQuestionSetEditText.setText(args.currentQuestionSet.questionSetName)

        binding.editQuestionSetButton.setOnClickListener {
            editQuestionSet()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun editQuestionSet() {
        val name = binding.editQuestionSetEditText.text.toString()
        if (inputCheck(name)) {
            val editedQuestionSet = QuestionSet(args.currentQuestionSet.questionSetId, name)
            mQuestionSetViewModel.editQuestionSet(editedQuestionSet)
            Toast.makeText(requireContext(), "Update successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editQuestionSetFragment_to_listQuestionSetFragment)
        } else {
            Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteQuestionSet() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mQuestionSetViewModel.deleteQuestionSet(args.currentQuestionSet)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentQuestionSet.questionSetName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editQuestionSetFragment_to_listQuestionSetFragment)
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ${args.currentQuestionSet.questionSetName}?")
        builder.setMessage("Are you sure you want to delete?")
        builder.create().show()
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete) {
            deleteQuestionSet()
        }
        return super.onOptionsItemSelected(item)
    }
}