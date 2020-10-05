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

//    fun obtainColor(): LiveData<com.paceraudio.wire.Color?> {
//        return color
//    }

    fun loadInitialColors() {
        _colors.value = listOf(Color(MAX, MAX, MIN, MIN), Color(MAX, MIN, MAX, MAX))
        //color.postValue(com.paceraudio.wire.Color(com.paceraudio.wire.MAX, com.paceraudio.wire.MAX, com.paceraudio.wire.MIN, com.paceraudio.wire.MIN))
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
                        _colors.value = listOf(Color(MAX, red, green, blue), Color(MAX, MAX - red, MAX - green, MAX - blue))
                        //color.postValue(com.paceraudio.wire.Color(com.paceraudio.wire.MAX, red, green, blue))
                    }
                }
                delay(250L)
            }
        }
    }
}