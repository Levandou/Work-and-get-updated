package com.example.tinkoffpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.tinkoffpractice.news.NewsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
  //      rv = findViewById(R.id.rv_category)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel.newsPage.observe(this) {
            lifecycleScope.launch {
                it.distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        viewModel.newsList.observe(this)
        {
            Log.d("asasddas", it.toString())
        }

        viewModel.news.observe(this)
        {
            Log.d("asasddas", it.toString())
        }
    }

    private fun setupRecyclerView() {
        rv = findViewById(R.id.rv_category)
        adapter = NewsAdapter()
        rv.adapter = adapter
    }
}