package com.example.tinkoffpractice.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.Points
import com.example.presentation.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PointDialogFragment(private val point: Points?) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_point_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
    }
}