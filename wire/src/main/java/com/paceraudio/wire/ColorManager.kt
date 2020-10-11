package com.paceraudio.wire

import java.lang.IllegalArgumentException
import kotlin.random.Random

const val MIN_RATE = 4.0
const val MAX_RATE = 16.0

class ColorManager(private val numColors: Int, private val sweeper: Sweeper) {

    val initialSteps: List<Int> = initSteps(numColors)
    val sweepRates = initSweepRates(numColors)
    val initialColors = initColors(numColors)
    private val currentColors = MutableList(numColors) { index -> transferColors(index) }


    fun obtainColors(): List<Color> {
        return currentColors
    }

    private fun transferColors(index: Int): Color {
        return initialColors[index]
    }

    private fun initSteps(numColors: Int): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        for (i in 0 until numColors) {
            list.add(calcRandomStep())
        }
        return list
    }

    private fun initColors(numColors: Int): List<Color> {
        val list: MutableList<Color> = mutableListOf()
        for (i in 0 until numColors) {
            val step = initialSteps[i]
            val color = Color(
                MAX,
                sweeper.calcValueAtStep(R_STEPS, step),
                sweeper.calcValueAtStep(G_STEPS, step),
                sweeper.calcValueAtStep(B_STEPS, step)
            )
            list.add(color)
        }
        return list
    }

    private fun initSweepRates(numColors: Int): List<Double> {
        val list: MutableList<Double> = mutableListOf()
        for (i in 0 until numColors) {
            list.add(calcRandomSweepRate())
        }
        return list
    }

    private fun calcRandomStep(): Int {
        return Random.nextInt(STEPS + 1)
    }

    private fun calcRandomSweepRate(): Double {
        return Random.nextDouble(MIN_RATE, MAX_RATE)
    }

    fun calcStep(loopStep: Int, rateIndex: Int, totalSteps: Int, rates: List<Double>): Int {
        if (rateIndex in rates.indices) {
            return (loopStep * rates[rateIndex]).toInt() % totalSteps
        } else {
            throw IllegalArgumentException("rateIndex $rateIndex out of sweepRates bounds 0 to ${rates.lastIndex}")
        }
    }

    fun updateColors(loopStep: Int) {
        for (i in sweepRates.indices) {
            currentColors[i] = updateColor(loopStep, i)
        }
    }

    private fun updateColor(loopStep: Int, index: Int): Color {
        val offsetStep = loopStep + initialSteps[index]
        val stepForColor = calcStep(offsetStep, index, STEPS, sweepRates)
        val red = sweeper.calcValueAtStep(R_STEPS, stepForColor)
        val green = sweeper.calcValueAtStep(G_STEPS, stepForColor)
        val blue = sweeper.calcValueAtStep(B_STEPS, stepForColor)
        return Color(MAX, red, green, blue)
    }

    fun generateComplimentary(color: Color): Color {
        return Color(MAX, MAX - color.red, MAX - color.green, MAX - color.blue)
    }
}