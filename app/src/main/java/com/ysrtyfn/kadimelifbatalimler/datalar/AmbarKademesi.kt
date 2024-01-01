package com.ysrtyfn.kadimelifbatalimler.datalar

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AmbarKademesi(
    var id: Int, var ambar: String, var kademe: Int, var teferruat: String, var tarih: String
)