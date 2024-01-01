package com.ysrtyfn.kadimelifbatalimler.reklam

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ysrtyfn.kadimelifbatalimler.BuildConfig
import com.ysrtyfn.kadimelifbatalimler.aletler.AD_ID_TAM_EKRAN
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci

class ReklamIdaresi {


//----------------------------------------------------------------
//-------------------------------ADs------------------------------
//----------------------------------------------------------------

    // TODO burası 5 olmalı
    val reklamGostermeSaulAdedi = 5
    private val reklamGosterilsinMI = true


    fun hazirlaReklamlari(context: Activity){
        // Log the Mobile Ads SDK version.
        //Log.d(LOG_TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."

        val requestConfiguration = MobileAds.getRequestConfiguration()
            .toBuilder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
            .setTestDeviceIds(listOf(BuildConfig.TECRUBE_CIHAZI_AD_ID_1))
            .setTestDeviceIds(listOf(BuildConfig.TECRUBE_CIHAZI_AD_ID_2))
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
    }


    fun showInterstitialAd(context: Activity) {
        if(!reklamGosterilsinMI){
            Ikazci.ikazEt(ikaz = "reklamGosterilsinMI: $reklamGosterilsinMI")
            return
        }

        InterstitialAd.load(
            context,
            AD_ID_TAM_EKRAN,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Ikazci.ihbarEt(ihbar = adError.message)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(context)
                }
            }
        )
    }

}