package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.roundToInt


data class CizKesikliCizgi(val step: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepsCount = (size.width / stepPx).roundToInt()
        val actualStep = size.width / stepsCount
        val dotSize = Size(width = actualStep / 2, height = size.height)
        for (i in 0 until stepsCount) {
            addRect(
                Rect(
                    offset = Offset(x = i * actualStep, y = 0f),
                    size = dotSize
                )
            )
        }
        close()
    })
}


//@Composable
//fun DrawDashLine() {
//    val cizgiRengi = MaterialTheme.colorScheme.outline
//
//    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
//    Canvas(
//        Modifier.fillMaxWidth()
//    ) {
//        drawLine(color = cizgiRengi,
//            strokeWidth = 3f,
//            start = Offset(20f, 0f),
//            end = Offset(size.width - 20, 0f),
//            pathEffect = pathEffect
//        )
//    }
//}