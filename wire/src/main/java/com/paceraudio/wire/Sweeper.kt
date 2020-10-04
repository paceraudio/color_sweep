package com.paceraudio.wire

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

private const val NUM_COLORS = 6
const val STEPS_PER_FADE = 255
const val STEPS = STEPS_PER_FADE * NUM_COLORS

/** Steps for a repeating color sweep.  Each sweep has 6 point where either a primary or secondary
 * color is at it's "pure" point. These Arrays hold the points where a color should start a new
 * phase (fade in from 0 to 255, stay at full 255, fade out from 255 to 0, and stay at 0). Set so
 * that at step 0 (think top of a color wheel) is pure red. */
val R_STEPS = intArrayOf(4 * STEPS_PER_FADE, 5 * STEPS_PER_FADE, 1 * STEPS_PER_FADE, 2 * STEPS_PER_FADE)
val G_STEPS = intArrayOf(0 * STEPS_PER_FADE, 1 * STEPS_PER_FADE, 3 * STEPS_PER_FADE, 4 * STEPS_PER_FADE)
val B_STEPS = intArrayOf(2 * STEPS_PER_FADE, 3 * STEPS_PER_FADE, 5 * STEPS_PER_FADE, 0 * STEPS_PER_FADE)

/** Indices to grab the transition points for the phases in [R_STEPS], [G_STEPS], or [B_STEPS]*/
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
            val red = calcValueAtStep(R_STEPS, i)
            val green = calcValueAtStep(G_STEPS, i)
            val blue = calcValueAtStep(B_STEPS, i)
            println("step: $i R: $red, G: $green, B: $blue")
        }
    }

    private fun isInPositiveRange(value: Int, startInclusive: Int, endExclusive: Int, maxInclusive: Int): Boolean {
        if (startInclusive < 0 ||
            endExclusive < 0 ||
            maxInclusive < 0
        ) {
            throw BadRangeException("All parameters besides value must be greater than 0!")
        }
        if (endExclusive > maxInclusive) {
            throw BadRangeException("Parameter maxInclusive must be greater or equal to endExclusive")
        }
        return if (endExclusive > startInclusive) {
            value in startInclusive until endExclusive
        } else {
            (value in startInclusive..maxInclusive) || (value in 0 until endExclusive)
        }
    }

    fun calcValueAtStep(transitions: IntArray, step: Int): Int {
        val fadeIn = transitions[FADE_IN]
        val full = transitions[FULL_START]
        val fadeOut = transitions[FADE_OUT]
        val gone = transitions[GONE]
        var value = 0

        value = when {
            isInPositiveRange(step, fadeIn, full, STEPS) -> {
                if (step >= fadeIn) {
                    step - fadeIn
                } else {
                    STEPS - fadeIn + step
                }
            }
            isInPositiveRange(step, full, fadeOut, STEPS) -> {
                MAX
            }
            isInPositiveRange(step, fadeOut, gone, STEPS) -> {
                if (step >= fadeOut) {
                    MAX - (step - fadeOut)
                } else {
                    MAX - (STEPS - fadeOut + step)
                }
            }
            isInPositiveRange(step, gone, fadeIn, STEPS) -> {
                MIN
            } else -> {
                throw RuntimeException("value $step not in acceptable range!")
            }
        }

        return value
    }
}

class BadRangeException(message: String) : IllegalArgumentException(message)

fun main(args: Array<String>) {
    Sweeper().sweep()
}