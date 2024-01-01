package com.ysrtyfn.kadimelifbatalimler.faaliyetler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.aletler.Ikazci
import com.ysrtyfn.kadimelifbatalimler.aletler.PROGRAMCI_EMAIL
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.TERCIH_ANHTR_LISAN
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.TERCIH_ANHTR_RESIMLER_HAFIZADA_TUTULSUN_MU
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.TERCIH_ANHTR_RESIM_AMBARI
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.TERCIH_ANHTR_RESIM_KALITESI
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.TERCIH_ANHTR_TALIM_SUALLERI_AMBAR_KADEMESI
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.readBool
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.readInt
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.readString
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.writeBool
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.writeInt
import com.ysrtyfn.kadimelifbatalimler.datalar.tercihler.writeString
import com.ysrtyfn.kadimelifbatalimler.reklam.ReklamIdaresi
import com.ysrtyfn.kadimelifbatalimler.ui.sahneler.HususilikSiyasetiSahnesi
import com.ysrtyfn.kadimelifbatalimler.ui.sahneler.MerkeziSahne
import com.ysrtyfn.kadimelifbatalimler.ui.sahneler.Sahne
import com.ysrtyfn.kadimelifbatalimler.ui.sahneler.TalimlerSahnesi
import com.ysrtyfn.kadimelifbatalimler.ui.sahneler.TercihlerSahnesi
import com.ysrtyfn.kadimelifbatalimler.ui.theme.KadimElifbaTalimlerTheme
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.HususilikSiyasetiViewModel
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.MerkeziSahneViewModel
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.TalimSahnesiViewModel
import com.ysrtyfn.kadimelifbatalimler.ui.vaziyetIdareleri.TercihlerSahnesiViewModel
import com.ysrtyfn.kadimelifbatalimler.vazifeler.VazifeAgVaziyetiTakibi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MerkeziActivity : ComponentActivity() {

    private val reklamIdaresi: ReklamIdaresi = ReklamIdaresi()

    private val merkeziSahneViewModel: MerkeziSahneViewModel by viewModels()
    private val tercihlerSahnesiViewModel: TercihlerSahnesiViewModel by viewModels()
    private val talimSahnesiViewModel: TalimSahnesiViewModel by viewModels()
    private val hususilikSiyasetiViewModel: HususilikSiyasetiViewModel by viewModels()

    // ---------------------------------------------------------------------------------
    // --------------------------- Ağ İrtibatı Takibi ----------------------------------
    // ---------------------------------------------------------------------------------

    private lateinit var vazifeAgVaziyetiTakibi: VazifeAgVaziyetiTakibi
    private val vazifeAgVaziyetiTakipcisi = Observer<Boolean> { isNetworkAvailable ->
        isNetworkAvailable.let { agFaalMi ->
            Ikazci.izahEt(izahat = "Ağ faal mi? : $agFaalMi")

            talimSahnesiViewModel.agIrtibatVaziyetiDegisince(agFaalMi)

            tedkikEtTalimSualleriHazirligini()
        }
    }


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vazifeAgVaziyetiTakibi = VazifeAgVaziyetiTakibi(application)


        tercihlerSahnesiViewModel.iletProgramciyaEmail = iletProgramciyaEmail
        tercihlerSahnesiViewModel.kaydetTercihEdilenResimAmbarini = kaydetTercihEdilenResimAmbarini
        tercihlerSahnesiViewModel.kaydetTercihEdilenResimKalitesini = kaydetTercihEdilenResimKalitesini
        tercihlerSahnesiViewModel.kaydetTercihEdilenLisani = kaydetTercihEdilenLisani
        tercihlerSahnesiViewModel.kaydetResimlerHafizadaTutulsunMuTercihini = kaydetResimlerHafizadaTutulsunMuTercihini
        tercihlerSahnesiViewModel.silResimleriHafizadan = silResimleriHafizadan
        tercihlerSahnesiViewModel.ayarlaPrograminLisanini = ayarlaPrograminLisanini

        tercihlerSahnesiViewModel.hazirlaHususilikSiyasetini = hazirlaHususilikSiyasetiMetnini

        talimSahnesiViewModel.gosterReklamiVaktiGeldiyse = ::gosterReklamiVaktiGeldiyse


        setContent {
            KadimElifbaTalimlerTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                Ikazci.izahEt(izahat = windowSizeClass.widthSizeClass.toString())
                Ikazci.izahEt(izahat = windowSizeClass.heightSizeClass.toString())

//                SahneEbati()

                merkeziSahneViewModel.windowSizeClass = windowSizeClass
                tercihlerSahnesiViewModel.windowSizeClass = windowSizeClass
                talimSahnesiViewModel.windowSizeClass = windowSizeClass
                hususilikSiyasetiViewModel.windowSizeClass = windowSizeClass


                val navController = rememberNavController()
                NavigationView(
                    navController = navController,
                    ustIdareCubuguDataBuilder = hazirlaUstIdareCubuguData,
                    merkeziSahneViewModel = merkeziSahneViewModel,
                    tercihlerSahnesiViewModel = tercihlerSahnesiViewModel,
                    talimSahnesiViewModel = talimSahnesiViewModel,
                    hususilikSiyasetiViewModel = hususilikSiyasetiViewModel
                )

            }
        }

        // Enable support for Splash Screen API for proper Android 12+ support
        installSplashScreen()

        // ---------------------------------------------------------------------------------
        // --------------------------- Ambar Kayıtları -------------------------------------
        // ---------------------------------------------------------------------------------

        val talimSualleriAmbarKademesi = readInt(TERCIH_ANHTR_TALIM_SUALLERI_AMBAR_KADEMESI).asLiveData()
        talimSualleriAmbarKademesi.observe(this) {
            if(talimSahnesiViewModel.talimSualleriAmbariKademesi != it){
                talimSahnesiViewModel.talimSualleriAmbariKademesi = it
                tedkikEtTalimSualleriHazirligini()
            }

            Ikazci.izahEt(izahat = "Tercihler-talimSualleriAmbarKademesi: $it")
        }

        val resimAmbariKaydi = readString(TERCIH_ANHTR_RESIM_AMBARI).asLiveData()
        resimAmbariKaydi.observe(this) {
            if (it.isNotEmpty() && it in tercihlerSahnesiViewModel.resimAmbarlari) {
                tercihlerSahnesiViewModel.resimAmbariHal.value = it
            } else {
                tercihlerSahnesiViewModel.resimAmbariHal.value = tercihlerSahnesiViewModel.resimAmbarlari[0]
            }

            talimSahnesiViewModel.resimAmbari = tercihlerSahnesiViewModel.resimAmbariHal.value
            Ikazci.izahEt(izahat = "Tercihler-resimAmbari: " + tercihlerSahnesiViewModel.resimAmbariHal.value)
        }

        val resimKalitesiKaydi = readString(TERCIH_ANHTR_RESIM_KALITESI).asLiveData()
        resimKalitesiKaydi.observe(this) {
            if (it.isNotEmpty() && it in tercihlerSahnesiViewModel.resimEbatleri) {
                tercihlerSahnesiViewModel.resimKalitesiHal.value = it
            } else {
                tercihlerSahnesiViewModel.resimKalitesiHal.value = tercihlerSahnesiViewModel.resimEbatleri[0]
            }

            talimSahnesiViewModel.resimKalitesi = tercihlerSahnesiViewModel.resimKalitesiHal.value
            talimSahnesiViewModel.tertipleTalimSuallerininResimYollarini()
            Ikazci.izahEt(izahat = "Tercihler-resimKalitesi: " + tercihlerSahnesiViewModel.resimKalitesiHal.value)
        }

        val lisanKaydi = readString(TERCIH_ANHTR_LISAN).asLiveData()
        lisanKaydi.observe(this) {
            if (it.isNotEmpty() && it in tercihlerSahnesiViewModel.lisanlar) {
                tercihlerSahnesiViewModel.tercihEdilenLisanHal.value = it
            } else {
                tercihlerSahnesiViewModel.tercihEdilenLisanHal.value = tercihlerSahnesiViewModel.lisanlar[0]
            }

            Ikazci.izahEt(izahat = "Tercihler-lisan: " + tercihlerSahnesiViewModel.tercihEdilenLisanHal.value)
        }

        val resimlerHafizadaTutulsunMuKaydi = readBool(TERCIH_ANHTR_RESIMLER_HAFIZADA_TUTULSUN_MU).asLiveData()
        resimlerHafizadaTutulsunMuKaydi.observe(this) {
            Ikazci.izahEt(izahat = "Tercihler-resimlerHafizadaTutulsunMu: $it")

            tercihlerSahnesiViewModel.resimlerHafizadaTutulsunMuHal.value = it

            ayarlaAgdanResimYukleyiciyi(it)
        }


        // ---------------------------------------------------------------------------------
        // --------------------------------- Reklamlar -------------------------------------
        // ---------------------------------------------------------------------------------

        reklamIdaresi.hazirlaReklamlari(this)
    }

    override fun onResume() {
        super.onResume()

        vazifeAgVaziyetiTakibi.observeForever(vazifeAgVaziyetiTakipcisi)
    }

    override fun onPause() {
        super.onPause()

        vazifeAgVaziyetiTakibi.removeObserver(vazifeAgVaziyetiTakipcisi)
    }


    private val kaydetTercihEdilenResimAmbarini: (String) -> Unit = {
        lifecycleScope.launch {
            writeString(TERCIH_ANHTR_RESIM_AMBARI, it)
        }
    }
    private val kaydetTercihEdilenResimKalitesini: (String) -> Unit = {
        lifecycleScope.launch {
            writeString(TERCIH_ANHTR_RESIM_KALITESI, it)
        }
    }
    private val kaydetTercihEdilenLisani: (String) -> Unit = {
        lifecycleScope.launch {
            writeString(TERCIH_ANHTR_LISAN, it)
        }
    }
    private val kaydetResimlerHafizadaTutulsunMuTercihini: (Boolean) -> Unit = {
        lifecycleScope.launch {
            writeBool(TERCIH_ANHTR_RESIMLER_HAFIZADA_TUTULSUN_MU, it)
        }
    }
    private val kaydetTalimSualleriAmbarKademesiniTercihini: (Int) -> Unit = {
        lifecycleScope.launch {
            writeInt(TERCIH_ANHTR_TALIM_SUALLERI_AMBAR_KADEMESI, it)
        }
    }

    private fun tedkikEtTalimSualleriHazirligini(){
        talimSahnesiViewModel.baslaTalimSualleriniHazirlamaya(talimSahnesiViewModel.talimSualleriAmbariKademesi) { yeniAmbarKademesi ->
            kaydetTalimSualleriAmbarKademesiniTercihini(yeniAmbarKademesi)
        }
    }

    // if you have many image request builders, it's recommended to set the cache globally so you wouldn't need to set it anywhere again.
    private fun ayarlaAgdanResimYukleyiciyi(resimlerHafizadaTutulsunMu: Boolean) {
        Ikazci.izahEt(izahat = "Ağdan alınan resimler hafızada tutulacak mı?: $resimlerHafizadaTutulsunMu")

        val imageLoaderBuilder = ImageLoader.Builder(this)
            .respectCacheHeaders(false)

        if(resimlerHafizadaTutulsunMu){
            imageLoaderBuilder.diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .memoryCache {
                    MemoryCache.Builder(this)
                        .maxSizePercent(0.1)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(cacheDir.resolve("talimResimleriAmbar"))
                        .maxSizePercent(0.02)
                        .build()
                }
        }else{
            imageLoaderBuilder.diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
        }

        val imageLoader = imageLoaderBuilder.build()
        Coil.setImageLoader(imageLoader)
    }


    private val hazirlaUstIdareCubuguData: (navController: NavHostController, sahne: Sahne) -> UstIdareCubuguData = {
            navController: NavHostController, sahne: Sahne ->

            when (sahne) {
                Sahne.MerkeziSahne -> UstIdareCubuguData(
                    ustIdareCubuguBaslikMetniID = R.string.merkezi_sahne_ismi,

                    tercihlerTusuGosterilsinMI = true,
                    tercihlerTusuOnClick = {
                        navController.navigateOnce(Sahne.TercihlerSahnesi.hedef)
                    }
                )


                Sahne.TercihlerSahnesi -> UstIdareCubuguData(
                    ustIdareCubuguBaslikMetniID = R.string.tercihler_sahnesi_ismi,

                    merkeziSahneTusuGosterilsinMI = true,
                    merkeziSahneTusuOnClick = {
                        navController.navigateOnce(Sahne.MerkeziSahne.hedef) { popUpTo(Sahne.MerkeziSahne.hedef) { inclusive = true } }
                    }
                )


                Sahne.TalimlerSahnesi -> UstIdareCubuguData(
                    ustIdareCubuguBaslikMetniID = R.string.talim_sahnesi_ismi,

                    merkeziSahneTusuGosterilsinMI = true,
                    merkeziSahneTusuOnClick = { navController.navigateOnce(Sahne.MerkeziSahne.hedef)
                                                    { popUpTo(Sahne.MerkeziSahne.hedef) { inclusive = true } } },

                    rehberTusuGosterilsinMI = true,
                    rehberTusuOnClick = talimSahnesiViewModel::gosterRehberPenceresini,

                    cetelePenceresiTusuGosterilsinMI = true,
                    cetelePenceresiTusuOnClick = talimSahnesiViewModel::gosterCetelePenceresini,

                    tasnifTusuGosterilsinMI = true,
                    tasnifTusuOnClick = talimSahnesiViewModel::gosterTasnifPenceresini,

                    tercihlerTusuGosterilsinMI = true,
                    tercihlerTusuOnClick = {
                        navController.navigateOnce(Sahne.TercihlerSahnesi.hedef)
                    } )


                Sahne.HususilikSiyasetiSahnesi -> UstIdareCubuguData(
                    ustIdareCubuguBaslikMetniID = R.string.hususilik_siyaseti_sahnesi_ismi,

                    merkeziSahneTusuGosterilsinMI = true,
                    merkeziSahneTusuOnClick = {
                        navController.navigateOnce(Sahne.MerkeziSahne.hedef) { popUpTo(Sahne.MerkeziSahne.hedef) { inclusive = true } }
                    }
                )

            }

        }

    // TODO bu kod yavaş çalışıyor, Skipped Frames hatasına sebep oluyor
    private val hazirlaHususilikSiyasetiMetnini: () -> Unit = {
        lifecycleScope.launch {
            hususilikSiyasetiViewModel.hususilikSiyasetiMetni = okuHususilikSiyasetiMetniniArkaplanda()
            hususilikSiyasetiViewModel.hususilikSiyasetiMetniHal.value = true
        }
    }

    private suspend fun okuHususilikSiyasetiMetniniArkaplanda() : String {
        var string: String? = ""
        val stringBuilder = StringBuilder()

        withContext(Dispatchers.IO){
            val inputStream: InputStream = resources.openRawResource(R.raw.privacy_policy)
            val reader = BufferedReader(InputStreamReader(inputStream))
            while (true) {
                try {
                    if (reader.readLine().also { string = it } == null) break
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                stringBuilder.append(string).append("\n")
            }
            inputStream.close()
        }

        // TODO kaldırılacak
        delay(2000)

        return stringBuilder.toString()
    }


    private val iletProgramciyaEmail: () -> Unit = {
        val emailHedefi = PROGRAMCI_EMAIL
        val emailMevzusu = getString(R.string.email_mevzusu)
        val emailMuhtevasi = getString(R.string.email_muhtevasi)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:?to=$emailHedefi&subject=$emailMevzusu&body=$emailMuhtevasi")
        startActivity(intent)
    }


    @OptIn(ExperimentalCoilApi::class)
    private val silResimleriHafizadan: () -> Unit = {
        Ikazci.izahEt(izahat = "silResimleriHafizadan()")

        var memoryCacheSize = Coil.imageLoader(this).memoryCache?.size ?: 0
        var diskCacheSize = Coil.imageLoader(this).diskCache?.size ?: 0
        Ikazci.izahEt(izahat = "memoryCacheSize: $memoryCacheSize - diskCacheSize: $diskCacheSize")

        val silinenResimlerinAgirligi = (diskCacheSize/1024).toString() + "kiB"

        Coil.imageLoader(this).memoryCache?.clear()
        Coil.imageLoader(this).diskCache?.clear()

        memoryCacheSize = Coil.imageLoader(this).memoryCache?.size ?: 0
        diskCacheSize = Coil.imageLoader(this).diskCache?.size ?: 0
        Ikazci.izahEt(izahat = "memoryCacheSize: $memoryCacheSize - diskCacheSize: $diskCacheSize")

        val resimlerinAgirligi = getString(R.string.tercihler_sahnesi_yafta_hafizadan_silinen_resimlerin_agirligi, silinenResimlerinAgirligi)
        Toast.makeText(this, resimlerinAgirligi, Toast.LENGTH_SHORT).show()
    }

    private val ayarlaPrograminLisanini: (String) -> Unit = {
        Ikazci.izahEt(izahat = "ayarlaPrograminLisanini(): $it")

    }

    private fun gosterReklamiVaktiGeldiyse() {
        if (talimSahnesiViewModel.talimSualiIbresi > 0 &&
            talimSahnesiViewModel.talimSualiIbresi % reklamIdaresi.reklamGostermeSaulAdedi == 0
        ) {
            reklamIdaresi.showInterstitialAd(this)
        }
    }
}


