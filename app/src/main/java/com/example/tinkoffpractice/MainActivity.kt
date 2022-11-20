package com.example.tinkoffpractice

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.presentation.R


class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val btn_map = findViewById<LinearLayout>(R.id.ll_map)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_navigation)
        navController = navHostFragment.navController

        findViewById<LinearLayout>(R.id.ll_map).setOnClickListener {
            /*val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync {

            }*/
         //   mapFragment.getMapAsync(this)
            navController.navigate(R.id.mapFragment)
        }

        findViewById<LinearLayout>(R.id.ll_news).setOnClickListener {
            navController.navigate(R.id.newsFragment)
        }
    }
}