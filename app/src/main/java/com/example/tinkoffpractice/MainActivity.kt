package com.example.tinkoffpractice

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
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

        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 30
                outRect.right = 30
                outRect.top = 30
                outRect.bottom = 30
            }

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
            }

            override fun onDrawOver(c: Canvas, parent: RecyclerView) {
                super.onDrawOver(c, parent)
            }

            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                super.getItemOffsets(outRect, itemPosition, parent)

            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
            }

            override fun onDraw(c: Canvas, parent: RecyclerView) {
                super.onDraw(c, parent)
            }
        })

    }
}