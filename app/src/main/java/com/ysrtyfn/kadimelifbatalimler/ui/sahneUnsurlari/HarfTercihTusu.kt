package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SpaceBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaTus


@Composable
fun ÇizHarfTercihTuşunu(
    modifier: Modifier = Modifier,
    basiliMi: Boolean = false,
    tusStili : TextStyle = MaterialTheme.typography.kadimElifbaTus,
    text: String,
    tiklaninca: () -> Unit ){

    ElevatedButton(
        modifier = modifier.then(modifier),
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 1.dp
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = tiklaninca)
    {

        if(text != " "){
            Text(text = text,
                style = tusStili,
                color = if (!basiliMi)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                modifier = Modifier.align(Alignment.CenterVertically) )
        }else{
            Icon(
                Icons.Rounded.SpaceBar,
                contentDescription = stringResource(id = R.string.talim_sahnesi_btn_bosluk_harfi_tarifi),
                tint = if (!basiliMi)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            )
        }


    }
}

//TercihHarfiTusu(
//talimSahnesiViewModel = talimSahnesiViewModel,
//id = it,
//modifier = Modifier
//.requiredSize(talimSahnesiViewModel.tercihHarfiTusuEbatiUI)
//.padding(talimSahnesiViewModel.tercihHarfiTusuDolguUI),
//text = talimSuali.karistirilmisKelimeOsmani[it].toString(),
//onClick = {
//    if(!tusBasiliMIHal.value) {
//        tusBasiliMIHal.value = !tusBasiliMIHal.value
//
//        talimSahnesiViewModel.cevapHal.value += talimSuali.karistirilmisKelimeOsmani[it].toString()
//
//        val netice: String
//        val hataMI: Boolean
//        if(talimSahnesiViewModel.cevapHal.value.length == talimSuali.kelimeOsmani.length){
//
//            if(talimSahnesiViewModel.cevapHal.value == talimSuali.kelimeOsmani){
//
//                netice = neticeMuspet + talimSuali.kelimeLatin
//                hataMI = false
//
//                Log.i(LOG_TAG,"Doğru cevapladınız")
//            }else{
//                netice = neticeMenfi
//                hataMI = true
//
//                Log.i(LOG_TAG,"Hatalı cevapladınız")
//            }
//
//            coroutineScope.launch {
//                snackbarHostState.showSnackbar(
//                    SnackbarVisualsWithError(
//                        message = netice,
//                        isError = hataMI
//                    )
//                )
//            }
//        }
//    }
//})