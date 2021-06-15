package com.ehmaugbogo.otpinverification.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.ehmaugbogo.otpinverification.R
import com.ehmaugbogo.otpinverificationlibrary.OtpinDialogCreator
import com.ehmaugbogo.otpinverificationlibrary.utils.OtpDisplay
import com.ehmaugbogo.otpinverificationlibrary.utils.OtpFields
import com.ehmaugbogo.otpinverificationlibrary.utils.OtpInputType


/**
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 13 june 2021
 */


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun FragmentActivity.getCcvPinDialog(): OtpinDialogCreator.Builder {
    return getDefaultFloatingDialog("Ccv")
        .customSubtitle("Please provide your card cvv")
        .displayMode(OtpDisplay.Float)
        .otpFields(OtpFields.THREE)
        //.autoSubmitOnComplete()
        .excludeResend()
    //.countDown(5)

}

fun FragmentActivity.getTransactionPinDialog(): OtpinDialogCreator.Builder {
    return getDefaultFloatingDialog("Transaction PIN")
        .customSubtitle("You are about to purchase \"Top Workplace design for UI Designers.\"")
        .displayMode(OtpDisplay.Float)
        //.autoSubmitOnComplete()
        .excludeResend()
    //.countDown(5)

}

fun FragmentActivity.getDefaultFloatingDialog(title: String? = null): OtpinDialogCreator.Builder {
    return OtpinDialogCreator.with(this)
        .title(title)
        .logo(R.drawable.img_one)
        .otpFields(OtpFields.FOUR)
        .inputType(OtpInputType.DIGIT)
        .cancelable(true)
        .setCountDownFinishListener { showToast("Count Down completed") }
        .setCancelListener { showToast("Task Cancelled") }
        .displayMode(OtpDisplay.FullScreen())
        .theme(Styles.myOtpDialogThemeFloating)
        //.countDown(5)

}