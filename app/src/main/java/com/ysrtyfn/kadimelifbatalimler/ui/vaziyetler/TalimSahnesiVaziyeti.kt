package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler

import com.ysrtyfn.kadimelifbatalimler.datalar.TalimSuali


data class TalimSahnesiVaziyeti(
    val talimSuali: TalimSuali = TalimSuali.alTemsiliTalimSuali()
)