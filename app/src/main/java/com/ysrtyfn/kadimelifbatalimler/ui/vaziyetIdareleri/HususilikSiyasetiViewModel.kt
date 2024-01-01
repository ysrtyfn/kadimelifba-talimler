package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData


class HususilikSiyasetiViewModel: ViewModel() {
    lateinit var windowSizeClass: WindowSizeClass

    lateinit var ustIdareCubuguData: UstIdareCubuguData

    var hususilikSiyasetiMetniHal: MutableState<Boolean> = mutableStateOf(false)
    var hususilikSiyasetiMetni: String = ""


}