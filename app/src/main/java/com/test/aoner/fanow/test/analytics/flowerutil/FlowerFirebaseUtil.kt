package com.test.aoner.fanow.test.analytics.flowerutil

import android.app.Application
import com.test.aoner.fanow.test.analytics.flowerevent.FlowerIAnalyticsEvent
//import com.test.aoner.fanow.test.constant_flower.Url_flower
//import com.google.android.gms.tasks.Task
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.analytics.ktx.analytics
//import com.google.firebase.analytics.ktx.logEvent
//import com.google.firebase.ktx.Firebase
//import com.google.gson.Gson
//import com.test.aoner.fanow.test.analytics.flowerhttp.FlowerHttpHelper
//import com.test.aoner.fanow.test.analytics.flowerdata_sdk.flowerbeans.FlowerParamFirebaseS2S
//import com.test.aoner.fanow.test.bean_flower.StaticConfig_flower


object FlowerFirebaseUtil {

//    private var mFirebaseAnalytics: FirebaseAnalytics = Firebase.analytics
//
//    private var mFirebaseAppId: String = ""
//
//    private var mGa4InstanceId: String = ""


    fun init(context: Application) {
//        try {
//            if (mFirebaseAppId.isBlank()) {
//                Thread {
//                    mFirebaseAppId = FirebaseAnalytics.getInstance(context).firebaseInstanceId
//                    println("mFirebaseAppId=$mFirebaseAppId")
//                }.start()
//            }
//
//            if (mGa4InstanceId.isBlank()) {
//                val gResult: Task<String> = FirebaseAnalytics.getInstance(context).appInstanceId
//                gResult.addOnSuccessListener {
//                    mGa4InstanceId = it
//                    println("mGa4InstanceId=$mGa4InstanceId")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }


    fun addEvent(event: FlowerIAnalyticsEvent) {
//        try {
//            when (event) {
//                FlowerIAnalyticsEvent.AT_REGISTER -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) {
//                        param(FirebaseAnalytics.Param.METHOD, FlowerIAnalyticsEvent.AT_REGISTER.name)
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_BASIC -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP) {
//                        param(
//                            FirebaseAnalytics.Param.CONTENT_TYPE,
//                            FlowerIAnalyticsEvent.AT_BASIC.name
//                        )
//                        param(FirebaseAnalytics.Param.LEVEL, "1")
//                        param(FirebaseAnalytics.Param.SCORE, "100")
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_WORK -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.POST_SCORE) {
//                        param(FirebaseAnalytics.Param.CONTENT_TYPE, FlowerIAnalyticsEvent.AT_WORK.name)
//                        param(FirebaseAnalytics.Param.CHARACTER, FlowerIAnalyticsEvent.AT_WORK.name)
//                        param(FirebaseAnalytics.Param.LEVEL, "1")
//                        param(FirebaseAnalytics.Param.SCORE, "100")
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_CONTACT -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS) {
//                        param(
//                            FirebaseAnalytics.Param.CONTENT_TYPE,
//                            FlowerIAnalyticsEvent.AT_CONTACT.name
//                        )
//                        param(
//                            FirebaseAnalytics.Param.SEARCH_TERM,
//                            FlowerIAnalyticsEvent.AT_CONTACT.name
//                        )
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_REV_CARD -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM) {
//                        param(
//                            FirebaseAnalytics.Param.CONTENT_TYPE,
//                            FlowerIAnalyticsEvent.AT_REV_CARD.name
//                        )
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_LOAN_APPLY -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE) {
//                        param(
//                            FirebaseAnalytics.Param.CONTENT_TYPE,
//                            FlowerIAnalyticsEvent.AT_LOAN_APPLY.name
//                        )
//                        param(
//                            FirebaseAnalytics.Param.AFFILIATION,
//                            FlowerIAnalyticsEvent.AT_LOAN_APPLY.name
//                        )
//                        param(FirebaseAnalytics.Param.COUPON, FlowerIAnalyticsEvent.AT_LOAN_APPLY.name)
//                        param(
//                            FirebaseAnalytics.Param.TRANSACTION_ID,
//                            FlowerIAnalyticsEvent.AT_LOAN_APPLY.name
//                        )
//                        param(FirebaseAnalytics.Param.VALUE, "1")
//                        param(FirebaseAnalytics.Param.SHIPPING, "1")
//                        param(FirebaseAnalytics.Param.TAX, "1")
//                        param(FirebaseAnalytics.Param.CURRENCY, "USD")
//                    }
//                }
//                FlowerIAnalyticsEvent.AT_REPAY -> {
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE) {
//                        param(
//                            FirebaseAnalytics.Param.CONTENT_TYPE,
//                            FlowerIAnalyticsEvent.AT_REPAY.name
//                        )
//                        param(FirebaseAnalytics.Param.ITEM_ID, FlowerIAnalyticsEvent.AT_REPAY.name)
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }


    fun saveS2SInfo() {
//        val params = FlowerParamFirebaseS2S()
//        val body = Gson().toJson(params).trim()
//
//        if (StaticConfig_flower.hasCountryWord()){
//            val pathFormat = StaticConfig_flower.getCountryUrlPrefix()+Url_flower.PATH_FIREBASE_S2S
//            FlowerHttpHelper.postData(pathFormat, body)
//        }
    }


    fun getFirebaseAppId(): String {
//        return mFirebaseAppId

        return ""
    }

    fun getGa4InstanceId(): String {
//        return mGa4InstanceId

        return ""
    }
}