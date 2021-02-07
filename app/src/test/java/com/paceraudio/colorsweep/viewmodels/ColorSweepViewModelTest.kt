package com.paceraudio.colorsweep.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paceraudio.colorsweep.rules.TestCoroutineRule
import com.paceraudio.colorsweep.ui.viewmodels.ColorSweepViewModel
import com.paceraudio.wire.util.ConsoleLogger
import com.paceraudio.wire.util.ILogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ColorSweepViewModelTest {

    lateinit var sweepViewModel: ColorSweepViewModel
    private val log: ILogger = ConsoleLogger()

    @get:Rule
    var coroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        sweepViewModel = ColorSweepViewModel(log, TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        sweepViewModel.stopSweep()
    }

    @Test
    fun test_first_color_value() {
        runBlocking {
            sweepViewModel.updateColorsIncrementStep()
            sweepViewModel.updateLiveDataColorValues()
        }
        Assert.assertEquals(sweepViewModel.color1.value, sweepViewModel.colors.value?.get(0))
    }

    @Test
    fun test_second_color_value() {
        runBlocking {
            sweepViewModel.updateColorsIncrementStep()
            sweepViewModel.updateLiveDataColorValues()
        }
        Assert.assertEquals(sweepViewModel.color2.value, sweepViewModel.colors.value?.get(1))
    }

    @Test
    fun test_step_value() {
        runBlocking {
            sweepViewModel.updateColorsIncrementStep()
            sweepViewModel.updateLiveDataColorValues()
        }
        Assert.assertEquals(1, sweepViewModel.obtainStep())
    }

    @Test
    fun test_step_value_after_delay() {
        runBlocking {
            for (i in 0 until 2) {
                sweepViewModel.updateColorsIncrementStep()
                sweepViewModel.updateLiveDataColorValues()
            }
        }
        Assert.assertEquals(2, sweepViewModel.obtainStep())
    }

    @Test
    fun test_on_click_view_model_running_true() {
        sweepViewModel.onClick()
        Assert.assertTrue(sweepViewModel.obtainRunning())
    }

    @Test
    fun test_on_click_view_model_running_false() {
        sweepViewModel.onClick()
        sweepViewModel.onClick()
        Assert.assertFalse(sweepViewModel.obtainRunning())
    }
}