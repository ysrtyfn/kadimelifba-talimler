package com.ysrtyfn.kadimelifbatalimler.ui.sahneler

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.datalar.TalimSuali
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.CizKesikliCizgi
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.SnackbarVisualsWithError
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.UstIdareCubugu
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.swipeableLeftRight
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.ÇizAgHatasiIlanini
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.ÇizHarfTercihTuşunu
import com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari.ÇizSnackbar
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaTus
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.TalimSahnesiViewModel
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.CevapKismiVaziyeti
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.TasnifSinifVaziyeti
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch


@SuppressLint("SwitchIntDef")
@Composable
fun TalimlerSahnesi(talimSahnesiViewModel: TalimSahnesiViewModel) {
    Ikazci.izahEt(izahat = "-------------- TalimlerSahnesi --------------")

    val rehberPenceresiVaziyeti by talimSahnesiViewModel.rehberPenceresiVaziyeti.collectAsState()
    val cetelePenceresiVaziyeti by talimSahnesiViewModel.cetelePenceresiVaziyeti.collectAsState()
    val tasnifPenceresiVaziyeti by talimSahnesiViewModel.tasnifPenceresiVaziyeti.collectAsState()

    val configuration = LocalConfiguration.current
    val widthInDp = configuration.screenWidthDp.dp
    val heightInDp = configuration.screenHeightDp.dp

    talimSahnesiViewModel.HazirlaUiUnsurlarininHususiyetlerini(configuration.orientation, widthInDp, heightInDp)

    when (configuration.orientation) {

        Configuration.ORIENTATION_PORTRAIT, Configuration.ORIENTATION_UNDEFINED -> {
//            Ikazci.izahEt(izahat = "ORIENTATION_PORTRAIT")
            ÇizmeyeBaşlaAmudi(talimSahnesiViewModel)

        }

        Configuration.ORIENTATION_LANDSCAPE -> {
//            Ikazci.izahEt(izahat = "ORIENTATION_LANDSCAPE")
            ÇizmeyeBaşlaUfki(talimSahnesiViewModel)

        }
    }

    if(rehberPenceresiVaziyeti.faalMi){
        ÇizRehberPenceresini(modifier = Modifier.wrapContentSize(),
            makbulTiklaninca = talimSahnesiViewModel::saklaRehberPenceresini)
    }

    if(cetelePenceresiVaziyeti.faalMi){
        ÇizÇetelePenceresini(modifier = Modifier.wrapContentSize(),
            dogruCevapAdedi = cetelePenceresiVaziyeti.dogruCevapAdedi,
            yanlisCevapAdedi = cetelePenceresiVaziyeti.yanlisCevapAdedi,
            makbulTiklaninca = talimSahnesiViewModel::saklaCetelePenceresini)
    }

    if(tasnifPenceresiVaziyeti.faalMi){
        ÇizTasnifPenceresini(modifier = Modifier.wrapContentSize(),
            sinifVaziyetleri = tasnifPenceresiVaziyeti.sinifVaziyetleri,
            makbulTiklaninca = talimSahnesiViewModel::tasnifleSualleri,
            iptalTiklaninca = talimSahnesiViewModel::saklaTasnifPenceresini)
    }
}


