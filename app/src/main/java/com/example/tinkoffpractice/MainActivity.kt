package com.example.tinkoffpractice

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.databinding.FragmentDetailNewsDialogBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    var menuChoosed: View? = null
    var menuMap: LinearLayout? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuChoosed = findViewById(R.id.back_choosed)
        menuMap = findViewById(R.id.ll_map)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_navigation)
        navController = navHostFragment.navController

        findViewById<LinearLayout>(R.id.ll_map).setOnClickListener {
            navController.navigate(R.id.mapFragment)
            findViewById<LinearLayout>(R.id.ll_map).let {
                moveChoosedMenu(findViewById(R.id.back_choosed), it.x + it.width/2 )
            }
        }

        findViewById<LinearLayout>(R.id.ll_news).setOnClickListener {
            navController.navigate(R.id.newsFragment)
            findViewById<LinearLayout>(R.id.ll_news).let {
                moveChoosedMenu(findViewById(R.id.back_choosed), it.x + it.width/2 )
            }
        }
    }

    fun moveChoosedMenu(view: View, newX: Float) {
        val newXAnim = ObjectAnimator.ofFloat(view, "x", newX - view.width/2)
        newXAnim.duration = 500
        AnimatorSet().apply {
            play(newXAnim)
            start()
        }
    }
}