package com.ehmaugbogo.otpinverificationlibrary.utils.interfaces

import com.ehmaugbogo.otpinverificationlibrary.utils.OtpDialog


/**
 * Interface used to allow the creator of a dialog to run some code when the
 * continue button is clicked.
 *
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 11 May 2021
 */
interface ContinueClickListener {

    /**
     * This method will be invoked when the continue button is clicked.
     * Exposes OtpDialog
     */
    fun onContinue(dialog: OtpDialog, otp: String)
}