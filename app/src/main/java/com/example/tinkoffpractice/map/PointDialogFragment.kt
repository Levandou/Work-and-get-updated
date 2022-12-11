package com.example.tinkoffpractice.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.Points
import com.example.presentation.R
import com.example.presentation.databinding.FragmentPointDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PointDialogFragment(private val point: Points?) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentPointDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointDialogBinding.bind(inflater.inflate(R.layout.fragment_point_dialog, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(
                if (!point?.photoPath.isNullOrEmpty()) "http://92.55.11.232:5000" + point?.photoPath
                else R.drawable.news_photo
            )
            .error(R.drawable.news_photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.simpleImage)

        binding.title.text = point?.title
        binding.textOfPoint.text = point?.text
        binding.data.text = point?.dateOfCreation
    }
}