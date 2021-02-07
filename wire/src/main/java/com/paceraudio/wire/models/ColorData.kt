package com.paceraudio.wire.models

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

data class ColorData(val alpha: Int, val red: Int, val green: Int, val blue: Int) {
    val color: Int = calcIntValue(alpha, red, green, blue)
}

data class ColorsWrapper(val colors: List<ColorData>) {
    val trick = colors.size * (if (colors.isNotEmpty()) colors[0].color else 0)
}