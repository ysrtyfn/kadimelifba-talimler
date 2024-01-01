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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.AletIpucuPenceresi
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.SegmentedControl
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.UstIdareCubugu
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.ÇizSnackbar
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.TercihlerSahnesiViewModel


@Composable
fun TercihlerSahnesi(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel) {
    ÇizmeyeBaşla(tercihlerSahnesiViewModel)
}


@SuppressLint("SwitchIntDef")
@Composable
private fun ÇizmeyeBaşla(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel) {
    val configuration = LocalConfiguration.current
    val widthInDp = configuration.screenWidthDp.dp
    val kucukMu340DpDen = widthInDp <= 340.dp

    when (configuration.orientation) {

        Configuration.ORIENTATION_PORTRAIT, Configuration.ORIENTATION_UNDEFINED -> {
            Ikazci.izahEt(izahat = "ORIENTATION_PORTRAIT")

            when(tercihlerSahnesiViewModel.windowSizeClass.widthSizeClass){
                WindowWidthSizeClass.Compact -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = if(kucukMu340DpDen) 15.dp else 35.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = if(kucukMu340DpDen) 15.dp else 25.dp
                }
                WindowWidthSizeClass.Medium -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = 35.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = 35.dp
                }
                WindowWidthSizeClass.Expanded -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = 35.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = 35.dp
                }
            }

            ÇizmeyeBaşlaAmudi(tercihlerSahnesiViewModel)
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            Ikazci.izahEt(izahat = "ORIENTATION_LANDSCAPE")

            when(tercihlerSahnesiViewModel.windowSizeClass.widthSizeClass){
                WindowWidthSizeClass.Compact -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = if(kucukMu340DpDen) 5.dp else 15.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = if(kucukMu340DpDen) 5.dp else 25.dp
                }
                WindowWidthSizeClass.Medium -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = if(kucukMu340DpDen) 5.dp else 15.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = 35.dp
                }
                WindowWidthSizeClass.Expanded -> {
                    tercihlerSahnesiViewModel.amudiUstKenaraMesafe = if(kucukMu340DpDen) 5.dp else 15.dp
                    tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi = 5.dp
                    tercihlerSahnesiViewModel.ufkiKenarlaraMesafe = 35.dp
                }
            }

            ÇizmeyeBaşlaUfki(tercihlerSahnesiViewModel)
        }

    }
}


@Composable
private fun ÇizmeyeBaşlaAmudi(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { UstIdareCubugu(tercihlerSahnesiViewModel.ustIdareCubuguData) },
        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = { ÇizProgramcıyaMailİletTuşunuEFAB(tercihlerSahnesiViewModel) },

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = tercihlerSahnesiViewModel.amudiUstKenaraMesafe),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                content = {
                    ÇizResimAmbariniSeçKısmını(tercihlerSahnesiViewModel, Modifier.fillMaxWidth()
                        .padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 0.dp))

                    Divider(modifier = Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 20.dp),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizResimKalitesiniSeçKısmını(tercihlerSahnesiViewModel, Modifier.fillMaxWidth()
                            .padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 0.dp))

                    Divider(modifier = Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 20.dp),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizResimHafızadaTutulsunMuKısmını(tercihlerSahnesiViewModel,
                        Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 0.dp))
                    ÇizResimleriHafizadanSilMuKısmını(tercihlerSahnesiViewModel,
                        Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 5.dp))

                    Divider(modifier = Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 10.dp),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizAcHususilikSiyasetiniTusunu(tercihlerSahnesiViewModel,
                        Modifier.padding(horizontal = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, vertical = 5.dp))

                    Spacer(modifier = Modifier.requiredHeight(72.dp))
                }
            )
        }
    )
}


@Composable
private fun ÇizmeyeBaşlaUfki(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            UstIdareCubugu(tercihlerSahnesiViewModel.ustIdareCubuguData)
        },
        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = { ÇizProgramcıyaMailİletTuşunuEFAB(tercihlerSahnesiViewModel) },
        content = { innerPadding ->
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(top = tercihlerSahnesiViewModel.amudiUstKenaraMesafe, bottom = 0.dp,
                    start = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe, end = tercihlerSahnesiViewModel.ufkiKenarlaraMesafe)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                content = {
                    ÇizResimAmbariniSeçKısmını(tercihlerSahnesiViewModel, Modifier)

                    Divider(modifier = Modifier.padding(vertical = tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizResimKalitesiniSeçKısmını(tercihlerSahnesiViewModel, Modifier)

                    Divider(modifier = Modifier.padding(vertical = tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizResimHafızadaTutulsunMuKısmını(tercihlerSahnesiViewModel, Modifier)
                    ÇizResimleriHafizadanSilMuKısmını(tercihlerSahnesiViewModel, Modifier.padding(vertical = 0.dp))

                    Divider(modifier = Modifier.padding(vertical = tercihlerSahnesiViewModel.amudiBolmeCizgisiKalinligi),
                        thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    ÇizAcHususilikSiyasetiniTusunu(tercihlerSahnesiViewModel, Modifier.padding(vertical = 0.dp))
                    Spacer(modifier = Modifier.requiredHeight(72.dp))
                }
            )
        }
    )
}


@Composable
private fun ÇizProgramcıyaMailİletTuşunuEFAB(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel) {
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onClick = {
            tercihlerSahnesiViewModel.iletProgramciyaEmail()
        }
    ) {
        Icon(
            Icons.Filled.Email,
            contentDescription = stringResource(id = R.string.tercihler_sahnesi_yafta_programciya_mail_ilet),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
//        Text(text = tercihlerSahnesiViewModel.yaftaTalimSonrakiSual,
//            style = MaterialTheme.typography.labelLarge)
    }
}


@Composable
private fun ÇizResimAmbariniSeçKısmını(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel, modifier: Modifier) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = stringResource(id = R.string.tercihler_sahnesi_yafta_resim_ambari),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )

        AletIpucuPenceresi(
            modifier = Modifier.padding(5.dp),
            baslik = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_baslik),
            teferruat = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_resim_ambari),
            ikonTarifi = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_ikon_tarifi) + " for " +  stringResource(id = R.string.tercihler_sahnesi_yafta_resim_ambari),
            tusMetni = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_tus_metni)
        )
        Spacer(modifier = Modifier.weight(1f))

        key(tercihlerSahnesiViewModel.resimAmbariHal.value) {
            SegmentedControl(
                modifier = Modifier,
                useFixedWidth = true,
                itemWidth = 48.dp,
//                overallHeight = 32.dp,
                items = tercihlerSahnesiViewModel.resimAmbarlariUI,
                defaultSelectedItemIndex = tercihlerSahnesiViewModel.resimAmbarlari.indexOf(tercihlerSahnesiViewModel.resimAmbariHal.value)
            ) {
                Ikazci.izahEt(izahat = "Tercih edilen resim ambarı: " + tercihlerSahnesiViewModel.resimAmbarlari[it])
                tercihlerSahnesiViewModel.kaydetTercihEdilenResimAmbarini(tercihlerSahnesiViewModel.resimAmbarlari[it])
            }
        }
    }

}


