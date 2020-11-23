package com.paceraudio.wire

import java.lang.IllegalArgumentException
import kotlin.random.Random

const val MIN_RATE = 4.0
const val MAX_RATE = 16.0

class ColorManager(private val numColors: Int, private val sweeper: Sweeper) {

    /** Stores the starting point of each color, a value on the color wheel */
    val initialSteps: List<Int> = initSteps(numColors)

    /** Stores the rate at which each color will change per loop tick (so colors change at different rates) */
    val sweepRates = initSweepRates(numColors)
    val initialColors = initColors(numColors)

    val sweepConfigs: List<SweepConfig> = initSweepConfigs(numColors)

    /** Stores the current colors for each loop tick */
    private val currentColors = MutableList(numColors) { index -> transferColors(index) }

    private val sweepRecorder: SweepRecorder = MemorySweepRecorder()

    fun setUpTurn(numColors: Int) {

    }

    fun obtainColors(): List<ColorData> {
        return currentColors
    }

    private fun initSweepConfigs(numColors: Int): List<SweepConfig> {
        val sweepingColors = mutableListOf<SweepConfig>()
        for (i in 0 until numColors) {
            sweepingColors.add(SweepConfig(calcRandomStep(), calcRandomSweepRate()))
        }
        return sweepingColors
    }

    private fun transferColors(index: Int): ColorData {
        return initialColors[index]
    }

    private fun initSteps(numColors: Int): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        for (i in 0 until numColors) {
            list.add(calcRandomStep())
        }
        return list
    }

    private fun initColors(numColors: Int): List<ColorData> {
        val list: MutableList<ColorData> = mutableListOf()
        for (i in 0 until numColors) {
            val step = initialSteps[i]
            val color = ColorData(
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

    fun calcStep2(loopStep: Int, sweepConfig: SweepConfig, totalSteps: Int): Int {
        return (loopStep * sweepConfig.rate).toInt() % totalSteps
    }

    fun updateColors(loopStep: Int) {
        for (i in sweepRates.indices) {
            currentColors[i] = updateColor(loopStep, i)
        }
    }

    private fun updateColor(loopStep: Int, index: Int): ColorData {
        val offsetStep = loopStep + sweepConfigs[index].offset
        val stepForColor = calcStep2(offsetStep, sweepConfigs[index], STEPS)
        val red = sweeper.calcValueAtStep(R_STEPS, stepForColor)
        val green = sweeper.calcValueAtStep(G_STEPS, stepForColor)
        val blue = sweeper.calcValueAtStep(B_STEPS, stepForColor)
        return ColorData(MAX, red, green, blue)
    }

    fun generateComplimentary(colorData: ColorData): ColorData {
        return ColorData(MAX, MAX - colorData.red, MAX - colorData.green, MAX - colorData.blue)
    }

    fun onSweepStopped(loopStep: Int) {
        sweepRecorder.storeTurn(sweepConfigs, loopStep)
    }
}