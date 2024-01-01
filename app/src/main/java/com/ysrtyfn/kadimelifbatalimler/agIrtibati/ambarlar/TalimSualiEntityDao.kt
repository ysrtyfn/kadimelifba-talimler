package com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar.TalimSualiEntity.Companion.AMBAR_TABLO_ISMI

@Dao
interface TalimSualiEntityDao {

    @Query("SELECT * FROM $AMBAR_TABLO_ISMI")
    suspend fun alTalimSualleriniHepsini(): List<TalimSualiEntity>

    @Query("SELECT * FROM $AMBAR_TABLO_ISMI WHERE id IN (:talimSualleriIDs)")
    suspend fun alTalimSualleriniBakarakIDs(talimSualleriIDs: IntArray): List<TalimSualiEntity>

    @Query("SELECT * FROM $AMBAR_TABLO_ISMI WHERE tasnif IN (:talimSualleriTasnifler)")
    suspend fun alTalimSualleriniBakarakTasnife(talimSualleriTasnifler: List<String>): List<TalimSualiEntity>

//    @Query("SELECT * FROM $AMBAR_TABLO_ISMI WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): TalimSualiEntity

    @Query("SELECT (SELECT COUNT(*) FROM $AMBAR_TABLO_ISMI) == 0")
    suspend fun tedkikEtTabloBosMu(): Boolean

    @Insert
    suspend fun ekleTalimSualini(talimSuali: TalimSualiEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ekleTalimSuallerini(talimSualleri: List<TalimSualiEntity>)

    @Update
    suspend fun degistirTalimSualini(talimSuali: TalimSualiEntity)

    @Update
    suspend fun degistirTalimSuallerini(talimSualleri: List<TalimSualiEntity>)

    @Delete
    suspend fun silTalimSualini(talimSualleri: TalimSualiEntity)

    @Delete
    suspend fun silTalimSuallerini(talimSualleri: List<TalimSualiEntity>)
}