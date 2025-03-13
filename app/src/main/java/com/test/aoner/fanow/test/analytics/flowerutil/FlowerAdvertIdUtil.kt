package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.core.content.edit
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import java.sql.DriverManager.println

object FlowerAdvertIdUtil {

    const val SP_KEY_GAID = "GAID"

    const val SP_KEY_AD_ENABLE = "ENABLE"

    private var mSp: SharedPreferences = BaseApplication_flower.getApplication_flower().getSharedPreferences(
        Constant_flower.PRODUCT,
        Context.MODE_PRIVATE
    )

    private var mAdEnabled: Int = 0

    private var mAdId: String = ""

    init {
        mAdId = mSp.getString(SP_KEY_GAID, "") ?: ""
        mAdEnabled = mSp.getInt(SP_KEY_AD_ENABLE, 0)
    }

    fun init(context: Application) {
        init(context) { _, _ -> }
    }


    fun init(context: Application, success: (String, Int) -> Unit) {
        if (mAdId.isBlank()) {
            Thread {
                try {
                    if (Looper.getMainLooper() != Looper.myLooper()) {
                        val advertisingIdClient = AdvertisingIdClient.getAdvertisingIdInfo(context)
                        val enable = if (advertisingIdClient.isLimitAdTrackingEnabled) 1 else 0
                        mAdId = advertisingIdClient.id ?: ""
                        mAdEnabled = enable

                        mSp.edit {
                            putInt(SP_KEY_AD_ENABLE, mAdEnabled)
                            putString(SP_KEY_GAID, mAdId)
                            commit()
                        }
                        println("mAdId=$mAdId mIsLimitAdTrackingEnabled=$mAdEnabled")

                        if (mAdId.isNotBlank()) {
                            Handler(Looper.getMainLooper()).post {
                                success.invoke(mAdId, mAdEnabled)
                            }
                        }
                    } else {
                        println("init GAID ERROR!!! Cannot call in the main thread, You must call in the other thread")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }


    fun getAdId(): String {
        return mAdId
    }

    fun getAdEnabled(): Int {
        return mAdEnabled
    }


    fun getAndroidId(): String {
        val androidID = Settings.System.getString(
            BaseApplication_flower.getApplication_flower().contentResolver,
            Settings.Secure.ANDROID_ID
        )
        if (androidID.isBlank())
            return System.currentTimeMillis().toString()

        if (androidID.contains("00000000"))
            return System.currentTimeMillis().toString()

        return androidID
    }


}