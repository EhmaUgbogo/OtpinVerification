package com.ehmaugbogo.otpinverificationlibrary

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ehmaugbogo.otpinverificationlibrary.databinding.DialogOtpBinding
import com.ehmaugbogo.otpinverificationlibrary.utils.*
import com.ehmaugbogo.otpinverificationlibrary.utils.interfaces.ContinueClickListener
import com.ehmaugbogo.otpinverificationlibrary.utils.interfaces.CancelListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.parcelize.Parcelize


/**
 * Singleton class to Create OtpDialog Object
 *
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 11 May 2021
 */

class OtpinDialogCreator {

    companion object {
        private var tempBuilder: Builder? = null

        /**
         * Use this to use OtpDialog in Activity Class
         *
         * @param activity Activity Instance
         */
        @JvmStatic
        fun with(activity: FragmentActivity): Builder {
            return Builder(activity)
        }

        /**
         * Use this to use OtpDialog in Fragment Class
         *
         * @param fragment Fragment Instance
         */
        @JvmStatic
        fun with(fragment: Fragment): Builder {
            return Builder(fragment)
        }

    }


    class Builder(private val activity: FragmentActivity) {

        /**
         * Call this while calling from fragment.
         */
        constructor(fragment: Fragment) : this(fragment.requireActivity()) {
            this.fragment = fragment
        }

        private var fragment: Fragment? = null


        /**
         * Title of the kind of operation needing otp eg. Forgot Password, Verification etc.
         */
        private var title: String? = null

        /**
         * Subtitle to replace default subtitle (enter_the_4_digit_otp_we_sent_to_you)
         */
        private var customSubtitle: String? = null

        /**
         * text to replace default continue button text
         */
        private var customBtnText: String? = null

        /**
         * the resource identifier of your logo drawable
         */
        private var logoId: Int? = null

        /**
         * Count down timer in minutes.
         *
         * Defaults of zero means No timer
         */
        private var countDownMins: Long = 0

        /**
         * Determine if Otp EditText inputs should be 4 or 6
         */
        private var otpFields: OtpFields = OtpFields.FOUR

        /**
         * Determine if each EditText input type should be Digit {9031}, or Text {x0Mv}
         */
        private var inputType: OtpInputType = OtpInputType.DIGIT

        /**
         * Determine if dialog should be cancelable or not
         */
        private var cancelable: Boolean = false

        /**
         * Determine if each EditText input type should be Digit {9031}, or Text {x0Mv}
         */
        private var boxShape: BoxShape = BoxShape.NORMAL


        /**
         * Determine if dialog should be floating or fullScreen
         */
        private var display: OtpDisplay = OtpDisplay.FULL_SCREEN

        /**
         * Displays only InputFields
         */
        private var displayOnlyInputFields = false

        /**
         * Excludes resend
         */
        private var excludeResend = false

        /**
         * indicate if onCompleteListener should be called when all otpFields are filled
         */
        private var autoSubmitOnComplete = false

        /**
         * Include your own theme
         *
         * should extend from OtpFullScreenDialog
         */
        private var dialogTheme: Int? = null

        /**
         * Set dialog Window animation
         *
         */
        private var windowAnim: Int? = null

        /**
         * Disable dialog Window animation
         *
         */
        private var disableWindowAnim = false


        /**
         * Continue button event listener
         */
        private var continueListener: ((dialog: OtpDialog, otp: String) -> Unit)? = null

        /**
         * Resend button event listener
         */
        private var resendListener: ((OtpCountDownOwner) -> Unit)? = null

        /**
         * CountDown dismiss event listener
         */
        private var countDownListener: (() -> Unit)? = null

        /**
         * Dialog dismiss event listener
         */
        private var cancelListener: (() -> Unit)? = null


        /**
         * Should indicate operation needing otp eg. Forgot Password, Verification etc.
         * Sets title to the main dialog TextView
         *
         *  @param title
         */
        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        /**
         * Replaces default subtitle (enter_the_4_digit_otp_we_sent_to_you)
         *
         *  @param subtitle
         */
        fun customSubtitle(subtitle: String): Builder {
            this.customSubtitle = subtitle
            return this
        }

        /**
         * Replaces default continue button text
         *
         *  @param btnText
         */
        fun customBtnText(btnText: String): Builder {
            this.customBtnText = btnText
            return this
        }

        /**
         * Set a custom DrawableRes logo that represent app brand { null by default }
         *
         *  @param logoId
         */
        // @Deprecated("Please use provider(OtpDialogManufacturer.logo) instead")
        fun logo(@DrawableRes logoId: Int?): Builder {
            this.logoId = logoId
            return this
        }

        /**
         * Count down timer in minutes.
         *
         *  @param countDownMins
         */
        fun countDown(countDownMins: Long): Builder {
            if (!displayOnlyInputFields)
                this.countDownMins = countDownMins
            return this
        }

