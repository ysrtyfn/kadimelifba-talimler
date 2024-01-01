package com.ysrtyfn.kadimelifbatalimler.datalar

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TalimSuali(
    var id: Int, var mertebe: Int, var tasnif: String, var resim: String,
    var kelimeOsmani: String, var karistirilmisKelimeOsmaniSaved: String = "", var kelimeLatin: String, var tarih: String = ""
) {

    @Json(ignore = true)
    var resimYollari: MutableMap<String, String> = mutableMapOf(ResimAmbari.KadimElifbaResimAmbari.ambarIbresi to "",
                                                    ResimAmbari.FirebaseResimAmbari.ambarIbresi to "")

    @Json(ignore = true)
    var faalResimYolu: String = ""

    @Json(ignore = true)
    private var _karistirilmisKelimeOsmani: String = if (karistirilmisKelimeOsmaniSaved != "") {
        karistirilmisKelimeOsmaniSaved
    } else {
        String(kelimeOsmani.toMutableList().shuffled().toCharArray())
    }

    @Json(ignore = true)
    val karistirilmisKelimeOsmani: String
        get() {
            return _karistirilmisKelimeOsmani
        }

    companion object {
        fun alTemsiliTalimSuali(): TalimSuali {
            return TalimSuali(
                id = 1, mertebe = 1, tasnif = "Lisan", resim = "",
                kelimeOsmani = "الفبا", kelimeLatin = "elifba",
                tarih = "2023-07-22 20:12:28"
            )
        }
    }

    override fun toString(): String {
        return "TalimSuali(id=$id, mertebe='$mertebe', tasnif='$tasnif', resim='$resim', kelimeOsmani='$kelimeOsmani', kelimeLatin='$kelimeLatin', tarih='$tarih')"
    }

}

