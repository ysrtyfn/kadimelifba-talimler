package com.ysrtyfn.kadimelifbatalimler.datalar

import com.ysrtyfn.kadimelifbatalimler.R


data class UstIdareCubuguData(
    val ustIdareCubuguBaslikMetniID: Int,

    val merkeziSahneTusuGosterilsinMI: Boolean = false,
    val merkeziSahneTusuTarifMetniID: Int = R.string.ust_idare_btn_ana_ekran,
    val merkeziSahneTusuOnClick: () -> Unit = {},

    val rehberTusuGosterilsinMI: Boolean = false,
    val rehberTusuTarifMetniID: Int = R.string.ust_idare_btn_rehber,
    val rehberTusuOnClick: () -> Unit = {},

    var cetelePenceresiTusuGosterilsinMI: Boolean = false,
    val cetelePenceresiTusuTarifMetniID: Int = R.string.ust_idare_btn_netice_tahtasi,
    val cetelePenceresiTusuOnClick: () -> Unit = {},

    val tasnifTusuGosterilsinMI: Boolean = false,
    val tasnifTusuTarifMetniID: Int = R.string.ust_idare_btn_tasnif,
    val tasnifTusuOnClick: () -> Unit = {},

    val tercihlerTusuGosterilsinMI: Boolean = false,
    val tercihlerTusuTarifMetniID: Int = R.string.ust_idare_btn_tercihler,
    val tercihlerTusuOnClick: () -> Unit = {},

    var baslikGosterilsinMI: Boolean = true,
    var baslikMetniKucukMu: Boolean = false,
)