@Composable
fun NavigationView(
    navController: NavHostController,
    ustIdareCubuguDataBuilder: (navController: NavHostController, sahne: Sahne) -> UstIdareCubuguData,
    merkeziSahneViewModel: MerkeziSahneViewModel,
    tercihlerSahnesiViewModel: TercihlerSahnesiViewModel,
    talimSahnesiViewModel: TalimSahnesiViewModel,
    hususilikSiyasetiViewModel: HususilikSiyasetiViewModel
) {
    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        navController = navController,
        startDestination = Sahne.MerkeziSahne.hedef) {

        composable(
            Sahne.MerkeziSahne.hedef,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            merkeziSahneViewModel.ustIdareCubuguData = ustIdareCubuguDataBuilder(navController, Sahne.MerkeziSahne)
            merkeziSahneViewModel.talimeBaslaOnClick = { navController.navigateOnce(Sahne.TalimlerSahnesi.hedef) }

            MerkeziSahne(merkeziSahneViewModel)
        }

        composable(
            Sahne.TercihlerSahnesi.hedef,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            val ustIdareCubuguData = ustIdareCubuguDataBuilder(navController, Sahne.TercihlerSahnesi)
            tercihlerSahnesiViewModel.ustIdareCubuguData = ustIdareCubuguData
            tercihlerSahnesiViewModel.acHususilikSiyasetini = { navController.navigateOnce(Sahne.HususilikSiyasetiSahnesi.hedef) }

            TercihlerSahnesi(tercihlerSahnesiViewModel)
        }

        composable(
            Sahne.TalimlerSahnesi.hedef,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            val ustIdareCubuguData = ustIdareCubuguDataBuilder(navController, Sahne.TalimlerSahnesi)
            talimSahnesiViewModel.ustIdareCubuguData = ustIdareCubuguData

            TalimlerSahnesi(talimSahnesiViewModel)
        }

        composable(
            Sahne.HususilikSiyasetiSahnesi.hedef,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            val ustIdareCubuguData = ustIdareCubuguDataBuilder(navController, Sahne.HususilikSiyasetiSahnesi)
            hususilikSiyasetiViewModel.ustIdareCubuguData = ustIdareCubuguData

            HususilikSiyasetiSahnesi(hususilikSiyasetiViewModel)
        }
    }
}

private fun NavController.navigateOnce(hedef: String) {
    if (this.currentDestination?.route !== hedef) {
        this.navigate(hedef)
    }
}

private fun NavController.navigateOnce(hedef: String,
                                       builder: NavOptionsBuilder.() -> Unit) {
    if (this.currentDestination?.route !== hedef) {
        this.navigate(hedef, builder)
    }
}
