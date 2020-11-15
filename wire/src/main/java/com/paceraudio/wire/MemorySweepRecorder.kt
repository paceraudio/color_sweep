package com.paceraudio.wire

class MemorySweepRecorder : SweepRecorder {

    private val sweepTurnList: MutableList<SweepTurn> = mutableListOf()

    override fun storeTurn(sweepConfigs: List<SweepConfig>, lastStepCount: Int) {
        sweepTurnList.add(SweepTurn(sweepConfigs, lastStepCount))
    }
}