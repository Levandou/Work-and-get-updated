package com.example.tinkoffpractice.news

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.tinkoffpractice.MainViewModel
import com.example.tinkoffpractice.detail_news.DetailNewsDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {
    lateinit var rv: RecyclerView
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        viewModel.newsPage.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it.distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        rv = view.findViewById(R.id.rv_category)
        adapter = NewsAdapter {
            DetailNewsDialog(it).show(requireActivity().supportFragmentManager, "tag")
        }

        rv.adapter = adapter.withLoadStateFooter(NewsLoadStateAdapter { adapter.retry() })
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 40
                outRect.top = 20
                outRect.right = 40
                outRect.bottom = 20
            }
        })
    }
}