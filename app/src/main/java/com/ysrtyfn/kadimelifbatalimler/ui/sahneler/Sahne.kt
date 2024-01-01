package com.ysrtyfn.kadimelifbatalimler.ui.sahneler


sealed class Sahne(val hedef: String) {
    object MerkeziSahne : Sahne("merkeziSahne")
    object TercihlerSahnesi : Sahne("tercihlerSahnesi")
    object TalimlerSahnesi : Sahne("talimlerSahnesi")
    object HususilikSiyasetiSahnesi : Sahne("hususiyetSiyasetiSahnesi")
}