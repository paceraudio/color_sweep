package com.paceraudio.wire

import com.paceraudio.wire.models.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.math.roundToInt

class TurnEvaluatorTest {

    lateinit var turnEvaluator: TurnEvaluator

    @Before
    fun setUp() {
        turnEvaluator = TurnEvaluator()
    }

    @Test
    fun `test average color with all full red input`() {
        val colors = listOf(fullRed(), fullRed(), fullRed())
        val average = turnEvaluator.calcAverageColor(colors)
        Assert.assertEquals(fullRed(), average.toColorData())
    }

    @Test
    fun `test average color with full red and full green input`() {
        val colors = listOf(fullRed(), fullGreen())
        val average = turnEvaluator.calcAverageColor(colors)
        val expectedR = (MAX.toDouble() / 2).roundToInt()
        val expectedG = (MAX.toDouble() / 2).roundToInt()
        val expected = ColorData(MAX, red = expectedR, green = expectedG, blue = 0)
        Assert.assertEquals(expected, average.toColorData())
    }

    @Test
    fun `test average color with full rgb input`() {
        val colors = listOf(
            ColorData(MAX, red = MAX, green = MAX, blue = MAX),
            ColorData(MAX, red = MAX, green = MAX, blue = MAX),
            ColorData(MAX, red = MAX, green = MAX, blue = MAX),
            ColorData(MAX, red = MAX, green = MAX, blue = MAX)
        )
        val average = turnEvaluator.calcAverageColor(colors)
        val expected = ColorData(MAX, red = MAX, green = MAX, blue = MAX)
        Assert.assertEquals(expected, average.toColorData())
    }

    @Test
    fun `test average color red value with one max one min input`() {
        val r1 = MAX
        val r2 = 0
        val colors = listOf(
            ColorData(MAX, red = r1, green = MAX, blue = MAX),
            ColorData(MAX, red = r2, green = MAX, blue = MAX)
        )
        val rExpected = averageIntValue((r1 + r2).toDouble(), colors.size)
        val average = turnEvaluator.calcAverageColor(colors)
        val expected = ColorData(MAX, red = rExpected, green = MAX, blue = MAX)
        Assert.assertEquals(expected, average.toColorData())
    }

    @Test
    fun `test average color with complimentary input`() {
        val c1 = fullRed()
        val c2 = generateComplimentary(c1)
        val colors = listOf(
            c1,
            c2
        )
        //val rExpected = turnEvaluator.averageValue((MAX + MAX - ).toDouble(), colors.size)
        val average = turnEvaluator.calcAverageColor(colors)
        val expected = ColorData(
            alpha = MAX,
            red = 128,
            green = 128,
            blue = 128)
        Assert.assertEquals(expected, average.toColorData())
    }

    @Test
    fun `test average color with rgb 10 steps diff input`() {
        val c1 = fullRed()
        val c2 = ColorData(
            alpha = MAX,
            red = c1.red - 10,
            green = 0,
            blue = 0
        )
        val c3 = ColorData(
            alpha = MAX,
            red = c2.red - 10,
            green = 0,
            blue = 0
        )
        val colors = listOf(
            c1,
            c2,
            c3
        )
        //val rExpected = turnEvaluator.averageValue((MAX + MAX - ).toDouble(), colors.size)
        val average = turnEvaluator.calcAverageColor(colors)
        Assert.assertEquals(c2, average.toColorData())
    }

    @Test
    fun `test color data average with rgb 11 steps diff input`() {
        val c1 = fullRed()
        val c2 = ColorData(
            alpha = MAX,
            red = c1.red - 11,
            green = 0,
            blue = 0
        )
        val c3 = ColorData(
            alpha = MAX,
            red = c2.red - 10,
            green = 0,
            blue = 0
        )
        val colors = listOf(
            c1,
            c2,
            c3
        )
        //val rExpected = turnEvaluator.averageValue((MAX + MAX - ).toDouble(), colors.size)
        val average = turnEvaluator.calcAverageColor(colors)
        val expected = ColorDataAverage(
            alpha = MAX.toDouble(),
            red = 244.33333333333334,
            green = 0.0,
            blue = 0.0
        )
        Assert.assertEquals(expected, average)
    }

    private fun fullRed(): ColorData {
        return ColorData(alpha = MAX, red = MAX, green = 0, blue = 0)
    }

    private fun fullGreen(): ColorData {
        return ColorData(alpha = MAX, red = 0, green = MAX, blue = 0)
    }

    private fun fullBlue(): ColorData {
        return ColorData(alpha = MAX, red = 0, green = 0, blue = MAX)
    }
}