        /**
         * Determine if Otp EditText inputs should be 4 or 6
         *
         *  @param otpFields OtpFields.FOUR by default
         */
        fun otpFields(otpFields: OtpFields): Builder {
            this.otpFields = otpFields
            return this
        }

        /**
         * Determine if each EditText input type should be Digit {eg 9031}, or Text {eg x0Mv}
         *
         *  @param inputType Defaults to OtpInputType.DIGIT
         */
        fun inputType(inputType: OtpInputType): Builder {
            this.inputType = inputType
            return this
        }


        /**
         * Determine if dialog should be cancels on onBackPressed
         *
         *  @param cancelable false by default
         */
        fun cancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        /**
         * Determine if each TextInputLayout shape type {@see BoxShape - NORMAL, ROUNDED, CUT}
         *
         *  @param boxShape
         */
        @Deprecated("Apply on dialog theme using style {<item name=\"otpDialogTextInputBoxShape\">@style/OtpBoxShape.Rounded</item>} instead")
        fun boxShape(boxShape: BoxShape): Builder {
            this.boxShape = boxShape
            return this
        }

        /**
         * Determine if dialog should be floating or fullScreen
         * Floating only available when OtpFields is Four or less
         *
         *  @param display
         */
        fun displayMode(display: OtpDisplay): Builder {
            this.display = display
            return this
        }

        /**
         * Displays only InputFields
         * Nullifies count down if applied
         */
        fun displayOnlyInputFields(): Builder {
            this.displayOnlyInputFields = true
            countDownMins = 0
            return this
        }

        /**
         * Excludes resend
         */
        fun excludeResend(): Builder {
            this.excludeResend = true
            return this
        }

        /**
         * Calls onCompleteListener when all otpFields are filled
         */
        fun autoSubmitOnComplete(): Builder {
            this.autoSubmitOnComplete = true
            return this
        }

        /**
         * Set your own theme that extends from OtpDialogTheme
         *
         *  @param theme
         */
        fun theme(@StyleRes theme: Int): Builder {
            this.dialogTheme = theme
            return this
        }

        /**
         * Set dialog Window animation (Enters from bottom by default)
         *
         *  @param resId
         */
        fun windowAnimation(@StyleRes resId: Int): Builder {
            this.windowAnim = resId
            return this
        }

