package com.ysrtyfn.kadimelifbatalimler.datalar

import androidx.compose.runtime.saveable.mapSaver


val TalimSualiSaver = run {
//    Log.d(LOG_TAG, "TalimSualiSaver run")

    val idKey = "id"
    val mertebeKey = "mertebe"
    val tasnifKey = "tasnif"
    val resimKey = "resim"
    val kelimeOsmaniKey = "kelimeOsmani"
    val karistirilmisKelimeOsmaniKey = "karistirilmisKelimeOsmani"
    val kelimeLatinKey = "kelimeLatin"
    val tarihKey = "tarih"

    mapSaver(
        save = {
//            Log.d(LOG_TAG, "TalimSualiSaver save")

            mapOf(
                idKey to it.id, mertebeKey to it.mertebe, tasnifKey to it.tasnif,
                resimKey to it.resim, kelimeOsmaniKey to it.kelimeOsmani, karistirilmisKelimeOsmaniKey to it.karistirilmisKelimeOsmani,
                kelimeLatinKey to it.kelimeLatin, tarihKey to it.tarih
            )
        },

        restore = {
//              Log.d(LOG_TAG, "TalimSualiSaver restore")
            TalimSuali(
                it[idKey] as Int, it[mertebeKey] as Int, it[tasnifKey] as String, it[resimKey] as String,
                it[kelimeOsmaniKey] as String, it[karistirilmisKelimeOsmaniKey] as String,
                it[kelimeLatinKey] as String, it[tarihKey] as String
            )
        }
    )

}