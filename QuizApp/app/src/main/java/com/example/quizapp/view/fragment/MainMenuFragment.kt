package com.example.quizapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentMainMenuBinding
import com.example.quizapp.view.activity.RecyclerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainMenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mStartButton = view.findViewById<Button>(R.id.main_menu_start_button)
        val mSetManager = view.findViewById<Button>(R.id.main_menu_set_manager_button)
        val mSettingsFAB = view.findViewById<FloatingActionButton>(R.id.main_menu_settings_button)

        mStartButton.setOnClickListener {
            changeActivity()
        }

        mSetManager.setOnClickListener {
            changeActivity()
        }

        mSettingsFAB.setOnClickListener {
            changeFragment()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainMenuFragment().apply {

            }
    }

    private fun changeActivity() {
        Toast.makeText(this.context, "GO TO RECYCLER", Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, RecyclerActivity().javaClass)
        startActivity(intent)
    }

    private fun changeFragment() {
        Toast.makeText(this.context, "GO TO SETTINGS", Toast.LENGTH_SHORT).show()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_menu_container, SettingsFragment())
        transaction?.addToBackStack("MainMenu")
        transaction?.commit()
    }
}