package com.ehmaugbogo.otpinverificationlibrary

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import com.ehmaugbogo.otpinverificationlibrary.utils.*
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


/**
 * Create OtpInputsVerification Object
 *
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 02 May 2021
 */


interface OtpinVerificationListener{
    fun onVerified(verified: Boolean, percent: Int, pin: String)
}

class OtpinVerification(
    private val inputFields: List<EditText>,
    private val inputType: OtpInputType = OtpInputType.DIGIT,
    private val onVerified: (verified: Boolean, percent: Int, text: String) -> Unit
) {
    private var textWatchers = mutableListOf<TextWatcher>()

    private var encounteredError = false // Useful for OtpInputType.DIGIT during setOtpInputsText()
    private val unMatchLengthError = "optText length does not match number of inputFields."
    private val notCompleteDigitError = "Otp contains character that is not digit, Consider using OtpInputType.TEXT"

    private val context get() = inputFields[0].context
    private var countDownTimer: CountDownTimer? = null

    private var otpText = ""
    private var populatedFieldCounter = 0
    private var percentageFilled = 0

    init {
        inputFields.forEachIndexed { index, editText ->
            setup(editText)
            textWatchers.add(addTextChangeListener(index, editText))
        }
    }

    private fun setup(editText: EditText) {
        val type = when (inputType) {
            OtpInputType.DIGIT -> InputType.TYPE_CLASS_NUMBER
            OtpInputType.TEXT -> InputType.TYPE_CLASS_TEXT
            //OtpInputType.DIGIT_PASSWORD -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            //OtpInputType.TEXT_PASSWORD -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        editText.inputType = type
        editText.maxLines = 1
    }

    private fun addTextChangeListener(index: Int, editText: EditText): TextWatcher {
        addCursorManagement(editText)
        return editText.onTextChangedWithView { current, s ->
            encounteredError = false
            jumpToNext(current, getNextInputField(current))
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addCursorManagement(editText: EditText) {
        editText.setOnTouchListener { v, event ->
            val edt = v as EditText
            edt.onTouchEvent(event)
            if (edt.hasFocus() && edt.hasContent) edt.setSelection(edt.textContent.length)
            true
        }
    }

    private fun getNextInputField(current: EditText): EditText? {
        val nextPosition = inputFields.indexOf(current) + 1
        return if (nextPosition < inputFields.size) inputFields[nextPosition] else null
    }


    private fun jumpToNext(current: EditText, next: EditText? = null) {
        if (jumpToPrevIfEmpty(current)) { // Prev not filled
            current.text?.clear()
        } else { // Prev(s) already filled so determine if current should jump to next
            if (current.textContent.isNotEmpty() && next?.textContent?.isEmpty() == true) next.requestFocus()
        }

        reportValidated(current)

    }

    private fun jumpToPrevIfEmpty(current: EditText): Boolean {
        val position = inputFields.indexOf(current)

        inputFields.forEachIndexed { index, inputEditText ->
            if (index == position) return false // No need looping beyond position so program is faster
            if (inputEditText.textContent.isEmpty()) {
                inputEditText.requestFocus(); return true
            }
        }
        return false
    }


    private fun reportValidated(current: EditText) {
        val valid = validateInputs()
        if (valid) { hideKeyboard(); current.clearFocus() }

        onVerified(valid, percentageFilled, otpText)
    }

    /**This method also sort out otp text*/
    private fun validateInputs(): Boolean {
        if (encounteredError) return false
        reset()

        inputFields.forEach {
            if (it.textContent.isEmpty()) {
                it.requestFocus(); return false
            } else {
                otpText += it.textContent
                populatedFieldCounter++
                percentageFilled = getPercentageFilled()
            }
        }
        return true
    }

    private fun reset() {
        otpText = ""
        populatedFieldCounter = 0
    }

    private fun getPercentageFilled(): Int {
        return (populatedFieldCounter / inputFields.size) * 100
    }


    /** Set Otp on input fields */
    fun setOtpInputsText(optText: String) {
        removeWatchers()
        if (optText.length != inputFields.size) showSnackBar(unMatchLengthError)

        optText.forEachIndexed { index, char ->
            if (index < inputFields.size) handleSetText(index, char)
        }

        if (encounteredError) showSnackBar(notCompleteDigitError)
        reportValidated(inputFields[optText.lastIndex])
        addWatchers()
    }

    private fun handleSetText(index: Int, char: Char) {
        if (inputType == OtpInputType.DIGIT) {
            if (!affirmCharIsNum(char)) encounteredError = true
            //return
        }

        inputFields[index].setText(char.toString())
    }

    private fun affirmCharIsNum(char: Char): Boolean {
        val positiveNumber = Pattern.compile("[0-9]\\d*")
        return positiveNumber.matcher(char.toString())
            .matches() // is a string containing just number(s) 0-9
    }

    fun clearInputs() {
        inputFields.forEachIndexed { index, editText ->
            if (index == populatedFieldCounter) return@forEachIndexed //No need looping beyond position so program is faster
            editText.text.clear()
        }
        inputFields.forEach { if (it.hasFocus()) it.clearFocus() }
    }

    private fun showSnackBar(msg: String) {
        inputFields[0].showSnackBar(msg, Snackbar.LENGTH_INDEFINITE)
    }

    private fun hideKeyboard() {
        context.hideKeyboard(inputFields[0])
    }

    private fun removeWatchers() {
        inputFields.forEachIndexed { index, editText ->
            editText.removeTextChangedListener(textWatchers[index])
        }
    }

    private fun addWatchers() {
        inputFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(textWatchers[index])
        }
    }

    fun startCountDown(mins: Long, listener: OtpCountDownListener) {
        val toMillis = TimeUnit.MINUTES.toMillis(mins) + 300
        countDownTimer = object : CountDownTimer(toMillis, TimeUnit.SECONDS.toMillis(1)) {
            override fun onTick(millisUntilFinished: Long) =
                getFormat(millisUntilFinished, listener)

            override fun onFinish() {
                listener.onCountDown(0, 0, "00:00", true)
                cancelCountDown()
            }
        }
        countDownTimer?.start()
    }

    fun cancelCountDown(){
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun getFormat(millisUntilFinished: Long, listener: OtpCountDownListener) {
        val sec = (millisUntilFinished / 1000).toInt() % 60
        val minutes = (millisUntilFinished / 1000).toInt() / 60
        val format = java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, sec)
        listener.onCountDown(sec, minutes, format, false)
    }


}