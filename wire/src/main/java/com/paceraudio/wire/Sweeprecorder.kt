package com.paceraudio.wire

import com.paceraudio.wire.models.SweepConfig

interface SweepRecorder {
    fun storeTurn(sweepConfigs: List<SweepConfig>, lastStepCount: Int)
}