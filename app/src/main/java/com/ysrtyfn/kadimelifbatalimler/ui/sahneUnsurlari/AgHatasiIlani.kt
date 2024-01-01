package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ysrtyfn.kadimelifbatalimler.R


@Composable
fun ÇizAgHatasiIlanini(modifier: Modifier) {
    Column(modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = stringResource(R.string.yafta_ag_hatasi),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )

        ÇizAgHatasiAnimasyonunu(Modifier.fillMaxSize())
    }
}

@Composable
fun ÇizAgHatasiAnimasyonunu(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_ag_hatasi))
    val progress by animateLottieCompositionAsState(composition = composition,
        iterations = LottieConstants.IterateForever)

    LottieAnimation(
        modifier = Modifier.then(modifier),
        composition = composition,
        progress = { progress },
    )
}