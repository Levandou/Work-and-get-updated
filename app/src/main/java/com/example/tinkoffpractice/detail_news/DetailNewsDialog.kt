package com.example.tinkoffpractice.detail_news

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.News
import com.example.presentation.R
import com.example.presentation.databinding.FragmentDetailNewsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

class DetailNewsDialog(private val news: News?) : BottomSheetDialogFragment() {
    var baseHeightImage: Int? = null
    lateinit var binding: FragmentDetailNewsDialogBinding

    private var behaviorContent: BottomSheetBehavior<NestedScrollView>? = null

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailNewsDialogBinding.bind(inflater.inflate(R.layout.fragment_detail_news_dialog, container))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.isDraggable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val display: Display = requireActivity().windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val height = size.y

            behavior.maxHeight = height / 5 * 4
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startHeight = binding.flImage.getMeasurments().second
        var isImageClosed = true
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        baseHeightImage = binding.simpleImage.getMeasurments().second
        behaviorContent = BottomSheetBehavior.from(binding.scrollText)
        behaviorContent?.skipCollapsed = true
        binding.simpleImage.setOnClickListener {
                if (isImageClosed)
                    openFullImage( width)
                else closeFullImage(startHeight, width)
            isImageClosed = !isImageClosed
        }

        binding.title.text = news?.title
        binding.textOfNews.text = news?.text

        Glide.with(this)
            .load(
                if (!news?.urlPhoto.isNullOrEmpty()) "http://92.55.11.232:5000" + news?.urlPhoto
                else R.drawable.news_photo
            )
            .error(R.drawable.news_photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.simpleImage)
    }

    private fun closeFullImage(startHeight:Int , width: Int) {
        val layoutParams: ViewGroup.LayoutParams = binding.simpleImage.layoutParams
        layoutParams.height = startHeight
        binding.simpleImage.layoutParams = layoutParams

        val newYBottomContent = binding.simpleImage.y + startHeight
        val animator = ObjectAnimator.ofFloat(binding.scrollText, "y", newYBottomContent)
        animator.duration = 500
        AnimatorSet().apply {
            play(animator)
            start()
        }
    }

    fun openFullImage( width: Int) {
        val layoutParams: ViewGroup.LayoutParams = binding.simpleImage.layoutParams
        layoutParams.height = width
        binding.simpleImage.layoutParams = layoutParams

        val newYBottomContent = binding.simpleImage.y + width
        val animator = ObjectAnimator.ofFloat(binding.scrollText, "y", newYBottomContent)
        animator.duration = 500
        AnimatorSet().apply {
            play(animator)
            start()
        }
    }

    fun View.getMeasurments(): Pair<Int, Int> {
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width = measuredWidth
        val height = measuredHeight
        return width to height
    }
}