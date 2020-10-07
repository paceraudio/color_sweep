package com.paceraudio.colorsweep.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paceraudio.wire.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private var color: MutableLiveData<Color> = MutableLiveData()
    private var _colors: MutableLiveData<List<Color>> = MutableLiveData(listOf())
    var colors: LiveData<List<Color>> = _colors
    private val sweeper = Sweeper()
    var step = 0
    var job: Job? = null

    fun setUp() {

    }

    fun startSweep() {
        job = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                val i = step % STEPS
                val red = sweeper.calcValueAtStep(R_STEPS, i)
                val green = sweeper.calcValueAtStep(G_STEPS, i)
                val blue = sweeper.calcValueAtStep(B_STEPS, i)
                step++
                if (isActive) {
                    withContext(Dispatchers.Main) {
                        _colors.value = listOf(Color(MAX, red, green, blue), Color(MAX, MAX - red, MAX - green, MAX - blue))
                    }
                }
                delay(10L)
            }
        }
    }

    fun stopSweep() {
        job?.cancel()
    }
}