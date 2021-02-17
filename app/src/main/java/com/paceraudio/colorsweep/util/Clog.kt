package com.paceraudio.colorsweep.util

import android.util.Log
import com.paceraudio.wire.util.ILogger

class Clog : ILogger {

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}