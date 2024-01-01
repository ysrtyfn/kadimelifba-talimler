package com.ysrtyfn.kadimelifbatalimler.agIrtibati


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ysrtyfn.kadimelifbatalimler.BuildConfig
import com.ysrtyfn.kadimelifbatalimler.datalar.AmbarKademesi
import com.ysrtyfn.kadimelifbatalimler.datalar.TalimSuali
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = BuildConfig.MUTA_MAHZENI;

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//interface KadimElifbaHizmeti {
//    @GET("talimler/andAlTalimSuallerini.php")
//    suspend fun alTalimSuallerini(): List<TalimSuali>
//
//    @GET("talimler/andAlAmbarKademesini.php?ambar=andTalimSualleriAmbarı")
//    suspend fun alTalimSualleriAmbarKademesini(): AmbarKademesi
//}

interface KadimElifbaHizmetiFirebase {
    @GET("talimSualleri.json")
    suspend fun alTalimSuallerini(): List<TalimSuali>

    @GET("ambarKademeleri.json")
    suspend fun alTalimSualleriAmbarKademesini(): List<AmbarKademesi>
}

object KadimElifbaApi {
    val kadimElifbaHizmeti : KadimElifbaHizmetiFirebase by lazy {
        retrofit.create(KadimElifbaHizmetiFirebase::class.java)
    }
}