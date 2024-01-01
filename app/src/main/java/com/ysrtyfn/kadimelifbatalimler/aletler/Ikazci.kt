package com.ysrtyfn.kadimelifbatalimler.aletler

import android.util.Log

// beyanEt var, bunu da ekleyebilirsin
object Ikazci {
    private const val IKAZ_FAAL_MI = false

    fun serhEt(tag: String = LOG_TAG, serh: String) {
        if(IKAZ_FAAL_MI) Log.i(tag, serh)
    }

    fun izahEt(tag: String = LOG_TAG, izahat: String) {
        if(IKAZ_FAAL_MI) Log.d(tag, izahat)
    }

    fun ikazEt(tag: String = LOG_TAG, ikaz: String) {
        if(IKAZ_FAAL_MI) Log.w(tag, ikaz)
    }

    fun ihbarEt(tag: String = LOG_TAG, ihbar: String) {
        if(IKAZ_FAAL_MI) Log.e(tag, ihbar)
    }
}