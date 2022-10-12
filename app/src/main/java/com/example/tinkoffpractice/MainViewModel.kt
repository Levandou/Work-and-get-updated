package com.example.tinkoffpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val a = MutableLiveData<Int>()

    init {
        setValue()
    }

    private fun setValue() {
        a.postValue(20)
    }
}