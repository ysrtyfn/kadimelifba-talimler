package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AletIpucuPenceresi(modifier: Modifier = Modifier,
                       baslik: String,
                       teferruat: String,
                       ikonTarifi : String,
                       tusMetni: String) {
    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()

    RichTooltipBox(
        modifier = Modifier.then(modifier),

        title = { Text(text = baslik,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold) },

        text = { Text(text = teferruat,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium) },

        action = { TextButton(onClick = { scope.launch { tooltipState.dismiss() } }) {
                        Text(text = tusMetni,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge)
                    }
        },
        tooltipState = tooltipState,
        colors = TooltipDefaults.richTooltipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionContentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        IconButton(
            onClick = {
                scope.launch { tooltipState.show() }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = ikonTarifi,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}