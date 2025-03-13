package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.PamGoogleInstall
import com.test.aoner.fanow.test.analytics.flowerhttp.FlowerHttpHelper
import com.test.aoner.fanow.test.activity_fragment_flower.base_flower.BaseApplication_flower
import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower
import com.test.aoner.fanow.test.constant_flower.Constant_flower
import com.test.aoner.fanow.test.constant_flower.Url_flower
import java.sql.DriverManager.println

object FlowerGoogleInstallUtil {


    private var mInstallReferrer: String = ""

    private var mInstallReferrerClient: InstallReferrerClient =
        InstallReferrerClient.newBuilder(BaseApplication_flower.getApplication_flower()).build()


    fun init(context: Application) {
        init(context) { saveS2sInfo() }
    }


    fun init(context: Application, success: (String) -> Unit) {
        try {
            if (mInstallReferrer.isBlank()) {
                mInstallReferrerClient.startConnection(object :
                    InstallReferrerStateListener {
                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK -> {
                                try {
                                    println("google install call back")
                                    val response = mInstallReferrerClient.installReferrer
                                    mInstallReferrer = response?.installReferrer ?: ""
                                    mInstallReferrerClient.endConnection()

                                    success.invoke(mInstallReferrer)

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    mInstallReferrerClient.endConnection()
                                }
                            }
                            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {

                                println("FEATURE_NOT_SUPPORTED")
                                mInstallReferrerClient.endConnection()
                            }
                            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {

                                println("SERVICE_UNAVAILABLE")
                                mInstallReferrerClient.endConnection()
                            }
                        }
                    }

                    override fun onInstallReferrerServiceDisconnected() {
                    }
                })
            } else {
                success.invoke(mInstallReferrer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getInstallReferrer(): String {
        return mInstallReferrer
    }


    fun saveS2sInfo() {
        val params =
            PamGoogleInstall(
                channelCode = getInstallReferrer(),
                subChannelCode = getInstallReferrer()
            )
        val body = params.toParams()
//        val body = Gson().toJson(params).trim()

        if (StaticConfig_flower.hasCountryWord()){
            val path = StaticConfig_flower.getWholeBaseUrl() + Url_flower.PATH_GP_INSTALL_TEMP

            if (TextUtils.isEmpty(path)) {
                if (Constant_flower.DebugFlag) Log.d("---saveGoogleS2sInfo---","path is empty")
            }else{
                if (Constant_flower.DebugFlag) Log.d("---saveGoogleS2sInfo---","path=$path")
                FlowerHttpHelper.postData(path, body)
            }
        }

    }
}