@Composable
private fun ÇizResimKalitesiniSeçKısmını(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel, modifier: Modifier) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = stringResource(id = R.string.tercihler_sahnesi_yafta_resim_kalitesi),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )

        AletIpucuPenceresi(
            modifier = Modifier.padding(5.dp),
            baslik = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_baslik),
            teferruat = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_resim_kalitesi),
            ikonTarifi = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_ikon_tarifi) + " for " +  stringResource(id = R.string.tercihler_sahnesi_yafta_resim_kalitesi),
            tusMetni = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_tus_metni)
        )
        Spacer(modifier = Modifier.weight(1f))

        key(tercihlerSahnesiViewModel.resimKalitesiHal.value) {
            SegmentedControl(
                modifier = Modifier,
                useFixedWidth = true,
                itemWidth = 48.dp,
//                overallHeight = 32.dp,
                items = tercihlerSahnesiViewModel.resimEbatleriUI,
                defaultSelectedItemIndex = tercihlerSahnesiViewModel.resimEbatleri.indexOf(tercihlerSahnesiViewModel.resimKalitesiHal.value)
            ) {
                Ikazci.izahEt(izahat = "Tercih edilen resim kalitesi: " + tercihlerSahnesiViewModel.resimEbatleri[it])
                tercihlerSahnesiViewModel.kaydetTercihEdilenResimKalitesini(tercihlerSahnesiViewModel.resimEbatleri[it])
            }
        }
    }

}


@Composable
private fun ÇizResimHafızadaTutulsunMuKısmını(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel, modifier: Modifier) {
    var resimHafizadaTutulsunMu by remember { tercihlerSahnesiViewModel.resimlerHafizadaTutulsunMuHal }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.tercihler_sahnesi_yafta_resimler_hafizada_tutulsun_mu),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )

        AletIpucuPenceresi(
            modifier = Modifier.padding(5.dp),
            baslik = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_baslik),
            teferruat = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_resimleri_hafizada_tutma),
            ikonTarifi = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_ikon_tarifi) + " for " +  stringResource(id = R.string.tercihler_sahnesi_yafta_resimler_hafizada_tutulsun_mu),
            tusMetni = stringResource(id = R.string.tercihler_sahnesi_alet_ipucu_tus_metni)
        )

        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = resimHafizadaTutulsunMu,
            onCheckedChange = { value ->
                Ikazci.izahEt(izahat = "resimHafizadaTutulsunMu: $value")
                resimHafizadaTutulsunMu = value

                tercihlerSahnesiViewModel.kaydetResimlerHafizadaTutulsunMuTercihini(value)
            },
            thumbContent = {
                Icon(
                    imageVector = if (resimHafizadaTutulsunMu) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = stringResource(R.string.tercihler_sahnesi_yafta_resimler_hafizada_tutulsun_mu),
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                checkedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                uncheckedIconColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}


@Composable
private fun ÇizResimleriHafizadanSilMuKısmını(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel, modifier: Modifier) {
    TextButton(
        modifier = Modifier.then(modifier),
        onClick = tercihlerSahnesiViewModel.silResimleriHafizadan,
//        colors = ButtonDefaults.textButtonColors(
//            contentColor = MaterialTheme.colorScheme.primary
//        )
    ) {
        Text(text = stringResource(R.string.tercihler_sahnesi_yafta_resimleri_hafizadan_sil),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)
    }
}


@Composable
fun ÇizAcHususilikSiyasetiniTusunu(tercihlerSahnesiViewModel: TercihlerSahnesiViewModel, modifier: Modifier) {
    TextButton(
        modifier = Modifier.then(modifier),
        onClick = {
            tercihlerSahnesiViewModel.hazirlaHususilikSiyasetini()
            tercihlerSahnesiViewModel.acHususilikSiyasetini() },
//        colors = ButtonDefaults.textButtonColors(
//            contentColor = MaterialTheme.colorScheme.primary
//        )
    ) {
        Text(text = stringResource(R.string.tercihler_sahnesi_yafta_ac_hususilik_siyasetini),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)
    }
}
