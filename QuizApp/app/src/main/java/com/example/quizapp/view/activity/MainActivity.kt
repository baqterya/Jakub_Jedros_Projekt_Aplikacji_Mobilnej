package com.example.quizapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.R
import com.example.quizapp.view.fragment.MainMenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_menu_container, MainMenuFragment())
        fragmentTransaction.commit()
    }
}