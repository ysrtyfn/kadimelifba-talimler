package com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseAmbarIdaresi {


    // Create a storage reference from our app
    private val storageRef = Firebase.storage.reference


    // Points to "images"
    private val talimResimleriRef = storageRef.child("talimResimleri")

    // Points to "talimResimleri/kaplan-1024.jpg"
    // Note that you can use variables to create child values
//    private val fileName = "kaplan-1024.jpg"


    fun alResimYolunu(resimAdi:String, resimYoluAlininca: (String?) -> Unit){
        talimResimleriRef.child(resimAdi)
            .downloadUrl.addOnSuccessListener {uri ->
                resimYoluAlininca(uri.toString())

                // Got the download URL for 'users/me/profile.png'
//                Log.d(LOG_TAG, "TalimSuali resminin yolu: " + uri.host)
//                Log.d(LOG_TAG, "TalimSuali resminin yolu: " + uri.path)
//                Log.d(LOG_TAG, "TalimSuali resminin yolu: $uri")
            }.addOnFailureListener {hata ->
                resimYoluAlininca(null)

                // Handle any errors
//                hata.message?.let { hataIci -> Log.e(LOG_TAG, hataIci) }
                hata.printStackTrace()
            }
    }

}