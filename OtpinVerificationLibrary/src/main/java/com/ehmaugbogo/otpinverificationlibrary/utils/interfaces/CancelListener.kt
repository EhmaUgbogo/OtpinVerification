package com.ehmaugbogo.otpinverificationlibrary.utils.interfaces


/**
 * Interface used to allow the creator of a dialog to run some code when the
 * dialog is dismissed.
 *
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 11 May 2021
 */
interface CancelListener {

    /**
     * This method will be invoked when the dialog is dismissed.
     */
    fun onDismiss()
}