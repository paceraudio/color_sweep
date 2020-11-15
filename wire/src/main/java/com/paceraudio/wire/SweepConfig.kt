package com.paceraudio.wire
/** Data class that holds the initial offset from 0 (color starting point) and the rate of change
 * for the color (how fast the color sweeps across the color wheel). */
data class SweepConfig(val offset: Int, val rate: Double) {
}