package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean = false
) : SnackbarVisuals {
    override val actionLabel: String
        get() = if (isError) "Anladım" else "Anladım"

    override val withDismissAction: Boolean
        get() = false

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Indefinite
}