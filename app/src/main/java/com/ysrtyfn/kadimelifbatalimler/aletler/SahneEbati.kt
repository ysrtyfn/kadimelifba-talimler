package com.ysrtyfn.kadimelifbatalimler.aletler

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun SahneEbati() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val widthInDp = configuration.screenWidthDp.dp
    val heightInDp = configuration.screenHeightDp.dp

    val widthInPx = with(density) { widthInDp.roundToPx() }
    val heightInPx = with(density) { heightInDp.roundToPx() }

    val navigationBarHeightDp = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    val statusBarHeightDp = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    val totalHeightInDp = heightInDp + navigationBarHeightDp + statusBarHeightDp
    val totalHeightInPx = with(density) { totalHeightInDp.roundToPx() }

    Log.i(LOG_TAG, "widthInDp = $widthInDp, heightInDp = $heightInDp")
    Log.i(LOG_TAG, "widthInPx = $widthInPx, heightInPx = $heightInPx")
    Log.i(LOG_TAG, "navigationBarHeightDp = $navigationBarHeightDp, statusBarHeightDp = $statusBarHeightDp")
    Log.i(LOG_TAG, "totalHeightInPx = $totalHeightInPx")

}
