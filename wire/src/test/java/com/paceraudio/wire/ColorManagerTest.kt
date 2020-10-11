package com.paceraudio.wire

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
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
        val step = colorManager.calcStep(10, 0, 100, listOf(2.0, 8.0))
        assertEquals(20, step)
    }

    @Test
    fun `test rate of two point zero with loop step more that total steps`() {
        val step = colorManager.calcStep(210, 0, 100, listOf(2.0, 8.0))
        assertEquals(20, step)
    }

    @Test
    fun `test rate of eight point zero`() {
        val step = colorManager.calcStep(10, 1, 100, listOf(2.0, 8.0))
        assertEquals(80, step)
    }

    @Test
    fun `test rate of eight point five`() {
        val step = colorManager.calcStep(10, 1, 100, listOf(2.0, 8.5))
        assertEquals(85, step)
    }

    @Test
    fun `test rate of eight point five with loop step more that total steps`() {
        val step = colorManager.calcStep(210, 1, 100, listOf(2.0, 8.5))
        assertEquals(85, step)
    }

    @Test
    fun `test rate of eight point zero with loop step more that total steps`() {
        val step = colorManager.calcStep(210, 1, 100, listOf(2.0, 8.0))
        assertEquals(80, step)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test index out of rates bounds`() {
        colorManager.calcStep(210, 5, 100, listOf(2.0, 8.0))
    }
}