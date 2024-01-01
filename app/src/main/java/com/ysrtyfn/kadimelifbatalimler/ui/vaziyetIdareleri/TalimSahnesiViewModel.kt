package com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri

import android.app.Application
import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.KadimElifbaApi
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar.FirebaseAmbarIdaresi
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar.KadimElifbaTalimlerDatabase
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar.TalimSualiEntity
import com.ysrtyfn.kadimelifbatalimler.agIrtibati.ambarlar.TalimSualiEntityDao
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.aletler.PROGRAMCI_AG_SAYFASI
import com.ysrtyfn.kadimelifbatalimler.datalar.ResimAmbari
import com.ysrtyfn.kadimelifbatalimler.datalar.TalimSuali
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaCevap
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaCevapKucuk
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaTus
import com.ysrtyfn.kadimelifbatalimler.ui.theme.kadimElifbaTusKucuk
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.CetelePenceresiVaziyeti
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.CevapKismiVaziyeti
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.RehberPenceresiVaziyeti
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.TasnifPenceresiVaziyeti
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetler.TasnifSinifVaziyeti
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TalimSahnesiViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var gosterReklamiVaktiGeldiyse: () -> Unit

    private val _agIrtibatiVaziyeti = MutableStateFlow(false)
    val agIrtibatiVaziyeti: StateFlow<Boolean> = _agIrtibatiVaziyeti.asStateFlow()

    lateinit var ustIdareCubuguData: UstIdareCubuguData

    private var kadimElifbaTalimlerDatabase: KadimElifbaTalimlerDatabase
    private var talimSualiEntityDao: TalimSualiEntityDao

    private var firebaseAmbarIdaresi: FirebaseAmbarIdaresi

    private val _talimSuali = MutableStateFlow(TalimSuali.alTemsiliTalimSuali())
    val talimSuali: StateFlow<TalimSuali> = _talimSuali.asStateFlow()

    private var _cevapKismiVaziyeti = MutableStateFlow(CevapKismiVaziyeti())
    val cevapKismiVaziyeti: StateFlow<CevapKismiVaziyeti> = _cevapKismiVaziyeti.asStateFlow()

    private var _rehberPenceresiVaziyeti = MutableStateFlow(RehberPenceresiVaziyeti())
    val rehberPenceresiVaziyeti: StateFlow<RehberPenceresiVaziyeti> = _rehberPenceresiVaziyeti.asStateFlow()

    private var _cetelePenceresiVaziyeti = MutableStateFlow(CetelePenceresiVaziyeti())
    val cetelePenceresiVaziyeti: StateFlow<CetelePenceresiVaziyeti> = _cetelePenceresiVaziyeti.asStateFlow()

    private var _tasnifPenceresiVaziyeti = MutableStateFlow(TasnifPenceresiVaziyeti())
    val tasnifPenceresiVaziyeti: StateFlow<TasnifPenceresiVaziyeti> = _tasnifPenceresiVaziyeti.asStateFlow()

    private var talimSualleriHazirMi: Boolean = false
    private var _talimSualleri: List<TalimSuali> = mutableListOf()
    private var _talimSualleriTasniflenmis: List<TalimSuali> = mutableListOf()
    private var _talimSualiIbresi = 0

    val talimSualiIbresi
        get() = _talimSualiIbresi

    private var _tasnifSiniflariVaziyetleri: MutableSet<TasnifSinifVaziyeti> = mutableSetOf()

    var resimAmbari: String = "KadimElifba"
    var resimKalitesi: String = "256"
    var talimSualleriAmbariKademesi: Int = -1

    private var _dogruCevaplarinAdedi: Int = 0
    private var _yanlisCevaplarinAdedi: Int = 0
    val dogruCevaplarinAdedi
        get() = _dogruCevaplarinAdedi
    val yanlisCevaplarinAdedi
        get() = _yanlisCevaplarinAdedi



    private fun sifirlaVaziyeti() {
        _talimSuali.value = TalimSuali.alTemsiliTalimSuali()
        _cevapKismiVaziyeti.value = CevapKismiVaziyeti()
    }

    /*
     * Talim sahnesindeki temizle tuşu ve sonraki sual tuşu ile çağrılır
     */
    fun sifirlaCevabi() {
        Ikazci.izahEt(izahat = "sifirlaCevabi()")

        val cevapKismiVaziyeti = CevapKismiVaziyeti(
            cevap = "",
            cevapTemizlemeIbresi = _cevapKismiVaziyeti.value.cevapTemizlemeIbresi + 1
        )
        _cevapKismiVaziyeti.value = cevapKismiVaziyeti
    }

    fun ekleCevabaHarfi(harf: String, neticelenince: (Boolean) -> Unit) {
        Ikazci.izahEt(izahat = "ekleCevabaHarfi: $harf")

        val cevapKismiVaziyeti = CevapKismiVaziyeti(
            cevap = _cevapKismiVaziyeti.value.cevap + harf,
            cevapTemizlemeIbresi = _cevapKismiVaziyeti.value.cevapTemizlemeIbresi
        )
        _cevapKismiVaziyeti.value = cevapKismiVaziyeti

        if(_cevapKismiVaziyeti.value.cevap.length == _talimSuali.value.kelimeOsmani.length) {
            if(_cevapKismiVaziyeti.value.cevap == _talimSuali.value.kelimeOsmani){
                _dogruCevaplarinAdedi++
                neticelenince(true)
            }else{
                _yanlisCevaplarinAdedi++
                neticelenince(false)
            }
        }
    }

    fun gecSonrakiSuale(){
        Ikazci.izahEt(izahat = "gecSonrakiSuale()")

        sifirlaVaziyeti()
        _talimSuali.value = alSonrakiTalimSualini()

        gosterReklamiVaktiGeldiyse()
    }

    private fun alSonrakiTalimSualini(): TalimSuali {
        Ikazci.izahEt(izahat = "alSonrakiTalimSualini()")

        if(!talimSualleriHazirMi){
            return TalimSuali.alTemsiliTalimSuali()
        }

        if (_talimSualiIbresi < (_talimSualleriTasniflenmis.size - 1)) {
            _talimSualiIbresi++
        } else {
            _talimSualiIbresi = 0
        }

        val talimSuali = _talimSualleriTasniflenmis[_talimSualiIbresi]

        if(resimAmbari == ResimAmbari.KadimElifbaResimAmbari.ambarIsmi){
            talimSuali.faalResimYolu = talimSuali.resimYollari.getOrDefault(ResimAmbari.KadimElifbaResimAmbari.ambarIbresi, "")
        }
        else if(resimAmbari == ResimAmbari.FirebaseResimAmbari.ambarIsmi){
            talimSuali.faalResimYolu = talimSuali.resimYollari.getOrDefault(ResimAmbari.FirebaseResimAmbari.ambarIbresi, "")
            if(talimSuali.faalResimYolu.isEmpty()){
                talimSuali.faalResimYolu = talimSuali.resimYollari.getOrDefault(ResimAmbari.KadimElifbaResimAmbari.ambarIbresi, "")
            }
        }

        return _talimSualleriTasniflenmis[_talimSualiIbresi]
    }

    /*
     * Göster Sakla Rehber Penceresini
     */
    fun gosterRehberPenceresini() {
        Ikazci.izahEt(izahat = "gosterRehberPenceresini()")

        _rehberPenceresiVaziyeti.value = _rehberPenceresiVaziyeti.value.copy(faalMi = true)
    }

    fun saklaRehberPenceresini(){
        Ikazci.izahEt(izahat = "saklaRehberPenceresini()")

        _rehberPenceresiVaziyeti.value = _rehberPenceresiVaziyeti.value.copy(faalMi = false)
    }

    /*
     * Göster Sakla Çetele Penceresini
     */
    fun gosterCetelePenceresini() {
        Ikazci.izahEt(izahat = "gosterCetelePenceresini()")

        _cetelePenceresiVaziyeti.value = _cetelePenceresiVaziyeti.value.copy(faalMi = true,
                                                                            dogruCevapAdedi = dogruCevaplarinAdedi,
                                                                            yanlisCevapAdedi = yanlisCevaplarinAdedi)
    }

    fun saklaCetelePenceresini(){
        Ikazci.izahEt(izahat = "saklaCetelePenceresini()")

        _cetelePenceresiVaziyeti.value = _cetelePenceresiVaziyeti.value.copy(faalMi = false)
    }

    /*
     * Göster Sakla Tasnif Penceresini
     */

    fun gosterTasnifPenceresini() {
        Ikazci.izahEt(izahat = "gosterTasnifPenceresini()")

        _tasnifPenceresiVaziyeti.value = _tasnifPenceresiVaziyeti.value.copy(faalMi = true, sinifVaziyetleri = _tasnifSiniflariVaziyetleri.toList())
    }

    fun saklaTasnifPenceresini() {
        Ikazci.izahEt(izahat = "saklaTasnifPenceresini()")

        _tasnifPenceresiVaziyeti.value = _tasnifPenceresiVaziyeti.value.copy(faalMi = false)
    }

    fun tasnifleSualleri(seciliSinifVaziyetleri: Set<TasnifSinifVaziyeti>) {
        Ikazci.izahEt(izahat = "tasnifleSualleri()")

        val seciliSiniflar: MutableList<String> = mutableListOf()
        for(talimSualiSinifVaziyeti in seciliSinifVaziyetleri){
//            Ikazci.izahEt(izahat = "Seçili Sınıf Vaziyeti: $talimSualiSinifVaziyeti")
            if(talimSualiSinifVaziyeti.seciliMi)
                seciliSiniflar.add(talimSualiSinifVaziyeti.sinif)
        }

        _talimSualleriTasniflenmis = _talimSualleri.filter { it.tasnif in seciliSiniflar}
//        for(talimSuali in _talimSualleriTasniflenmis)
//            Ikazci.izahEt(izahat = "Seçili Tasnife göre Talim Suali: $talimSuali")

        _tasnifSiniflariVaziyetleri = seciliSinifVaziyetleri.toMutableSet()

        _tasnifPenceresiVaziyeti.value = _tasnifPenceresiVaziyeti.value.copy(faalMi = true, sinifVaziyetleri = _tasnifSiniflariVaziyetleri.toList())

        saklaTasnifPenceresini()
        gecSonrakiSuale()
    }

    // ---------------------------------------------------------------------------------
    // --------------------------- Ağ İrtibatı Takibi ----------------------------------
    // ---------------------------------------------------------------------------------

    fun agIrtibatVaziyetiDegisince(agFaalMi: Boolean) {
        _agIrtibatiVaziyeti.value = agFaalMi
    }

    fun baslaTalimSualleriniHazirlamaya(ambarKademesiCihazdaKayitli: Int, ambarKademesiFarkliysa: (yeniAmbarKademesi: Int) -> Unit){
        Ikazci.izahEt(izahat = "baslaTalimSualleriniHazirlamaya()")

        if(talimSualleriHazirMi){
            Ikazci.izahEt(izahat = "baslaTalimSualleriniHazirlamaya(): Talim Sualleri önceden alınmış.")
            return
        } else if(!_agIrtibatiVaziyeti.value) {
            Ikazci.izahEt(izahat = "baslaTalimSualleriniHazirlamaya(): Ağ irtibati yok.")
            return
        } else if(talimSualleriAmbariKademesi == -1){
            Ikazci.izahEt(izahat = "baslaTalimSualleriniHazirlamaya(): ambarKademesi hazır değil.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            Ikazci.izahEt(izahat = "talimSualleri tablosu boş mu: -> " + talimSualiEntityDao.tedkikEtTabloBosMu())

            try {
                val talimSualleriAmbarKademeListesi = KadimElifbaApi.kadimElifbaHizmeti.alTalimSualleriAmbarKademesini()
                Ikazci.izahEt(izahat = "talimSualleriAmbarKademesi: -> $talimSualleriAmbarKademeListesi")

                val kademeListesiDizilmis = talimSualleriAmbarKademeListesi.sortedBy { it.id }
                val ambarKademesiSonuncu = kademeListesiDizilmis.last()

                if(ambarKademesiCihazdaKayitli < ambarKademesiSonuncu.kademe){
                    Ikazci.serhEt(serh = "Cihazda kayıtlı ambar kademesi ağdakinden küçük, talim suallerini yeniliyoruz.")

                    talimSualleriAmbariKademesi = ambarKademesiSonuncu.kademe
                    ambarKademesiFarkliysa(ambarKademesiSonuncu.kademe)
                    alTalimSualleriniAgdan()
                }else{
                    Ikazci.serhEt(serh = "Cihazda kayıtlı ambar kademesi ağdakinden küçük değil, cihazdaki talim suallerini kullanacağız.")

                    alTalimSualleriniCihazdan()
                }
            } catch (e: Exception) {
                Ikazci.ihbarEt(ihbar = "baslaTalimSualleriniHazirlamaya()->Hata: ${e.message}") // TODO talebeye bilgi ver
            }
        }
    }

    private suspend fun alTalimSualleriniAgdan(){
        Ikazci.izahEt(izahat = "alTalimSualleriniAgdan()")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val talimSualleriAgAmbarindan = KadimElifbaApi.kadimElifbaHizmeti.alTalimSuallerini()

                kaydetAgdanAlinanTalimSualleriniCihaza(talimSualleriAgAmbarindan)
                hazirlaTalimSuallerini(talimSualleriAgAmbarindan)
                Ikazci.serhEt(serh = "Ağdan talim sualleri alındı. Adedi: -> " + talimSualleriAgAmbarindan.size)
            } catch (e: Exception) {
                Ikazci.ihbarEt(ihbar = "alTalimSualleriniAgdan()->Hata: ${e.message}") // TODO talebeye bilgi ver
            }
        }

    }

    private suspend fun alTalimSualleriniCihazdan(){
        Ikazci.izahEt(izahat = "alTalimSualleriniCihazdan()")
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                hazirlaTalimSuallerini(talimSualiEntityDao.alTalimSualleriniHepsini().map { TalimSualiEntity.alTalimSualiniEntityden(it) })

                Ikazci.serhEt(serh = "Cihazdan talim sualleri alındı. Adedi: -> " + _talimSualleri.size)
            } catch (e: Exception) {
                Ikazci.ihbarEt(ihbar = "alTalimSualleriniCihazdan()->Hata: ${e.message}") // TODO talebeye bilgi ver
            }
        }
    }

    private suspend fun kaydetAgdanAlinanTalimSualleriniCihaza(talimSualleriAgAmbarindan: List<TalimSuali>) {
        val talimSualiEntityList: List<TalimSualiEntity> = talimSualleriAgAmbarindan.map { TalimSualiEntity.alTalimSualindenEntity(it) }
        talimSualiEntityDao.ekleTalimSuallerini(talimSualiEntityList)
    }

    private fun hazirlaTalimSuallerini(talimSualleriAmbardan: List<TalimSuali>){

        _talimSualleri = talimSualleriAmbardan.shuffled()
        _talimSualleriTasniflenmis = _talimSualleri
        tertipleTalimSuallerininResimYollarini()

        for(talimSuali in _talimSualleri){
            _tasnifSiniflariVaziyetleri.add(TasnifSinifVaziyeti(seciliMi = true, sinif = talimSuali.tasnif))
        }

        talimSualleriHazirMi = true
        gecSonrakiSuale()
    }

    fun tertipleTalimSuallerininResimYollarini(){
        for(talimSuali in _talimSualleriTasniflenmis){
            val resimAdiEbatiyle = talimSuali.resim.replace(".", "-$resimKalitesi.")
            talimSuali.resimYollari[ResimAmbari.KadimElifbaResimAmbari.ambarIbresi] =
                "$PROGRAMCI_AG_SAYFASI/kadimElifba/talimler/resimler/$resimAdiEbatiyle"

            firebaseAmbarIdaresi.alResimYolunu(resimAdiEbatiyle) { resimYoluFirebase ->
                if(resimYoluFirebase != null){
                    talimSuali.resimYollari[ResimAmbari.FirebaseResimAmbari.ambarIbresi] = resimYoluFirebase
                }
//                Ikazci.izahEt(izahat = "FirebaseResimAmbarı: ${talimSuali.kelimeLatin} -> $resimYoluFirebase")
            }
        }

        gecSonrakiSuale()
    }


    init {
        Ikazci.izahEt(izahat = "TalimViewModel created!")
        sifirlaVaziyeti()

        kadimElifbaTalimlerDatabase = Room.databaseBuilder(application, KadimElifbaTalimlerDatabase::class.java,
                                                        "kadimElifbaTalimlerDatabase").build()
        talimSualiEntityDao = kadimElifbaTalimlerDatabase.talimSualiEntityDao()


        firebaseAmbarIdaresi = FirebaseAmbarIdaresi()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()

        Ikazci.izahEt(izahat = "TalimViewModel destroyed!")
    }



    lateinit var windowSizeClass: WindowSizeClass

    lateinit var sualKismiTextStyleUI: TextStyle
    lateinit var cevapKismiTextStyleUI: TextStyle
    lateinit var harfTercihTusuTextStyleUI: TextStyle

    var amudiMesafe: Dp = 0.dp
    var ufkiMesafe: Dp = 0.dp
    var sonrakiSualTusuGosterilsinMiUI: Boolean = true
    var neticeKismiGosterilsinMiUI: Boolean = true

    var harfTercihTusuKismiYuksekligiUI: Dp = 120.dp
    var harfTercihTusuEbatiUI: Dp = 54.dp
    var harfTercihTusuDolguUI: Dp = 3.dp

    @Composable
    fun HazirlaUiUnsurlarininHususiyetlerini(orientation: Int, widthInDp: Dp, heightInDp: Dp){
        amudiMesafe = widthInDp * .025f
        ufkiMesafe = widthInDp * .025f

        sualKismiTextStyleUI = MaterialTheme.typography.titleLarge
        cevapKismiTextStyleUI = MaterialTheme.typography.kadimElifbaCevap
        harfTercihTusuTextStyleUI = MaterialTheme.typography.kadimElifbaTus

        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT, Configuration.ORIENTATION_UNDEFINED -> {


                if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = false

                    if(widthInDp < 330.dp){
                        ustIdareCubuguData.baslikGosterilsinMI = false
                        sonrakiSualTusuGosterilsinMiUI = false
                        sualKismiTextStyleUI = MaterialTheme.typography.bodySmall
                        cevapKismiTextStyleUI = MaterialTheme.typography.kadimElifbaCevapKucuk
                        harfTercihTusuTextStyleUI = MaterialTheme.typography.kadimElifbaTusKucuk
                        harfTercihTusuEbatiUI = 36.dp
                        harfTercihTusuDolguUI = 2.dp
                    }

                    else if(widthInDp < 380.dp){
                        ustIdareCubuguData.baslikMetniKucukMu = true
                        sonrakiSualTusuGosterilsinMiUI = false
                        sualKismiTextStyleUI = MaterialTheme.typography.bodyMedium
                        harfTercihTusuEbatiUI = 48.dp
                    }
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = false
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                    amudiMesafe = widthInDp * .1f
                    harfTercihTusuEbatiUI = 72.dp
                    harfTercihTusuDolguUI = 5.dp
                }
            }

            Configuration.ORIENTATION_LANDSCAPE -> {

                if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact){
                    sonrakiSualTusuGosterilsinMiUI = false
                    neticeKismiGosterilsinMiUI = false

                    if(heightInDp < 320.dp){
                        ustIdareCubuguData.baslikMetniKucukMu = false
                        sualKismiTextStyleUI = MaterialTheme.typography.bodyMedium
                        cevapKismiTextStyleUI = MaterialTheme.typography.kadimElifbaCevapKucuk
                        harfTercihTusuTextStyleUI = MaterialTheme.typography.kadimElifbaTusKucuk
                        harfTercihTusuEbatiUI = 38.dp
                        harfTercihTusuDolguUI = 2.dp
                    }
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact){
                    sonrakiSualTusuGosterilsinMiUI = false
                    neticeKismiGosterilsinMiUI = false

                    if(heightInDp < 380.dp){
                        ustIdareCubuguData.baslikMetniKucukMu = false
                        sualKismiTextStyleUI = MaterialTheme.typography.bodyMedium
                        harfTercihTusuEbatiUI = 48.dp
                    }
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact){
                    sonrakiSualTusuGosterilsinMiUI = false
                    neticeKismiGosterilsinMiUI = false
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                }

                else if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded){
                    sonrakiSualTusuGosterilsinMiUI = true
                    neticeKismiGosterilsinMiUI = true
                    harfTercihTusuEbatiUI = 72.dp
                    harfTercihTusuDolguUI = 5.dp
                }
            }

        }

        ustIdareCubuguData.cetelePenceresiTusuGosterilsinMI = !neticeKismiGosterilsinMiUI
        harfTercihTusuKismiYuksekligiUI = harfTercihTusuEbatiUI * 2 + harfTercihTusuDolguUI * 3
    }


}
