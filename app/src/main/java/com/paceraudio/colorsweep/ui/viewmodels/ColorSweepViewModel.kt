package com.paceraudio.colorsweep.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paceraudio.colorsweep.BuildConfig
import com.paceraudio.wire.*
import com.paceraudio.wire.models.ColorData
import com.paceraudio.wire.util.ILogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

private const val TAG = COLOR_TAG
const val SWEEP_DELAY_MILLIS = 20L

@HiltViewModel
class ColorSweepViewModel @Inject constructor(
    private val log: ILogger,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _colors: MutableLiveData<List<ColorData>> = MutableLiveData(listOf<ColorData>())
    var colors: LiveData<List<ColorData>> = _colors
    private var colorList: List<ColorData> = mutableListOf()

    private var _color1: MutableLiveData<ColorData> = MutableLiveData()
    var color1: LiveData<ColorData> = _color1
    private var _color2: MutableLiveData<ColorData> = MutableLiveData()
    var color2: LiveData<ColorData> = _color2

//    private var _colorsWrapper = MutableLiveData<ColorsWrapper>()
//    var colorsWrapper: LiveData<ColorsWrapper> = _colorsWrapper

    private val colorManager = ColorManager(2, Sweeper())
    private var step = 0
    private var job: Job? = null
    private var running: Boolean = false

    private val className = this::class.java.simpleName

    fun setUp() {
        colorManager.updateColors(step)
        _colors.value = colorManager.obtainColors()
        _color1.value = _colors.value?.get(0)
        _color2.value = _colors.value?.get(1)
    }

    fun obtainStep(): Int {
        return step
    }

    fun obtainRunning(): Boolean {
        return running
    }

    fun onClick() {
        if (BuildConfig.DEBUG) {
            log.d(COLOR_TAG, "$className onClick() running: $running")
        }
        running = if (running) {
            stopSweep()
            false
        } else {
            startSweep()
            true
        }
    }

    fun startSweep(coroutineStart: CoroutineStart = CoroutineStart.DEFAULT) {
        job = viewModelScope.launch(defaultDispatcher, coroutineStart) {
            while (isActive) {
                updateColorsIncrementStep()
                if (isActive) {
                    updateLiveDataColorValues()
                    if (BuildConfig.DEBUG && (step % 100 == 0)) {
                        log.d(TAG, "$className $colorList")
                    }
                }
                delay(SWEEP_DELAY_MILLIS)
            }
        }
    }

    suspend fun updateColorsIncrementStep() {
        withContext(defaultDispatcher) {
            colorManager.updateColors(step)
            step++
        }
    }

    suspend fun updateLiveDataColorValues() {
        withContext(Dispatchers.Main) {
            _colors.value = colorManager.obtainColors()
            _color1.value = _colors.value?.get(0)
            _color2.value = _colors.value?.get(1)
        }
    }

    fun stopSweep() {
        job?.cancel()
        colorManager.onSweepStopped(step)
    }
}