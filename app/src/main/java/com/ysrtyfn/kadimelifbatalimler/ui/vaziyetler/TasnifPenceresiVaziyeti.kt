package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler

data class TasnifPenceresiVaziyeti(
    val faalMi:Boolean = false,
    val sinifVaziyetleri:List<TasnifSinifVaziyeti> = listOf() )