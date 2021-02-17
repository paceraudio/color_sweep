package com.paceraudio.wire

import com.paceraudio.wire.models.MAX
import com.paceraudio.wire.models.SweepConfig
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method

class ColorManagerTest {

    private lateinit var colorManager: ColorManager
    private lateinit var initColors: Method
    private lateinit var initSweepRates: Method
    private val numColors = 5

    @Before
    fun setUp() {
        colorManager = ColorManager(numColors, Sweeper())
    }

    @Test
    fun `test colors not empty`() {
        assert(colorManager.initialColors.size == numColors)
    }

    @Test
    fun `test red values valid`() {
        colorManager.initialColors.forEach {
            assert(it.red in 0..MAX)
        }
    }

    @Test
    fun `test green values valid`() {
        colorManager.initialColors.forEach {
            assert(it.green in 0..MAX)
        }
    }

    @Test
    fun `test blue values valid`() {
        colorManager.initialColors.forEach {
            assert(it.blue in 0..MAX)
        }
    }

    @Test
    fun `test sweep rates not empty`() {
        assert(colorManager.sweepRates.size == numColors)
    }

    @Test
    fun `test sweep rates values valid`() {
        colorManager.sweepRates.forEach {
            assert(it in MIN_RATE..MAX_RATE)
        }
    }

    @Test
    fun `test rate of two point zero`() {
        val step = colorManager.calcStep2(10, SweepConfig(0, 2.0), 100)
        assertEquals(20, step)
    }

    @Test
    fun `test rate of two point zero with loop step more that total steps`() {
        val step = colorManager.calcStep2(210, SweepConfig(0, 2.0), 100)
        assertEquals(20, step)
    }

    @Test
    fun `test rate of eight point zero`() {
        val step = colorManager.calcStep2(10, SweepConfig(0, 8.0), 100)
        assertEquals(80, step)
    }

    @Test
    fun `test rate of eight point five`() {
        val step = colorManager.calcStep2(10, SweepConfig(0, 8.5), 100)
        assertEquals(85, step)
    }

    @Test
    fun `test rate of eight point five with loop step more that total steps`() {
        val step = colorManager.calcStep2(210, SweepConfig(0, 8.5), 100)
        assertEquals(85, step)
    }

    @Test
    fun `test rate of eight point zero with loop step more that total steps`() {
        val step = colorManager.calcStep2(210, SweepConfig(0, 8.0), 100)
        assertEquals(80, step)
    }
//
//    @Test(expected = IllegalArgumentException::class)
//    fun `test index out of rates bounds`() {
//        colorManager.calcStep2(210, SweepConfig(0, 8.0), 100)
//    }
}