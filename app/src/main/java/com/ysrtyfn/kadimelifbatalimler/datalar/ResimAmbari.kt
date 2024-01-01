package com.ysrtyfn.kadimelifbatalimler.datalar

sealed class ResimAmbari(val ambarIbresi: String, val ambarIsmi: String) {
    object KadimElifbaResimAmbari: ResimAmbari(ambarIbresi = "1", ambarIsmi = "KadimElifba")
    object FirebaseResimAmbari: ResimAmbari(ambarIbresi = "2", ambarIsmi = "Firebase")
}