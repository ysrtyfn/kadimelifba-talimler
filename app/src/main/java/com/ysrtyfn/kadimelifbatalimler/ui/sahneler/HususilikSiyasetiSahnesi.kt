package com.ysrtyfn.kadimelifbatalimler.ui.sahneler

import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.UstIdareCubugu
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.HususilikSiyasetiViewModel


@Composable
fun HususilikSiyasetiSahnesi(hususilikSiyasetiViewModel: HususilikSiyasetiViewModel) {
    ÇizmeyeBaşla(hususilikSiyasetiViewModel)
}


@Composable
private fun ÇizmeyeBaşla(hususilikSiyasetiViewModel: HususilikSiyasetiViewModel) {
    Scaffold(
        topBar = {
            UstIdareCubugu(hususilikSiyasetiViewModel.ustIdareCubuguData)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    ÇizHususilikSiyasetini(hususilikSiyasetiViewModel)
                }
            )

        }
    )
}


@Composable
fun ÇizHususilikSiyasetini(hususilikSiyasetiViewModel: HususilikSiyasetiViewModel) {
    val hususilikSiyasetiMetniHazirMi by remember { hususilikSiyasetiViewModel.hususilikSiyasetiMetniHal }

    val yaziRengi = MaterialTheme.colorScheme.onBackground

    // parsing html string using the HtmlCompat class
    val hususilikSiyasetiMetni = HtmlCompat.fromHtml(hususilikSiyasetiViewModel.hususilikSiyasetiMetni, 0)

    if(hususilikSiyasetiMetniHazirMi){
        AndroidView(
            modifier = Modifier.padding(10.dp),
            factory = {
                TextView(it).apply {
                    // links
                    autoLinkMask = Linkify.WEB_URLS
                    linksClickable = true
                    // setting the color to use for highlighting the links
//                textSize = 24F
                    setTextColor(yaziRengi.toArgb())
                    setLinkTextColor(yaziRengi.toArgb())
                }
            },
            update = {
//            it.maxLines = currentMaxLines
                it.text = hususilikSiyasetiMetni
            }
        )
    } else {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize().padding(64.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }


}