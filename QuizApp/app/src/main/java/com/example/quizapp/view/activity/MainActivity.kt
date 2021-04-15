package com.example.quizapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapp.QuestionSetApplication
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.view.fragment.ListQuestionSetFragment
import com.example.quizapp.view.fragment.SettingsFragment
import com.example.quizapp.viewmodel.QuestionSetViewModel
import com.example.quizapp.viewmodel.QuestionSetViewModelFactory

class MainActivity : AppCompatActivity() {

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

    val questionSetViewModel: QuestionSetViewModel by viewModels {
        QuestionSetViewModelFactory((QuestionSetApplication()).repository)
    }

}