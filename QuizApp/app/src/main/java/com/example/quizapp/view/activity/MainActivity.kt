package com.example.quizapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.view.fragment.ListQuestionSetFragment
import com.example.quizapp.view.fragment.SettingsFragment

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
                R.id.nav_question_sets -> changeFragment(0)
                R.id.nav_settings -> changeFragment(1)
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

    fun changeFragment(fragmentCode: Int) {
        when(fragmentCode) {
            0 -> {
                Toast.makeText(this, "GO TO QUESTION SETS", Toast.LENGTH_SHORT).show()
                val transaction = this.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.recyclerViewContainer, ListQuestionSetFragment())
                transaction.addToBackStack("QuestionSetList")
                transaction.commit()
            }
            1 -> {
                Toast.makeText(this, "GO TO SETTINGS", Toast.LENGTH_SHORT).show()
                val transaction = this.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.recyclerViewContainer, SettingsFragment())
                transaction.addToBackStack("Settings")
                transaction.commit()
            }
        }
    }

}