package com.paceraudio.colorsweep.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paceraudio.colorsweep.BuildConfig
import com.paceraudio.wire.*
import kotlinx.coroutines.*

private const val TAG = COLOR_TAG
class MainViewModel : ViewModel() {

    private var _colors: MutableLiveData<List<ColorData>> = MutableLiveData(listOf<ColorData>())
    var colors: LiveData<List<ColorData>> = _colors
    var colorList: List<ColorData> = mutableListOf()

    private var _color1: MutableLiveData<ColorData> = MutableLiveData()
    var color1: LiveData<ColorData> = _color1
    private var _color2: MutableLiveData<ColorData> = MutableLiveData()
    var color2: LiveData<ColorData> = _color2

    private var _colorsWrapper = MutableLiveData<ColorsWrapper>()
    var colorsWrapper: LiveData<ColorsWrapper> = _colorsWrapper

    private val colorManager = ColorManager(2, Sweeper())
    private var step = 0
    private var job: Job? = null

    private val className = this::class.java.simpleName

    fun setUp() {
    }

    fun startSweep() {
        job = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                colorManager.updateColors(step)
                step++
                if (isActive) {
                    withContext(Dispatchers.Main) {
                        //_colorsWrapper.value = ColorsWrapper(colorManager.obtainColors())
                       // colorList = colorManager.obtainColors()
                        _colors.value = colorManager.obtainColors()
                        _color1.value = _colors.value?.get(0)
                        _color2.value = _colors.value?.get(1)
                    }
                    if (BuildConfig.DEBUG && (step % 100 == 0)) {
                        Log.d(TAG, "$className $colorList")
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