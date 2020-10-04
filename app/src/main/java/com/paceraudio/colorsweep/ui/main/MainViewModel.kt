package com.paceraudio.colorsweep.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paceraudio.wire.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private var color: MutableLiveData<Color> = MutableLiveData()

    fun obtainColor(): LiveData<Color?> {
        return color
    }

    fun loadInitialColors() {
        color.postValue(Color(MAX, MAX, MIN, MIN))
    }

    fun startSweep() {
        val sweep = Sweeper()
        viewModelScope.launch(Dispatchers.Default) {
            for (i in 0 until STEPS) {
                val red = sweep.calcValueAtStep(R_STEPS, i)
                val green = sweep.calcValueAtStep(G_STEPS, i)
                val blue = sweep.calcValueAtStep(B_STEPS, i)

                if (isActive) {
                    withContext(Dispatchers.Main) {
                        color.postValue(Color(MAX, red, green, blue))
                    }
                }
                delay(250L)
            }
        }
    }
}