package com.paceraudio.colorsweep.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.ui.tooling.preview.Preview
import com.paceraudio.wire.COLOR_TAG
import com.paceraudio.wire.ColorData
import com.paceraudio.wire.MAX
import com.paceraudio.wire.MIN

private const val TAG = COLOR_TAG

@Composable
fun DualColorScreen(
    colorList: List<ColorData>,
    sizeDp: Dp,
    modifier: Modifier,
    onClick: () -> Unit
) {
    modifier.background(color = Color.Black)
    DualColorColumn(
        colorList = colorList,
        sizeDp = sizeDp,
        onClick = onClick
    )
}

@Composable
fun DualColorColumn(colorList: List<ColorData>, sizeDp: Dp, onClick: () -> Unit) {
    if (colorList.size == 2) {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxHeight()
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .clickable(onClick = onClick)
        ) {

            ColorBox(shape = CircleShape, sizeDp = sizeDp, colorData = colorList[0])
            ColorBox(shape = CircleShape, sizeDp = sizeDp, colorData = colorList[1])
        }
    }
}

@Composable
fun ColorBox(shape: Shape, sizeDp: Dp, colorData: ColorData) {
    Box(
        modifier = Modifier
            .padding(Dp(14f))
            .preferredSize(size = sizeDp)
            .clip(shape = shape)
            .background(color = Color(colorData.color))
    ) {
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DualColorScreen(
        colorList = listOf(
            ColorData(MAX, MAX, MIN, MIN),
            ColorData(MAX, MIN, MAX, MAX)

        ),
        sizeDp = Dp(330f),
        modifier = Modifier,
        onClick = {}
    )
}
