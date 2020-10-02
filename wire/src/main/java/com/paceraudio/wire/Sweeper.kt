package com.paceraudio.wire

import java.lang.RuntimeException
import java.util.*

private const val MAX = 255
private const val MIN = 0
private const val NUM_COLORS = 6
private const val STEPS_PER_FADE = 256
private const val STEPS = STEPS_PER_FADE * NUM_COLORS
private val R_STEPS = intArrayOf(4 * STEPS_PER_FADE, 5 * STEPS_PER_FADE, 1 * STEPS_PER_FADE, 2 * STEPS_PER_FADE)
private val G_STEPS = intArrayOf(0 * STEPS_PER_FADE, 1 * STEPS_PER_FADE, 3 * STEPS_PER_FADE, 4 * STEPS_PER_FADE)
private val B_STEPS = intArrayOf(2 * STEPS_PER_FADE, 3 * STEPS_PER_FADE, 5 * STEPS_PER_FADE, 0 * STEPS_PER_FADE)
private const val FADE_IN = 0
private const val FULL_START = 1
private const val FADE_OUT = 2
private const val GONE = 3



class Sweeper() {

    fun sweep() {
        println("RED   : " + R_STEPS.contentToString())
        println("GREEN : " + G_STEPS.contentToString())
        println("BLUE  : " + B_STEPS.contentToString())
        for (i in 0 until STEPS) {
            val red = calcValue2(R_STEPS, i)
            val green = calcValue2(G_STEPS, i)
            val blue = calcValue2(B_STEPS, i)
            println("step: $i R: $red, G: $green, B: $blue")
        }
    }

    private fun isInRange(value: Int, begin: Int, end: Int, steps: Int): Boolean {
        return if (end > begin) {
            value in begin until end
        } else {
            (value in begin..steps) || (value in 0 until end)
        }
    }

    private fun calcValue2(transitions: IntArray, step: Int): Int {
        val fadeIn = transitions[FADE_IN]
        val full = transitions[FULL_START]
        val fadeOut = transitions[FADE_OUT]
        val gone = transitions[GONE]
        var value = 0

        value = when {
            isInRange(step, fadeIn, full, STEPS) -> {
                if (step >= fadeIn) {
                    step - fadeIn
                } else {
                    STEPS - fadeIn + step
                }
            }
            isInRange(step, full, fadeOut, STEPS) -> {
                MAX
            }
            isInRange(step, fadeOut, gone, STEPS) -> {
                if (step >= fadeOut) {
                    MAX - (step - fadeOut)
                } else {
                    MAX - (STEPS - fadeOut + step)
                }
            }
            isInRange(step, gone, fadeIn, STEPS) -> {
                MIN
            } else -> {
                throw RuntimeException("value $step not in acceptable range!")
            }
        }

        return value
    }
}

fun main(args: Array<String>) {
    Sweeper().sweep()
}