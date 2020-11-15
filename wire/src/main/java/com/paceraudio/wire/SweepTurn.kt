package com.paceraudio.wire

/** Class to record a round of one sweep. [sweepConfigs] contain the initial offset and sweep rate.
 *  [lastLoopCount] is the loop count at the time the sweep was stopped. */
data class SweepTurn(val sweepConfigs: List<SweepConfig>, val lastLoopCount: Int)