package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri

import android.app.Application
import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData
import kotlin.random.Random


class MerkeziSahneViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var ustIdareCubuguData: UstIdareCubuguData

    lateinit var talimeBaslaOnClick: () -> Unit

    private val animListesi = listOf(R.raw.anim_brothers_reading_quran, R.raw.anim_girl_reading_quran, R.raw.anim_man_reading_quran)
    private val _animRastgeleID = animListesi[Random.nextInt(animListesi.size)]
    var animRastgele: LottieCompositionSpec

    init {
        Ikazci.izahEt(izahat = "MerkeziSahneViewModel created!")

        animRastgele = LottieCompositionSpec.RawRes(_animRastgeleID)
    }

    override fun onCleared() {
        super.onCleared()
//        viewModelScope.cancel()

        Ikazci.izahEt(izahat = "MerkeziSahneViewModel destroyed!")
    }



    lateinit var windowSizeClass: WindowSizeClass
    var merkeziSahneResmininEbatiUI: Dp = 200.dp

    @Composable
    fun HazirlaUIUnsurlarininEbatlarini(orientation: Int, widthInDp: Dp){

//        val genislikKucukMu340DpDen = widthInDp <= 340.dp
//        val genislikKucukMu365DpDen = widthInDp <= 365.dp
//        val genislikKucukMu590DpDen = widthInDp <= 590.dp
//        val genislikKucukMu890DpDen = widthInDp <= 890.dp

        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT, Configuration.ORIENTATION_UNDEFINED -> {
                when(windowSizeClass.widthSizeClass){
                    WindowWidthSizeClass.Compact -> {
                        merkeziSahneResmininEbatiUI = 224.dp
                    }
                    WindowWidthSizeClass.Medium -> {
                        merkeziSahneResmininEbatiUI = 256.dp
                    }
                    WindowWidthSizeClass.Expanded -> {
                        merkeziSahneResmininEbatiUI = 312.dp
                    }
                }
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                when(windowSizeClass.widthSizeClass){
                    WindowWidthSizeClass.Compact -> {
                        merkeziSahneResmininEbatiUI = 224.dp
                    }
                    WindowWidthSizeClass.Medium -> {
                        merkeziSahneResmininEbatiUI = 256.dp
                    }
                    WindowWidthSizeClass.Expanded -> {
                        merkeziSahneResmininEbatiUI = 312.dp
                    }
                }
            }

        }
    }
}