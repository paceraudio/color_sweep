package com.paceraudio.wire

import com.paceraudio.wire.models.ColorData
import com.paceraudio.wire.models.ColorDataAverage
import com.paceraudio.wire.models.averageDoubleValue
import com.paceraudio.wire.models.averageIntValue
import kotlin.math.abs

const val TOTAL_VALUES = 256
private const val TOTAL_SPACES = 3

// TODO calc an average of each color in the list and compare actual colors to the average
class TurnEvaluator {

    fun evaluateTurn(colorsToRate: List<ColorData>) {
        val colorDataAverage = calcAverageColor(colorsToRate)

    }

    internal fun calcAverageColor(colors: List<ColorData>): ColorDataAverage {
        var alphaTotal = 0.0
        var redTotal = 0.0
        var greenTotal = 0.0
        var blueTotal = 0.0
        val size = colors.size

        for (i in colors.indices) {
            val color = colors[i]
            alphaTotal += color.alpha
            redTotal += color.red
            greenTotal += color.green
            blueTotal += color.blue
        }

        return ColorDataAverage(
            alpha = averageDoubleValue(alphaTotal, size),
            red = averageDoubleValue(redTotal, size),
            green = averageDoubleValue(greenTotal, size),
            blue = averageDoubleValue(blueTotal, size)
        )
    }

    fun calcDiffFromAverage(colorDataAverage: ColorDataAverage) {

    }

    private fun checkMatch(val1: Int, val2: Int): Double {
        val diff = abs(val1 - val2)
        val acc = 100.0 - (diff / TOTAL_VALUES * 100.0)
        return acc
    }
}