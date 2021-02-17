package com.paceraudio.wire.models

import kotlin.math.roundToInt

const val MAX = 255
const val MIN = 0
private const val GREEN_SHIFT = 8
private const val RED_SHIFT = GREEN_SHIFT * 2
private const val ALPHA_SHIFT = GREEN_SHIFT * 3

private fun calcIntValue(alpha: Int, red: Int, green: Int, blue: Int): Int {
    val a = alpha shl 24
    val r = red shl 16
    val g = green shl 8
    return a or r or g or blue
}

fun generateComplimentary(colorData: ColorData): ColorData {
    return ColorData(MAX, MAX - colorData.red, MAX - colorData.green, MAX - colorData.blue)
}

fun averageIntValue(total: Double, size: Int): Int {
    return averageDoubleValue(total, size).roundToInt()
}

fun averageDoubleValue(total: Double, size: Int): Double {
    return (total / size)
}

data class ColorData(val alpha: Int, val red: Int, val green: Int, val blue: Int) {
    val color: Int = calcIntValue(alpha, red, green, blue)
}

data class ColorDataAverage(val alpha: Double, val red: Double, val green: Double, val blue: Double) {

    fun toColorData(): ColorData {
        return ColorData(
            alpha = alpha.roundToInt(),
            red = red.roundToInt(),
            green = green.roundToInt(),
            blue = blue.roundToInt()
        )
    }
}

data class ColorAccuracy(val alpha: Double, val red: Double, val green: Double, val blue: Double) {

}
data class ColorsWrapper(val colors: List<ColorData>) {
    val trick = colors.size * (if (colors.isNotEmpty()) colors[0].color else 0)
}