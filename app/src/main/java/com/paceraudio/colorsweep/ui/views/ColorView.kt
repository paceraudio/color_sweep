package com.paceraudio.colorsweep.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.paceraudio.colorsweep.R
import com.paceraudio.wire.models.ColorData
import com.paceraudio.wire.models.MIN
import kotlin.math.pow
import kotlin.math.sqrt

class ColorView @JvmOverloads constructor(context: Context,
                                          attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var colorData: ColorData = ColorData(MIN, MIN, MIN, MIN)
    private val paint = Paint()
    private val radius = this.width / 2f
    private val squared = radius.pow(2)
    private val strokeWidth = sqrt(squared * squared)

    private val shape = ContextCompat.getDrawable(context, R.drawable.circle_mask_2)


    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        paint.apply {
//            isAntiAlias = true
//            color = 0xff0000
//            style = Paint.Style.STROKE
//            strokeWidth = this@ColorView.strokeWidth
//        }
//        paint.color = color.color
//        canvas?.drawCircle(radius, radius, radius, paint)
    }

//    private fun calcStrokeWidth(): Float {
//        val squared = radius.pow(2)
//        sqrt(squared * squared)
//    }
}