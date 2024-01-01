package com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TalimSualiEntity::class], version = 1)
abstract class KadimElifbaTalimlerDatabase : RoomDatabase() {
    abstract fun talimSualiEntityDao(): TalimSualiEntityDao
}