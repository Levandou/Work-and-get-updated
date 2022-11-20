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
import com.example.tinkoffpractice.detail_news.DetailNewsDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {
    private var rv: RecyclerView? = null
    private val viewModel by viewModel<NewsViewModel>()
    private var adapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = view.findViewById(R.id.rv_news)
        setupRecyclerView()

        requireActivity().lifecycleScope.launch {
            viewModel.loadCategoryList().distinctUntilChanged().collectLatest {
                adapter?.let { ad->
                    ad.submitData(it)
                }
            }
        }

        val a = adapter?.snapshot()
        val b = 0

     /*   viewModel.newsPage.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                it.distinctUntilChanged().collectLatest { pagingData ->
                    adapter?.submitData(pagingData)
                }
            }
        }*/
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter {
            DetailNewsDialog(it).show(requireActivity().supportFragmentManager, "tag")
        }
        rv?.let {
            it.adapter = adapter
        }

        rv?.adapter = adapter//.withLoadStateFooter(NewsLoadStateAdapter { adapter.retry() })
        rv?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 40
                outRect.top = 20
                outRect.right = 40
                outRect.bottom = 20
            }
        })
    }
}