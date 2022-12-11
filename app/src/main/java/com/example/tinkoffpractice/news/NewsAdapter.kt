package com.example.tinkoffpractice.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.News
import com.example.presentation.R
import java.util.*

class NewsAdapter(private val onItemClick: (News?) -> Unit) : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(
    NewsDiffCallback()
) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.apply {
            nameOfOffice.text = getItem(position)?.tagTitle
            dateOfCreation.text = getItem(position)?.dataOfCreation
            text.text = getItem(position)?.title

            Glide.with(this.itemView)
                .load(
                    if (!getItem(position)?.urlPhoto.isNullOrEmpty()) "http://92.55.11.232:5000" + getItem(position)?.urlPhoto
                    else R.drawable.news_photo
                )
                .error(R.drawable.news_photo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivNews)

            itemView.setOnClickListener {
                onItemClick(getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.new_item_news,
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