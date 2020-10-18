package com.paceraudio.colorsweep.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paceraudio.wire.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

//    private var color: MutableLiveData<Color> = MutableLiveData()
    private var _colors: MutableLiveData<List<Color>> = MutableLiveData(listOf())
    var colors: LiveData<List<Color>> = _colors
    private val colorManager = ColorManager(2, Sweeper())
    private var step = 0
    private var job: Job? = null

    fun setUp() {
    }


//    fun loadInitialColors() {
//        color.postValue(Color(MAX, MAX, MIN, MIN))
//    }

    fun startSweep() {
        job = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                colorManager.updateColors(step)
                step++
                if (isActive) {
                    withContext(Dispatchers.Main) {
                        _colors.value = colorManager.obtainColors()
                    }
                }
                delay(20L)
            }
        }
    }

    fun stopSweep() {
        job?.cancel()
    }
}