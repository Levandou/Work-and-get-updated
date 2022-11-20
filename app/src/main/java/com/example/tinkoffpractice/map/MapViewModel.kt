package com.example.tinkoffpractice.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MapRepository
import com.example.domain.Points
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(
    private var mapRepository: MapRepository
): ViewModel() {
    val pointsList = MutableLiveData<List<Points>>()
    init {
        getPoints()
    }

    fun getPoints(){
        viewModelScope.launch(Dispatchers.IO) {
            pointsList.postValue(mapRepository.getPoints())
        }
    }
}