        /**
         * Disable dialog Window animation
         *
         */
        fun disableWindowAnimation(): Builder {
            this.disableWindowAnim = true
            return this
        }


        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         */
        fun setContinueListener(listener: ((OtpDialog, String) -> Unit)): Builder {
            this.continueListener = listener
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         */
        fun setResendListener(listener: ((OtpCountDownOwner) -> Unit)): Builder {
            this.resendListener = listener
            return this
        }


        /**
         * Sets the callback that will be called when CountDown Finishes if there is.
         */
        fun setCountDownFinishListener(listener: (() -> Unit)): Builder {
            this.countDownListener = listener
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         */
        fun setCancelListener(listener: (() -> Unit)): Builder {
            this.cancelListener = listener
            return this
        }


        /**
         * Start Otp Dialog
         */
        fun start(): OtpDialog {
            tempBuilder = this
            val fm = if (fragment != null) fragment?.parentFragmentManager!!
            else activity.supportFragmentManager

            return OtpDialogImpl().apply { show(fm, "OtpDialog") }
        }



        /**
         * Create OtpDialog Object
         *
         * @author Ehma Ugbogo
         * @version 1.0
         * @since 17 Apr 2021
         */
        internal class OtpDialogImpl : DialogFragment(), OtpDialog, OtpCountDownOwner {
            private var binding: DialogOtpBinding by autoCleaned()
            private lateinit var otpin: OtpinVerification
            private var optInputsValidated = false

            private var otpText = ""
            private var continueBtnText = ""
            private lateinit var toolBar: Toolbar
            private val builder: Builder get() = tempBuilder!!



            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                isCancelable = builder.cancelable
                val theme = builder.dialogTheme ?: R.style.OtpDialogTheme
                setStyle(STYLE_NORMAL, theme)
            }

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? = inflater.inflate(R.layout.dialog_otp, container, false)


            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                binding = DialogOtpBinding.bind(view).also { it.init() }

            }

            private fun DialogOtpBinding.init() {
                setupAppearance()

                val optInputFields = inputFields()
                val inputType = builder.inputType
                val autoSubmitOnComplete = builder.autoSubmitOnComplete

                otpin = OtpinVerification(optInputFields, inputType) { verified, percent, otp ->
                    otpText = otp
                    optInputsValidated = verified
                    continueBtn.isEnabled = verified

                    if(autoSubmitOnComplete && verified) proceed()
                }

                startCountDown()

            }

            private fun DialogOtpBinding.setupAppearance() {
                toolbar.apply {
                    setNavigationOnClickListener { v -> onCancel(); dismiss() }
                    /*inflateMenu(R.menu.menu_dialog)
                    setOnMenuItemClickListener { item: MenuItem? ->
                        onCancel(); dismiss()
                        true
                    }*/
                }.also { toolBar = it }

                builder.apply {
                    title?.let { titleTv.text = it }
                    customBtnText?.let { continueBtn.text = it }
                    logoId?.let { logoImg.apply { setImageResource(it); show() } }
                    //continueBtn.isVisible = !autoSubmitOnComplete
                }

                continueBtnText = continueBtn.textContent
                continueBtn.setOnClickListener { proceed() }
                resendTv.setOnClickListener { resend() }
                resendLayout.isVisible = !builder.excludeResend
            }

            private fun DialogOtpBinding.inputFields(): List<EditText> {
                val optInputFields = mutableListOf(
                    verifyInput1, verifyInput2,
                    verifyInput3, verifyInput4,
                    verifyInput5, verifyInput6
                )

                val num = builder.otpFields.value
                val numToRemove = optInputFields.size - num
                val boxesToRemove = mutableListOf<TextInputLayout>()

                for (i in 1..numToRemove) boxesToRemove.add(optInputFields[i]) // avoid first (and last) - They hold up box constraints
                boxesToRemove.forEach {
                    it.isVisible = false; optInputFields.remove(it)
                }

                var msg = "Enter the $num - Digit OTP we sent to You"
                builder.customSubtitle?.let { msg = it }
                subtitleTv.text = msg

                return optInputFields.map { it.editText!! }
            }

            private fun DialogOtpBinding.startCountDown() {
                if (builder.countDownMins <= 0) return

                otpin.startCountDown(builder.countDownMins, object : OtpCountDownListener {
                    override fun onCountDown(
                        sec: Int,
                        minutes: Int,
                        timeFormat: String,
                        onFinish: Boolean
                    ) {
                        countDownTv.text = timeFormat
                        if (minutes < 1) countDownTv.setTextColor(getColorrr(android.R.color.holo_red_light))
                        else countDownTv.setTextColor(getColorrr(R.color.otp_title_color))
                        if (onFinish) builder.countDownListener?.invoke()
                    }
                })
            }

            private fun proceed() = binding.apply {
                if (!optInputsValidated) return@apply
                showProgress()
                hideKeyboard()
                builder.continueListener?.invoke(this@OtpDialogImpl, otpText)
            }

            override fun setOtpInputsText(otpText: String) = otpin.setOtpInputsText(otpText)

            private fun onCancel() {
                cancelCountDown(); builder.cancelListener?.invoke(); clearTemp()
            }

            override fun dismissDialog() {
                cancelCountDown(); clearTemp(); dismiss()
            }

            private fun cancelCountDown() = otpin.cancelCountDown()

            private fun clearTemp() = null.also { tempBuilder = it }

            private fun resend() {
                showProgress()
                cancelCountDown()
                clearInputs()
                builder.resendListener?.invoke(this)
            }

            private fun clearInputs() {
                hideKeyboard()
                otpin.clearInputs()
            }

            override fun restartCountDown() {
                binding.startCountDown()
            }

            private fun showProgress() {
                binding.apply {
                    progressBar.show()
                    continueBtn.text = ""
                    continueBtn.isEnabled = false
                    binding.resendTv.isEnabled = false
                }
            }

            override fun hideProgress() {
                binding.apply {
                    progressBar.hide()
                    continueBtn.text = continueBtnText
                    continueBtn.isEnabled = optInputsValidated
                    binding.resendTv.isEnabled = true
                }
            }

            override fun showMessage(msg: String, useSnackInsteadOfToast: Boolean) {
                if (useSnackInsteadOfToast && builder.display != OtpDisplay.FLOAT)
                    binding.resendTv.showSnackBar(msg)
                else showToast(msg)
            }

            private fun showToast(msg: String) {
                context?.showToast(msg)
            }

            override fun onResume() {
                super.onResume()
                dialog?.apply {
                    when (builder.display) {
                        OtpDisplay.FLOAT -> makeFloatScreen()
                        OtpDisplay.FULL_SCREEN -> makeFullScreen()
                    }

                    if (builder.displayOnlyInputFields) binding.apply {
                        hideViews(logoImg, titleTv, subtitleTv, countDownTv, resendLayout)
                    }

                    animateWindow()
                    setOnCancelListener { onCancel() }
                }
            }

            private fun Dialog.makeFloatScreen() {
                hideViews(toolBar)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

            private fun Dialog.makeFullScreen() {
                val width = ViewGroup.LayoutParams.MATCH_PARENT
                val height = ViewGroup.LayoutParams.MATCH_PARENT
                window?.setLayout(width, height)
            }

            private fun Dialog.animateWindow() {
                val windowAnim = builder.windowAnim ?: R.style.SlideUp
                if (!builder.disableWindowAnim) window?.setWindowAnimations(windowAnim)
            }

        }


    }

}