@Composable
private fun ÇizmeyeBaşlaAmudi(talimSahnesiViewModel: TalimSahnesiViewModel){
    Ikazci.izahEt(izahat = "ÇizmeyeBaşlaAmudi()")

    val agIrtibatiVaziyeti by talimSahnesiViewModel.agIrtibatiVaziyeti.collectAsState()
    val talimSuali by talimSahnesiViewModel.talimSuali.collectAsState()
    val cevapVaziyeti by talimSahnesiViewModel.cevapKismiVaziyeti.collectAsState()

    val talimResmiAgdanAliniyorMuHali = remember { mutableStateOf(true) }
    val talimIpucuHali = remember { mutableStateOf(false) }

    val neticeMuspet = stringResource(R.string.talim_sahnesi_sual_cevaplama_neticesi_muspet)
    val neticeMenfi = stringResource(R.string.talim_sahnesi_sual_cevaplama_neticesi_menfi)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { UstIdareCubugu(talimSahnesiViewModel.ustIdareCubuguData) },
        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = {
            if(talimSahnesiViewModel.sonrakiSualTusuGosterilsinMiUI) {
                ÇizSonrakiSualeGeçTuşunuEFAB {
                    coroutineScope.coroutineContext.cancelChildren()
                    talimSahnesiViewModel.gecSonrakiSuale()
                } } },

        content = { innerPadding ->
            if(agIrtibatiVaziyeti) {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = talimSahnesiViewModel.amudiMesafe)
                    .swipeableLeftRight(
                        onLeft = {
                            Ikazci.izahEt(izahat = "swipedLeft")
                            coroutineScope.coroutineContext.cancelChildren()
                            talimSahnesiViewModel.gecSonrakiSuale()
                        },
                        onRight = {
                            Ikazci.izahEt(izahat = "swipedRight")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    content = {

                        if(talimSahnesiViewModel.neticeKismiGosterilsinMiUI)
                            ÇizNeticeKısmını(Modifier.wrapContentSize(Alignment.Center).padding(vertical = 15.dp),
                                dogruCevapAdedi = talimSahnesiViewModel.dogruCevaplarinAdedi,
                                yanlisCevapAdedi = talimSahnesiViewModel.yanlisCevaplarinAdedi)


                        Box(modifier = Modifier
                            .background(Color.Transparent)
                            .weight(1f)
                            .aspectRatio(ratio = 1F, matchHeightConstraintsFirst = false)
                            .padding(all = 20.dp),
                            contentAlignment = Alignment.Center) {

                            ÇizResimKısmını(modifier = Modifier.fillMaxSize()
//                                .shadow(
//                                    elevation = 2.dp,
//                                    shape = MaterialTheme.shapes.large,
//                                    clip = true,
//                                    ambientColor = MaterialTheme.colorScheme.primary,
//                                    spotColor = MaterialTheme.colorScheme.primary )
                                .padding(3.dp)
                                .clip(MaterialTheme.shapes.large)
//                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .background(Color.Transparent)
                                .clickable { talimIpucuHali.value = !talimIpucuHali.value
                                    Ikazci.izahEt(izahat = "Resime tıklandı: " + talimSuali.kelimeLatin) },
                                talimResmiHaliDegisince = { talimResmiAgdanAliniyorMuHali.value = it },
                                talimResmiYolu = talimSuali.faalResimYolu,
                                talimResmiTarifi = talimSuali.kelimeLatin)

                            if(talimResmiAgdanAliniyorMuHali.value) {
                                ÇizTalimResmiYukleniyorAnimasyonunu(modifier = Modifier.height(4.dp).fillMaxWidth().align(Alignment.TopCenter)
                                    .clip(MaterialTheme.shapes.large))
                            }

                            if(talimIpucuHali.value) {
                                Text(text = talimSuali.kelimeLatin,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.headlineLarge,
                                    modifier = Modifier.wrapContentSize(Alignment.Center)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            shape = MaterialTheme.shapes.medium )
                                        .padding(horizontal = 16.dp, vertical = 8.dp), // add inner padding
                                )
                            }

                        }

                        ÇizSualKısmını(Modifier.wrapContentSize(Alignment.Center).padding(horizontal = 10.dp, vertical = 0.dp),
                            talimSahnesiViewModel.sualKismiTextStyleUI)

                        ÇizCevapKısmını(Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 10.dp),
                            cevapKismiVaziyeti = cevapVaziyeti,
                            cevapKismiTextStyle = talimSahnesiViewModel.cevapKismiTextStyleUI,
                            temizleTiklaninca = talimSahnesiViewModel::sifirlaCevabi)

                        ÇizHarfTercihTuşlarıKısmını(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 2.dp)
                                .requiredHeight(talimSahnesiViewModel.harfTercihTusuKismiYuksekligiUI),
                            talimSuali = talimSuali,
                            cevapTemizlemeIbresi = cevapVaziyeti.cevapTemizlemeIbresi,
                            tusStili = talimSahnesiViewModel.harfTercihTusuTextStyleUI,
                            tusEbati = talimSahnesiViewModel.harfTercihTusuEbatiUI,
                            tusDolgusu = talimSahnesiViewModel.harfTercihTusuDolguUI,
                            harfTiklaninca =
                            {
                                talimSahnesiViewModel.ekleCevabaHarfi(harf = it,
                                    neticelenince = {neticeMuspetMi ->
                                        val netice: String

                                        if (neticeMuspetMi) {
                                            netice = neticeMuspet + talimSuali.kelimeLatin

                                            Ikazci.izahEt(izahat = "Doğru cevapladınız")
                                        } else {
                                            netice = neticeMenfi

                                            Ikazci.izahEt(izahat = "Hatalı cevapladınız")
                                        }

                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                SnackbarVisualsWithError(
                                                    message = netice,
                                                    isError = !neticeMuspetMi
                                                )
                                            )
                                        }
                                })
                            } )

                        if(talimSahnesiViewModel.sonrakiSualTusuGosterilsinMiUI)
                            Spacer(modifier = Modifier.requiredHeight(72.dp))
                    }
                )
            } else {
                ÇizAgHatasiIlanini(modifier = Modifier.fillMaxSize())
            }

        }
    )
}


