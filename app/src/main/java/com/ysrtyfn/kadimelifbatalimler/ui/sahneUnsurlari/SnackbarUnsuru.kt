package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ÇizSnackbar(snackbarHostState: SnackbarHostState) {

    SnackbarHost(snackbarHostState) { snackbarData ->

        val isError = (snackbarData.visuals as? SnackbarVisualsWithError)?.isError ?: false
        val buttonColor = if (isError) {
            IconButtonDefaults.filledIconButtonColors(
//                containerColor = MaterialTheme.colorScheme.errorContainer,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.error
            )
        } else {
            IconButtonDefaults.filledIconButtonColors(
//                containerColor = MaterialTheme.colorScheme.inverseSurface,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        }

        Snackbar(modifier = Modifier
            .requiredHeight(58.dp)
            .padding(start = 30.dp, end = 30.dp, top = 0.dp, bottom = 10.dp),
            shape = MaterialTheme.shapes.extraSmall,
            containerColor = MaterialTheme.colorScheme.inverseSurface,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface,
            action = {
                IconButton(modifier = Modifier.padding(all = 0.dp),
                    colors = buttonColor,
                    onClick = {
                        if (isError) snackbarData.dismiss() else snackbarData.performAction()
                    })
                {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = snackbarData.visuals.actionLabel,
                        modifier = Modifier.requiredSize(24.dp)
                    )
                }
            }
        ) {
            Text(text = snackbarData.visuals.message,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = MaterialTheme.typography.bodyMedium)
        }
    }
    Spacer(modifier = Modifier.requiredHeight(10.dp))
}
