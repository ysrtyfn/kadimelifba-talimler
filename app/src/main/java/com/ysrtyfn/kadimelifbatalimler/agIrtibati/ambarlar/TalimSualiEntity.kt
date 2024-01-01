package com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ysrtyfn.kadimelifbatalimler.datalar.TalimSuali


@Entity(tableName = TalimSualiEntity.AMBAR_TABLO_ISMI)
data class TalimSualiEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "mertebe") val mertebe: Int,
    @ColumnInfo(name = "tasnif") val tasnif: String,
    @ColumnInfo(name = "resim") val resim: String,
    @ColumnInfo(name = "kelimeOsmani") val kelimeOsmani: String,
    @ColumnInfo(name = "kelimeLatin") val kelimeLatin: String) {

    companion object {
        const val AMBAR_TABLO_ISMI: String = "talimSualleri"

        fun alTalimSualindenEntity(talimSuali: TalimSuali): TalimSualiEntity {
            return TalimSualiEntity(
                id = talimSuali.id,
                mertebe = talimSuali.mertebe,
                tasnif = talimSuali.tasnif,
                resim = talimSuali.resim,
                kelimeOsmani = talimSuali.kelimeOsmani,
                kelimeLatin = talimSuali.kelimeLatin )
        }

        fun alTalimSualiniEntityden(talimSualiEntity: TalimSualiEntity): TalimSuali {
            return TalimSuali(
                id = talimSualiEntity.id,
                mertebe = talimSualiEntity.mertebe,
                tasnif = talimSualiEntity.tasnif,
                resim = talimSualiEntity.resim,
                kelimeOsmani = talimSualiEntity.kelimeOsmani,
                kelimeLatin = talimSualiEntity.kelimeLatin )
        }


    }


}