@Composable
private fun ÇizmeyeBaşlaUfki(talimSahnesiViewModel: TalimSahnesiViewModel) {
    Ikazci.izahEt(izahat = "ÇizmeyeBaşlaUfki()")

    val agIrtibatiVaziyeti by talimSahnesiViewModel.agIrtibatiVaziyeti.collectAsState()
    val talimSuali by talimSahnesiViewModel.talimSuali.collectAsState()
    val cevapVaziyeti by talimSahnesiViewModel.cevapKismiVaziyeti.collectAsState()

    val talimResmiAgdanAliniyorMuHali = remember { mutableStateOf(true) }
    val talimIpucuHali = remember { mutableStateOf(false) }

    val neticeMuspet = stringResource(R.string.talim_sahnesi_sual_cevaplama_neticesi_muspet)
    val neticeMenfi = stringResource(R.string.talim_sahnesi_sual_cevaplama_neticesi_menfi)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { UstIdareCubugu(talimSahnesiViewModel.ustIdareCubuguData) },
        snackbarHost = { ÇizSnackbar(snackbarHostState) },
        floatingActionButton = {
            if(talimSahnesiViewModel.sonrakiSualTusuGosterilsinMiUI) {
                ÇizSonrakiSualeGeçTuşunuEFAB {
                    coroutineScope.coroutineContext.cancelChildren()
                    talimSahnesiViewModel.gecSonrakiSuale()
                }
            }},

        content = { innerPadding ->
            if(agIrtibatiVaziyeti) {
                Row(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = talimSahnesiViewModel.ufkiMesafe)
                    .swipeableLeftRight(
                        onLeft = {
                            Ikazci.izahEt(izahat = "swipedLeft")
                            coroutineScope.coroutineContext.cancelChildren()
                            talimSahnesiViewModel.gecSonrakiSuale()
                        },
                        onRight = {
                            Ikazci.izahEt(izahat = "swipedRight")
                        }),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {

                    Box(modifier = Modifier
                        .background(Color.Transparent)
                        .weight(1f)
                        .aspectRatio(ratio = 1F, matchHeightConstraintsFirst = true)
                        .padding(all = 20.dp),
                        contentAlignment = Alignment.Center){

                        ÇizResimKısmını(
                            modifier = Modifier
                                .fillMaxSize()
//                                .shadow(
//                                    elevation = 2.dp,
//                                    shape = MaterialTheme.shapes.large,
//                                    clip = true,
//                                    ambientColor = MaterialTheme.colorScheme.primary,
//                                    spotColor = MaterialTheme.colorScheme.primary )
                                .padding(3.dp)
                                .clip(MaterialTheme.shapes.large)
//                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .background(Color.Transparent)
                                .clickable { talimIpucuHali.value = !talimIpucuHali.value
                                    Ikazci.izahEt(izahat = "Resime tıklandı: " + talimSuali.kelimeLatin) },
                            talimResmiHaliDegisince = { talimResmiAgdanAliniyorMuHali.value = it },
                            talimResmiYolu = talimSuali.faalResimYolu,
                            talimResmiTarifi = talimSuali.kelimeLatin )

                        if(talimResmiAgdanAliniyorMuHali.value) {
                            ÇizTalimResmiYukleniyorAnimasyonunu(
                                modifier = Modifier.height(4.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .clip(MaterialTheme.shapes.large))
                        }

                        if(talimIpucuHali.value) {
                            Text(text = talimSuali.kelimeLatin,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.wrapContentSize()
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = MaterialTheme.shapes.medium )
                                    .padding(horizontal = 16.dp, vertical = 8.dp), // add inner padding
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f).aspectRatio(ratio = 1F, matchHeightConstraintsFirst = true).padding(all = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center)
                    {

                        if(talimSahnesiViewModel.neticeKismiGosterilsinMiUI) {
                            ÇizNeticeKısmını(
                                Modifier.wrapContentSize(Alignment.Center).padding(vertical = 15.dp),
                                dogruCevapAdedi = talimSahnesiViewModel.dogruCevaplarinAdedi,
                                yanlisCevapAdedi = talimSahnesiViewModel.yanlisCevaplarinAdedi
                            )

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        ÇizSualKısmını(Modifier.wrapContentSize(Alignment.Center), talimSahnesiViewModel.sualKismiTextStyleUI)

                        ÇizCevapKısmını(Modifier.fillMaxWidth().padding(bottom = 10.dp),
                            cevapKismiVaziyeti = cevapVaziyeti,
                            cevapKismiTextStyle = talimSahnesiViewModel.cevapKismiTextStyleUI,
                            temizleTiklaninca = talimSahnesiViewModel::sifirlaCevabi)

                        ÇizHarfTercihTuşlarıKısmını(
                            modifier = Modifier.fillMaxWidth().requiredHeight(talimSahnesiViewModel.harfTercihTusuKismiYuksekligiUI),
                            talimSuali = talimSuali,
                            cevapTemizlemeIbresi = cevapVaziyeti.cevapTemizlemeIbresi,
                            tusStili = talimSahnesiViewModel.harfTercihTusuTextStyleUI,
                            tusEbati = talimSahnesiViewModel.harfTercihTusuEbatiUI,
                            tusDolgusu = talimSahnesiViewModel.harfTercihTusuDolguUI,
                            harfTiklaninca =
                            {
                                talimSahnesiViewModel.ekleCevabaHarfi(harf = it,
                                    neticelenince = {neticeMuspetMi ->
                                        val netice: String

                                        if (neticeMuspetMi) {
                                            netice = neticeMuspet + talimSuali.kelimeLatin

                                            Ikazci.izahEt(izahat = "Doğru cevapladınız")
                                        } else {
                                            netice = neticeMenfi

                                            Ikazci.izahEt(izahat = "Hatalı cevapladınız")
                                        }

                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                SnackbarVisualsWithError(
                                                    message = netice,
                                                    isError = !neticeMuspetMi
                                                )
                                            )
                                        }
                                    })
                            } )

                        Spacer(modifier = Modifier.requiredHeight(72.dp).fillMaxWidth())
                    }
                }
            } else {
                ÇizAgHatasiIlanini(modifier = Modifier.fillMaxSize())
            }

        }
    )
}


