package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

/**
 * items : list of items to be render
 * defaultSelectedItemIndex : to highlight item by default (Optional)
 * useFixedWidth : set true if you want to set fix width to item (Optional)
 * itemWidth : Provide item width if useFixedWidth is set to true (Optional)
 * cornerRadius : To make control as rounded, percent from 0 to 100(Optional)
 * color : Set color to control (Optional)
 * onItemSelection : Get selected item index
 */


@Composable
fun SegmentedControl(
    modifier: Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    overallHeight : Dp = 48.dp,
    cornerRadius : Int = 50, // percent value
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(defaultSelectedItemIndex) }

    Row(modifier = Modifier.then(modifier).requiredHeight(overallHeight),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth) {
                            Modifier.width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.intValue == index) 1f else 0f)
                        } else {
                            Modifier.wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.intValue == index) 1f else 0f)
                        }
                    } else -> {
                        if (useFixedWidth) {
                            Modifier.width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.intValue == index) 1f else 0f)
                        }
                        else {
                            Modifier.wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.intValue == index) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    selectedIndex.intValue = index
                    onItemSelection(selectedIndex.intValue)
                },
                shape = when (index) {
                    /**
                     * left outer button
                     */
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    /**
                     * right outer button
                     */
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    /**
                     * middle button
                     */
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedIndex.intValue == index) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex.intValue == index) {
                    /**
                     * selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else {
                    /**
                     * not selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent
//                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedIndex.intValue == index) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    },
                )
            }
        }
    }
}