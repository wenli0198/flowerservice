package com.test.aoner.fanow.test.util_flower

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode


object AppReviewUtil {


    private var mReviewManager: ReviewManager? = null


    private var mReviewInfo: ReviewInfo? = null

    fun init(context: Application) {
        mReviewManager = ReviewManagerFactory.create(context)
        val request = mReviewManager?.requestReviewFlow()
        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mReviewInfo = task.result

            }
        }
    }

    fun launchReviewFlow(activity: AppCompatActivity, completed: (Boolean) -> Unit) {
        if (mReviewInfo == null) {
            completed.invoke(false)
        }

        mReviewInfo?.let {
            val flow = mReviewManager?.launchReviewFlow(activity, it)

            if (flow == null) {
                completed.invoke(false)
            }

            flow?.addOnCompleteListener {
                completed.invoke(true)
            }
        }
    }

}