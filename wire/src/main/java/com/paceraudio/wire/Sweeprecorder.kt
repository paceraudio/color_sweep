package com.paceraudio.wire

interface SweepRecorder {
    fun storeTurn(sweepConfigs: List<SweepConfig>, lastStepCount: Int)
}