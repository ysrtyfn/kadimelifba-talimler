package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun ÇizVektorAnimasyonu(modifier: Modifier, animasyon: LottieCompositionSpec) {
    val composition by rememberLottieComposition(animasyon)
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        modifier = Modifier.then(modifier),
        composition = composition,
        progress = { progress },
    )
}
