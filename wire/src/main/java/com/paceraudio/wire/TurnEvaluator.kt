package com.paceraudio.wire

import com.paceraudio.wire.models.ColorData
import kotlin.math.abs

const val TOTAL_VALUES = 256
private const val TOTAL_SPACES = 3

// TODO calc an average of each color in the list and compare actual colors to the average
class TurnEvaluator {

    fun evaluateTurn(colorsToRate: List<ColorData>) {
        var firstR = 0
        var firstG = 0
        var firstB = 0

        var runningRTotal = 0.0
        var runningGTotal = 0.0
        var runningBTotal = 0.0

        var runningRAcc = 0.0
        var runningGAcc = 0.0
        var runningBAcc = 0.0
        for (totalColors in colorsToRate.indices) {
            val color = colorsToRate[totalColors]
            when {
                totalColors == 0 -> {
                    firstR = color.red
                    firstG = color.green
                    firstB = color.blue
                }
                else -> {
                    val rAcc = checkMatch(firstR, color.red)
                    val gAcc = checkMatch(firstG, color.green)
                    val bAcc = checkMatch(firstB, color.blue)

                    runningRTotal += rAcc
                    runningGTotal += gAcc
                    runningBTotal += bAcc
                }
            }
        }
        val totRAcc = runningRAcc / max(1.0, colorsToRate.size - 1.0)
    }

//    fun evaluateTurn(colorsToRate: List<ColorData>) {
//        var firstR = 0
//        var firstG = 0
//        var firstB = 0
//
//        var runningRAcc = 0.0
//        var runningGAcc = 0.0
//        var runningBAcc = 0.0
//        for (totalColors in colorsToRate.indices) {
//            val color = colorsToRate[totalColors]
//            when {
//                totalColors == 0 -> {
//                    firstR = color.red
//                    firstG = color.green
//                    firstB = color.blue
//                }
//                else -> {
//                    val rAcc = checkMatch(firstR, color.red)
//                    val gAcc = checkMatch(firstG, color.green)
//                    val bAcc = checkMatch(firstB, color.blue)
//                }
//            }
//        }
//    }

    private fun checkMatch(val1: Int, val2: Int): Double {
        val diff = abs(val1 - val2)
        val acc = 100.0 - (diff / TOTAL_VALUES * 100.0)
        return acc
    }
}