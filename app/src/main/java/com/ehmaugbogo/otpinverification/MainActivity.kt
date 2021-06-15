package com.ehmaugbogo.otpinverification

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.ehmaugbogo.otpinverification.utils.*
import com.ehmaugbogo.otpinverificationlibrary.OtpinDialogCreator
import com.ehmaugbogo.otpinverificationlibrary.OtpinVerification
import com.ehmaugbogo.otpinverificationlibrary.utils.*


/**
 * (activity_main) Ui credits to
 * https://www.ronnystudio.com/tag/android-ui-templates-source-code-free/
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var otpDialog: OtpDialog
    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClick(R.id.topLayout) { openDefaultOtpDialog() }
        onClick(R.id.firstLayout) { openDefaultOtpDialog(null) }
        onClick(R.id.secondLayout) { enterTransactionPin() }
        onClick(R.id.thirdLayout) { enterCcvPin() }
        onClick(R.id.forthLayout) { enterPinWithBtn() }
        onClick(R.id.fifthLayout) { enterPin() }

        findViewById<RadioGroup>(R.id.radioGroup).setOnCheckedChangeListener { group, checkedId ->
            Styles.style = when (checkedId) {
                R.id.underline -> StyleChoice.Underline
                R.id.outlineNormal -> StyleChoice.OutlineNormal
                R.id.outlineRounded -> StyleChoice.OutlineRounded
                R.id.outlineCut -> StyleChoice.OutlineCut
                else -> throw IllegalStateException("Wrong Id")
            }
        }

    }

    private fun onClick(@IdRes id : Int, listen: ()-> Unit) =
        findViewById<View>(id).setOnClickListener { listen() }


    private fun openDefaultOtpDialog() {
        otpDialog = getDialogBuilder(R.drawable.main_image)
            .otpFields(OtpFields.FIVE)
            .displayMode(OtpDisplay.FullScreen(showToolbar = true))
            .start()
    }

    private fun openDefaultOtpDialog(logoId: Int?) {
        otpDialog = getDialogBuilder(logoId)
            .inputType(OtpInputType.TEXT)
            .autoSubmitOnComplete(hideContinueBtn = true)
            .start()
    }

    private fun getDialogBuilder(logoId: Int?) =
        OtpinDialogCreator.with(this)
        //.title(title)
        //.customSubtitle("Please provide your card cvv")
        //.customBtnText("Submit")
        .logo(logoId)
        //.otpFields(OtpFields.FOUR)
        //.inputType(OtpInputType.DIGIT)
        .cancelable(true)
        .countDown(5)
        .setCountDownFinishListener { showToast("Count Down completed") }
        .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp) }
        .setResendListener { resendClicked(it) }
        .setCancelListener { showToast("Task Cancelled") }
        .displayMode(OtpDisplay.FullScreen())
        //.autoSubmitOnComplete(hideContinueBtn = false)
        //.excludeResend()
        //.displayOnlyInputFields()
        //.windowAnimation()
        //.disableWindowAnimation()
        //.boxShape() // Use styles instead
        .theme(Styles.myOtpDialogTheme)

    private fun enterTransactionPin() {
        getTransactionPinDialog()
            .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp) }
            .start()
    }

    private fun enterCcvPin() {
        getCcvPinDialog()
            .logo(null)
            .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp) }
            .start()
    }

    private fun enterPinWithBtn() {
        getCcvPinDialog()
            .customBtnText("Submit")
            .inputType(OtpInputType.TEXT)
            .theme(Styles.myOtpDialogThemeJustBox)
            .displayOnlyInputFields()
            .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp, 0) }
            .start()
    }

    private fun enterPin() {
        getCcvPinDialog()
            .otpFields(OtpFields.TWO)
            .displayOnlyInputFields()
            .customBtnText("Submit")
            .autoSubmitOnComplete(hideContinueBtn = true)
            .theme(Styles.myOtpDialogThemeJustBox)
            .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp, 0) }
            .start()
    }


    private fun continueClicked(dialog: OtpDialog, otp: String, intervalInSec: Long = 1) {
        mockApiCall(intervalInSec) { // OnApiSuccess ->
            dialog.hideProgress()
            dialog.showMessage("$title success {$otp}", false)
            dialog.dismissDialog()
        }
    }


    private fun resendClicked(owner: OtpCountDownOwner) {
        owner.showMessage("Resending Otp", false)

        mockApiCall() { // OnApiSuccess ->
            owner.hideProgress()
            owner.showMessage("Otp resent successfully")
            owner.restartCountDown() // To restart count down if it was initially specified
        }
    }

    private fun setOtpText(text: String) {
        otpDialog.setOtpInputsText(text)
    }


    /**
     * How to implement when you have your own layout view and editTexts
     *
     */
    private fun startOtpVerificationWithCountDown(){
        val yourFourOrSixEditTextFields = listOf<EditText>()

        val otp =OtpinVerification(yourFourOrSixEditTextFields) { isVerified, percent, otp ->
            // continueBtn.isEnabled = isVerified
            // optInputsValidated = isVerified
            // if (isVerified){ ... }
        }.startCountDown(2, otpCountDownListener)
    }

    private val otpCountDownListener = object : OtpCountDownListener {
        override fun onCountDown(sec: Int, minutes: Int, timeFormat: String, onFinish: Boolean) {
            // your code here
            if (onFinish) {

            }
        }
    }


}