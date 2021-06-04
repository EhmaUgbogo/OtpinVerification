package com.ehmaugbogo.otpinverification

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ehmaugbogo.otpinverificationlibrary.OtpinDialogCreator
import com.ehmaugbogo.otpinverificationlibrary.OtpinVerification
import com.ehmaugbogo.otpinverificationlibrary.utils.*



class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var otpDialog: OtpDialog
    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress)
        findViewById<Button>(R.id.otpBtn).setOnClickListener { openOtpDialog() }

    }


    private fun openOtpDialog() {
        //title = "Cvv"
        otpDialog = OtpinDialogCreator.with(this)
                //.title(title)
                //.customSubtitle("Please provide your card cvv")
                .customBtnText("Submit")
                //.logo(R.drawable.ic_logo)
                .cancelable(false)
                .otpFields(OtpFields.FOUR)
                .inputType(OtpInputType.DIGIT)
                .countDown(5)
                .setCountDownFinishListener { showToast("Count Down completed") }
                .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp) }
                .setResendListener {resendClicked(it)}
                .setCancelListener { showToast("Task Cancelled") }
                .displayMode(OtpDisplay.FLOAT)
                //.excludeResend()
                //.displayOnlyInputFields()
                //.theme(R.style.OtpStyle)
                //.windowAnimation()
                //.disableWindowAnimation()
                //.boxShape() // Use styles instead
                .start()
    }

    private fun continueClicked(dialog: OtpDialog, otp: String) {
        mockApiCall() { // OnApiSuccess ->
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

    private fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}