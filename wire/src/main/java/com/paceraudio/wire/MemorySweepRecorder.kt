package com.paceraudio.wire

import com.paceraudio.wire.models.SweepConfig
import com.paceraudio.wire.models.SweepTurn

class MemorySweepRecorder : SweepRecorder {

    private val sweepTurnList: MutableList<SweepTurn> = mutableListOf()

    override fun storeTurn(sweepConfigs: List<SweepConfig>, lastStepCount: Int) {
        sweepTurnList.add(SweepTurn(sweepConfigs, lastStepCount))
    }
}