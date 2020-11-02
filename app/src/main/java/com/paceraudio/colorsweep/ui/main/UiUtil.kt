package com.paceraudio.colorsweep.ui.main

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.min

class UiUtil @Inject constructor(@ApplicationContext private val context: Context) {

    fun calcCircleSizeInPx(): Float {
        val metrics = context.resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        val limit = min(width, height / 2)
        return dpFromPx(limit * 0.9f)
    }

    private fun dpFromPx(px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    private fun pxFromDp(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }
}