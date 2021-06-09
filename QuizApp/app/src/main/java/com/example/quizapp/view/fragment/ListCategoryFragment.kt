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
import com.example.quizapp.databinding.FragmentListCategoryBinding
import com.example.quizapp.model.Category
import com.example.quizapp.view.adapter.ListCategoryAdapter
import com.example.quizapp.viewmodel.CategoryViewModel
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel

class ListCategoryFragment : Fragment() {
    private lateinit var binding: FragmentListCategoryBinding
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    private val args by navArgs<ListCategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListCategoryBinding.inflate(inflater, container, false)

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mCategoryViewModel.setQuestionSet(args.questionSetId)
        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        val recyclerView = binding.categoryRecyclerView
        val adapter = ListCategoryAdapter(
            mCategoryViewModel.categoriesFromQuestionSet,
            mCategoryViewModel,
            mQuestionAndAnswerViewModel
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCategoryViewModel.categoriesFromQuestionSet.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })

        binding.addCategoryFAB.setOnClickListener {
            addCategoryDialog()
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
            findNavController().navigate(R.id.action_listCategoryFragment_to_settingsFragment)
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
            mCategoryViewModel.deleteAllCategoriesFromQuestionSet(args.questionSetId)
            mQuestionAndAnswerViewModel.deleteQuestionsAndAnswersFromQuestionSet(args.questionSetId)

            Toast.makeText(requireContext(), "All categories successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ALL categories?")
        builder.setMessage("Are you sure you want to delete ALL?")
        builder.create().show()
    }

    private fun addCategoryDialog() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_add_category)

        dialog.findViewById<Button>(R.id.dialogAddCategoryButton).setOnClickListener {
            val categoryName = dialog.findViewById<EditText>(R.id.dialogAddCategoryEditText).text.toString()
            if (inputCheck(categoryName)) {
                val category = Category(0, categoryName, args.questionSetId)
                mCategoryViewModel.insertCategory(category)
                Toast.makeText(requireContext(), "Category added!", Toast.LENGTH_SHORT).show()
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
}