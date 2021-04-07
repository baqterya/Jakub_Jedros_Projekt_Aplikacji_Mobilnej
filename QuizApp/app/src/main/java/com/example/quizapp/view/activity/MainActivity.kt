package com.example.quizapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.quizapp.R
import com.example.quizapp.view.fragment.QuestionSetListFragment
import com.example.quizapp.view.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.recyclerViewContainer, QuestionSetListFragment())
        fragmentTransaction.commit()

        navigationView.setNavigationItemSelectedListener {
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
                transaction.replace(R.id.recyclerViewContainer, QuestionSetListFragment())
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