@Composable
fun ÇizNeticeKısmını(modifier: Modifier,
                     dogruCevapAdedi:Int = 0,
                     yanlisCevapAdedi:Int = 0) {
    
    Row (modifier = Modifier.then(modifier)
        .background(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
        .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        Column (modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center ) {

            Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.talim_sahnesi_netice_yanlis))

            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 3.dp), thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface)

            Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 10.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                text = yanlisCevapAdedi.toString())
        }

        Column (modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center ) {

            Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.talim_sahnesi_netice_dogru))

            Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 3.dp), thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface)

            Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 10.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                text = dogruCevapAdedi.toString())
        }

    }

}


@Composable
private fun ÇizSualKısmını(modifier: Modifier, sualMetniStili: TextStyle) {
    Ikazci.izahEt(izahat = "ÇizSualKısmını()")

    Text(text = stringResource(R.string.talim_sahnesi_yafta_sual),
        textAlign = TextAlign.Center,
        style = sualMetniStili,
        modifier = Modifier.then(modifier))
}


@Composable
private fun ÇizResimKısmını(modifier: Modifier,
                            talimResmiHaliDegisince: (Boolean) -> Unit,
                            talimResmiYolu: String,
                            talimResmiTarifi: String) {
    Ikazci.izahEt(izahat = "ÇizResimKısmını()")

    var imagePainter: Painter =
        if(talimResmiYolu.isNotEmpty()) {
            Ikazci.izahEt(izahat = "ÇizResimKısmını() -> $talimResmiYolu")

            rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current).data(talimResmiYolu)
                                                .size(Size.ORIGINAL).crossfade(true).build())
        }
        else {
            painterResource(id = R.drawable.hilal_yildiz)
        }

    if(imagePainter is AsyncImagePainter){
        when(imagePainter.state) {
            is AsyncImagePainter.State.Empty -> {
                talimResmiHaliDegisince(false)
                imagePainter = painterResource(id = R.drawable.hilal_yildiz)
            }
            is AsyncImagePainter.State.Error -> {
                talimResmiHaliDegisince(false)
                imagePainter = painterResource(id = R.drawable.error)
            }
            is AsyncImagePainter.State.Loading -> {
                talimResmiHaliDegisince(true)
                imagePainter = painterResource(id = R.drawable.hilal_yildiz)
            }
            is AsyncImagePainter.State.Success -> {
                talimResmiHaliDegisince(false)
            }
        }
    }

    Image(
        modifier = Modifier.then(modifier)
            .clip(MaterialTheme.shapes.large)
            .background(Color.Transparent),
//            .background(MaterialTheme.colorScheme.surface)
        painter = imagePainter,
        contentDescription = stringResource(id = R.string.talim_sahnesi_sual_resmi_tarifi, talimResmiTarifi),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun ÇizTalimResmiYukleniyorAnimasyonunu(modifier: Modifier){
    LinearProgressIndicator(
        modifier = Modifier.then(modifier),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}


@Composable
private fun ÇizCevapKısmını(modifier: Modifier, cevapKismiVaziyeti: CevapKismiVaziyeti,
                            cevapKismiTextStyle: TextStyle,
                            temizleTiklaninca: () -> Unit) {
    Ikazci.izahEt(izahat = "ÇizCevapKısmını()")

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Box(modifier = Modifier.padding(0.dp))
        {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
                text = cevapKismiVaziyeti.cevap,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                style = cevapKismiTextStyle )

            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = temizleTiklaninca)
            {
                Icon(Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.talim_sahnesi_btn_temizle),
                    modifier = Modifier.requiredSize(36.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Box(Modifier.height(2.dp).fillMaxWidth().background(MaterialTheme.colorScheme.outline, shape = CizKesikliCizgi(step = 30.dp)))
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ÇizHarfTercihTuşlarıKısmını(
    modifier: Modifier,
    talimSuali: TalimSuali,
    cevapTemizlemeIbresi: Int,
    tusStili :TextStyle = MaterialTheme.typography.kadimElifbaTus,
    tusEbati: Dp = 54.dp,
    tusDolgusu: Dp = 2.dp,
    harfTiklaninca: (String) -> Unit
) {
    Ikazci.izahEt(izahat = "ÇizHarfTercihTuşlarıKısmını()")
//    Ikazci.izahEt(izahat = "ÇizHarfTercihTuşlarıKısmını() -> TalimSuali-ID: " + talimSuali.id)
//    Ikazci.izahEt(izahat = "ÇizHarfTercihTuşlarıKısmını() -> cevapTemizlemeIbresi: $cevapTemizlemeIbresi")

    FlowRow(modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top
    ){

        repeat(talimSuali.karistirilmisKelimeOsmani.length) {
            val halKayitAnahtari = talimSuali.id.toString() + it
            var tusBasiliMIHal by rememberSaveable (talimSuali.id, cevapTemizlemeIbresi,
                                                    key = halKayitAnahtari) { mutableStateOf(false) }
            val harfOsmani = talimSuali.karistirilmisKelimeOsmani[it].toString()

//            Ikazci.izahEt(izahat = "halKayitAnahtari: $halKayitAnahtari")
//            Ikazci.izahEt(izahat = "TusYeri: $it - tusBasiliMI: $tusBasiliMIHal")

            ÇizHarfTercihTuşunu(
                modifier = Modifier.requiredSize(tusEbati).padding(tusDolgusu),
                basiliMi = tusBasiliMIHal,
                tusStili = tusStili,
                text = harfOsmani,
                tiklaninca = {
                    if(!tusBasiliMIHal)
                        harfTiklaninca(harfOsmani)

                    tusBasiliMIHal = true
                }
            )
        }

    }
}


@Composable
private fun ÇizSonrakiSualeGeçTuşunuEFAB(tiklaninca: () -> Unit){
    Ikazci.izahEt(izahat = "ÇizSonrakiSualeGeçTuşunuEFAB()")

    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onClick = {
            tiklaninca()
        }
    ) {
        Text(text = stringResource(R.string.talim_sahnesi_btn_sonraki_sual),
            style = MaterialTheme.typography.labelLarge)
    }
}


@Composable
private fun ÇizRehberPenceresini(
    modifier: Modifier,
    makbulTiklaninca: () -> Unit
){
    AlertDialog(
        modifier = Modifier.then(modifier),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 6.dp,
        onDismissRequest = makbulTiklaninca, // iptalTiklaninca
        title = {
            Column {
                Text(modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.talim_sahnesi_rehber_baslik))

                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            } },
        text = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringResource(id = R.string.talim_sahnesi_rehber_izahat))
               },
        confirmButton = {
            TextButton(
                onClick = makbulTiklaninca) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(id = R.string.talim_sahnesi_rehber_btn_makbul)) } }
