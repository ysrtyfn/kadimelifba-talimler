package com.ysrtyfn.kadimelifbatalimler.ui.sahneler

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.UstIdareCubugu
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.ÇizVektorAnimasyonu
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaTakdim
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.MerkeziSahneViewModel


@Composable
fun MerkeziSahne(merkeziSahneViewModel: MerkeziSahneViewModel) {
    Ikazci.izahEt(izahat = "-------------- MerkeziSahne --------------")

    ÇizmeyeBaşla(merkeziSahneViewModel)
}


@SuppressLint("SwitchIntDef")
@Composable
private fun ÇizmeyeBaşla(merkeziSahneViewModel: MerkeziSahneViewModel) {
    val configuration = LocalConfiguration.current
    val widthInDp = configuration.screenWidthDp.dp
    val heightInDp = configuration.screenHeightDp.dp
    Ikazci.izahEt(izahat = "MerkeziSahne - widthInDp: $widthInDp")
    Ikazci.izahEt(izahat = "MerkeziSahne - heightInDp: $heightInDp")

    merkeziSahneViewModel.HazirlaUIUnsurlarininEbatlarini(configuration.orientation, widthInDp)

    when (configuration.orientation) {

        Configuration.ORIENTATION_LANDSCAPE, Configuration.ORIENTATION_UNDEFINED -> {
            Ikazci.izahEt(izahat = "MerkeziSahne - ORIENTATION_LANDSCAPE")
            ÇizmeyeBaşlaUfki(merkeziSahneViewModel)
        }

        Configuration.ORIENTATION_PORTRAIT -> {
            Ikazci.izahEt(izahat = "MerkeziSahne - ORIENTATION_PORTRAIT")
            ÇizmeyeBaşlaAmudi(merkeziSahneViewModel)
        }

    }
}


@Composable
private fun ÇizmeyeBaşlaAmudi(merkeziSahneViewModel: MerkeziSahneViewModel) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { UstIdareCubugu(merkeziSahneViewModel.ustIdareCubuguData) },
//        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = { ÇizTalimeBaşlaTuşunuEFAB(merkeziSahneViewModel.talimeBaslaOnClick) },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                content = {
                    ÇizTakdimKısmını(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center).padding(0.dp))
                    ÇizVektorAnimasyonu(Modifier.requiredSize(merkeziSahneViewModel.merkeziSahneResmininEbatiUI), merkeziSahneViewModel.animRastgele)
                    Spacer(modifier = Modifier.requiredHeight(72.dp))
                }
            )
        }

    )
}


@Composable
private fun ÇizmeyeBaşlaUfki(merkeziSahneViewModel: MerkeziSahneViewModel) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { UstIdareCubugu(merkeziSahneViewModel.ustIdareCubuguData) },
//        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = { ÇizTalimeBaşlaTuşunuEFAB(merkeziSahneViewModel.talimeBaslaOnClick) },

        content = { innerPadding ->
            Row(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(bottom = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {
                    ÇizVektorAnimasyonu(Modifier.requiredSize(merkeziSahneViewModel.merkeziSahneResmininEbatiUI), merkeziSahneViewModel.animRastgele)
                    Column(modifier = Modifier.padding(bottom = 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ÇizTakdimKısmını(modifier = Modifier.wrapContentSize(Alignment.Center).padding(bottom = 30.dp))
                    }
                }
            )
        }

    )
}


@Composable
private fun ÇizTakdimKısmını(modifier: Modifier) {

    Text(modifier = modifier,
        text = "عثمانلى   توركجه سى   الفباسى\n" +
            "Hakiki Türkçe'ye",
//                "Gözlere renkler\n" +
//                "Kulaklara sesler\n" +
//                "Zihinlere kelimeler",
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.kadimElifbaTakdim,
        lineHeight = 50.sp
    )
}


@Composable
private fun ÇizTalimeBaşlaTuşunuEFAB(tiklaninca: () -> Unit){
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onClick = tiklaninca
    ) {
        Text(text = stringResource(id = R.string.merkezi_sahne_btn_talime_basla),
            style = MaterialTheme.typography.labelLarge)
    }
}

