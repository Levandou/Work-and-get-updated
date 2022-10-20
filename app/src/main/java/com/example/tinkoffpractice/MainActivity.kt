package com.example.tinkoffpractice

import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.tinkoffpractice.detail_news.DetailNewsDialog
import com.example.tinkoffpractice.news.NewsAdapter
import com.example.tinkoffpractice.news.NewsLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        super.onCreate(savedInstanceState)
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
        adapter = NewsAdapter {
            DetailNewsDialog(it).show(supportFragmentManager, "tag")
        }

        rv.adapter = adapter.withLoadStateFooter(NewsLoadStateAdapter { adapter.retry() })
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 24
                outRect.top = 12
                outRect.bottom = 12
            }
        })
    }
}