//        dismissButton = {
//            TextButton(
//                onClick = iptalTiklaninca) {
//                Text(
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.primary,
//                    text = stringResource(id = R.string.talim_sahnesi_rehber_btn_iptal))
//            } }
    )
}


@Composable
private fun ÇizÇetelePenceresini(
    modifier: Modifier,
    dogruCevapAdedi: Int = 0,
    yanlisCevapAdedi: Int = 0,
    makbulTiklaninca: () -> Unit
){
    AlertDialog(
        modifier = Modifier.then(modifier),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 6.dp,
        onDismissRequest = makbulTiklaninca, // iptalTiklaninca
        title = {
            Column {
                Text(modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.talim_sahnesi_cetele_baslik))
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            } },
        text = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringResource(id = R.string.talim_sahnesi_cetele_izahat, dogruCevapAdedi, yanlisCevapAdedi)
            ) },
        confirmButton = {
            TextButton(
                onClick = makbulTiklaninca) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(id = R.string.talim_sahnesi_cetele_btn_makbul))
            } }
//        dismissButton = {
//            TextButton(
//                onClick = iptalTiklaninca) {
//                Text(
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.primary,
//                    text = stringResource(id = R.string.talim_sahnesi_cetele_btn_iptal))
//            } }
    )
}


