package com.example.quizapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.view.fragment.FragmentCommunicator
import com.example.quizapp.view.fragment.ListQuestionSetFragment
import com.example.quizapp.view.fragment.SettingsFragment

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.recyclerViewContainer, ListQuestionSetFragment())
        fragmentTransaction.commit()

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_question_sets -> changeFragment(fragment = ListQuestionSetFragment())
                R.id.nav_settings -> changeFragment(fragment = SettingsFragment())
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun changeFragment(fragment: Fragment) {

        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.recyclerViewContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun passDataCom(textInput: String) {
        val bundle = Bundle()
        bundle.putString("TEXT_INPUT", textInput)

        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragment = ListQuestionSetFragment()
        transaction.replace(R.id.recyclerViewContainer, fragment)
        transaction.addToBackStack("QuestionSetList")
        transaction.commit()
    }

}