package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.ysrtyfn.kadimelifbatalimler.datalar.ResimAmbari
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData


class TercihlerSahnesiViewModel : ViewModel() {

    lateinit var windowSizeClass: WindowSizeClass
    var amudiUstKenaraMesafe: Dp = 0.dp
    var amudiBolmeCizgisiKalinligi: Dp = 0.dp
    var ufkiKenarlaraMesafe: Dp = 0.dp



    lateinit var ustIdareCubuguData: UstIdareCubuguData

    lateinit var iletProgramciyaEmail: () -> Unit
    lateinit var kaydetTercihEdilenResimAmbarini: (String) -> Unit
    lateinit var kaydetTercihEdilenResimKalitesini: (String) -> Unit
    lateinit var kaydetTercihEdilenLisani: (String) -> Unit
    lateinit var kaydetResimlerHafizadaTutulsunMuTercihini: (Boolean) -> Unit
    lateinit var silResimleriHafizadan: () -> Unit
    lateinit var hazirlaHususilikSiyasetini: () -> Unit
    lateinit var acHususilikSiyasetini: () -> Unit
    lateinit var ayarlaPrograminLisanini: (String) -> Unit


    // En düşük kalite 1 - KadimElifba.com, 2 - Firebase Storage
    val resimAmbarlari = listOf(ResimAmbari.KadimElifbaResimAmbari.ambarIsmi, ResimAmbari.FirebaseResimAmbari.ambarIsmi)
    val resimAmbarlariUI = listOf(ResimAmbari.KadimElifbaResimAmbari.ambarIbresi, ResimAmbari.FirebaseResimAmbari.ambarIbresi)
    var resimAmbariHal = mutableStateOf(ResimAmbari.KadimElifbaResimAmbari.ambarIbresi)

    // En düşük kalite 256, sonra 512, sonra da 1024
    val resimEbatleri = listOf("256", "512", "1024")
    val resimEbatleriUI = listOf("1", "2", "3")
    var resimKalitesiHal = mutableStateOf("256")
    var resimlerHafizadaTutulsunMuHal = mutableStateOf(false)


    val lisanlar = listOf("Latin-i Türkçe", "Osman-i Türkçe", "English")
    var tercihEdilenLisanHal = mutableStateOf(lisanlar[0])



}