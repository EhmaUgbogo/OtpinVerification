package com.ehmaugbogo.otpinverification.utils

import com.ehmaugbogo.otpinverification.R


/**
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 14 june 2021
 */


enum class StyleChoice {
    Underline, OutlineNormal, OutlineRounded, OutlineCut
}

object Styles {
    var style = StyleChoice.Underline

    val myOtpDialogTheme get() = when (style) {
        StyleChoice.Underline -> R.style.myOtpDialogTheme
        StyleChoice.OutlineNormal -> R.style.myOtpDialogThemeNormal
        StyleChoice.OutlineRounded -> R.style.myOtpDialogThemeRounded
        StyleChoice.OutlineCut -> R.style.myOtpDialogThemeCut
    }

    val myOtpDialogThemeFloating get() = when (style) {
        StyleChoice.Underline -> R.style.myOtpDialogThemeFloating
        StyleChoice.OutlineNormal -> R.style.myOtpDialogThemeFloatingNormal
        StyleChoice.OutlineRounded -> R.style.myOtpDialogThemeFloatingRound
        StyleChoice.OutlineCut -> R.style.myOtpDialogThemeFloatingCut
    }

    val myOtpDialogThemeJustBox get() = when (style) {
        StyleChoice.Underline -> R.style.myOtpDialogThemeJustBox
        StyleChoice.OutlineNormal -> R.style.myOtpDialogThemeJustBoxNormal
        StyleChoice.OutlineRounded -> R.style.myOtpDialogThemeJustBoxRounded
        StyleChoice.OutlineCut -> R.style.myOtpDialogThemeJustBoxCut
    }


}