@Composable
private fun ÇizTasnifPenceresini(
    modifier: Modifier,
    sinifVaziyetleri: List<TasnifSinifVaziyeti>,
    makbulTiklaninca: (Set<TasnifSinifVaziyeti>) -> Unit,
    iptalTiklaninca: () -> Unit
){
    val seciliSinifVaziyetleri: MutableSet<TasnifSinifVaziyeti> = sinifVaziyetleri.toMutableSet()

    AlertDialog(
        modifier = Modifier.then(modifier),
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 6.dp,
        onDismissRequest = iptalTiklaninca,
        title = {
            Column {
                Text(modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.talim_sahnesi_tasnif_baslik))
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            } },
        text = {
            if(sinifVaziyetleri.isNotEmpty()) {
                val listeSatirYuksekligi = 48
                val listeYukseklikCarpani = if (sinifVaziyetleri.size < 3.5F) sinifVaziyetleri.size.toFloat() else 3.5F

                LazyColumn(modifier = Modifier.requiredHeight((listeYukseklikCarpani * listeSatirYuksekligi).dp)) {
                    items(sinifVaziyetleri) { mevzuBahisSinifVaziyeti ->
                        var isaretliMi by rememberSaveable { mutableStateOf(mevzuBahisSinifVaziyeti.seciliMi) }

                        val sinifSeciliyseHalIzahi = stringResource(id = R.string.talim_sahnesi_tasnif_secilmis_sinif_hali,
                            mevzuBahisSinifVaziyeti.sinif)
                        val sinifSeciliDegilseHalIzahi = stringResource(id = R.string.talim_sahnesi_tasnif_secilmemis_sinif_hali,
                            mevzuBahisSinifVaziyeti.sinif)

                        Row(modifier = Modifier.fillMaxWidth().requiredHeight(listeSatirYuksekligi.dp)
                            .semantics(mergeDescendants = true) {
                                // Set any explicit semantic properties
                                stateDescription = if (isaretliMi) sinifSeciliyseHalIzahi else sinifSeciliDegilseHalIzahi
                            }
                            .toggleable(
                                value = isaretliMi,
                                role = Role.Checkbox,
                                onValueChange = {
                                    isaretliMi = !isaretliMi

                                    seciliSinifVaziyetleri.remove(mevzuBahisSinifVaziyeti)
                                    val yeniSinifVaziyeti = mevzuBahisSinifVaziyeti.copy(seciliMi = isaretliMi)
                                    seciliSinifVaziyetleri.add(yeniSinifVaziyeti)
                                }
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                text = mevzuBahisSinifVaziyeti.sinif)

                            Checkbox(
                                modifier = Modifier.requiredSize(48.dp),
                                checked = isaretliMi,
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary,
                                    checkmarkColor = MaterialTheme.colorScheme.onPrimary ) )
                        }

                    }
                }
            } else {
                Ikazci.izahEt(izahat = "Sınıflar listesi boş!")
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(id = R.string.talim_sahnesi_tasnif_izahat)
                )
            } },
        confirmButton = {
            TextButton(
                onClick = { makbulTiklaninca(seciliSinifVaziyetleri.toSet()) }) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(id = R.string.talim_sahnesi_tasnif_btn_makbul))
            } },
        dismissButton = {
            TextButton(
                onClick = iptalTiklaninca) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(id = R.string.talim_sahnesi_tasnif_btn_iptal))
            } },
    )
}




