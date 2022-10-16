package com.example.tinkoffpractice.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.News
import com.example.presentation.R

class NewsAdapter: PagingDataAdapter<News,NewsAdapter.NewsViewHolder >(
    NewsDiffCallback()
) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.nameOfOffice.text = getItem(position)?.title
        holder.dateOfCreation.text = getItem(position)?.dataOfCreation
        holder.text.text = getItem(position)?.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_news,
            parent,
            false
        )
        return NewsViewHolder(view)
    }

    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val nameOfOffice = view.findViewById<TextView>(R.id.title_office)
        val dateOfCreation = view.findViewById<TextView>(R.id.news_date_creation)
        val text = view.findViewById<TextView>(R.id.text_news)
        val ivNews = view.findViewById<ImageView>(R.id.iv_news)
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
    }
}