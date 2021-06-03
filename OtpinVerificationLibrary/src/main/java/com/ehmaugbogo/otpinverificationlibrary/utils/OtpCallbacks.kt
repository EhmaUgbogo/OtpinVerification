package com.ehmaugbogo.otpinverificationlibrary.utils



/**
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 02 May 2021
 */


interface OtpMessenger{
    fun hideProgress()
    fun showMessage(msg: String, useSnackInsteadOfToast: Boolean = true)
}

interface OtpDialog : OtpMessenger{
    fun setOtpInputsText(otpText: String)
    fun dismissDialog()
}

interface OtpCountDownOwner : OtpMessenger{
    fun restartCountDown()
}

interface OtpCountDownListener {
    fun onCountDown(sec: Int, minutes: Int, timeFormat: String, onFinish: Boolean)
}


enum class OtpDisplay {
    FLOAT, FULL_SCREEN
}

enum class OtpFields(val value: Int) {
    THREE(3), FOUR(4), FIVE(5), SIX(6)
}

enum class OtpInputType {
    DIGIT, TEXT//, DIGIT_PASSWORD, TEXT_PASSWORD
}


enum class BoxShape {
    NORMAL, ROUNDED, CUT
}