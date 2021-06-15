package com.ehmaugbogo.otpinverificationlibrary.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.XmlRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ehmaugbogo.otpinverificationlibrary.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


/**
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 02 May 2021
 */



internal fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

internal fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}



internal inline fun EditText.onTextChanged(crossinline listen: (String) -> Unit): TextWatcher {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listen(s.toString())
        }
    }
    addTextChangedListener(watcher)
    return watcher
}


internal inline fun EditText.onTextChangedWithView(crossinline listen: (EditText, String) -> Unit): TextWatcher {
    return onTextChanged {
        listen(this@onTextChangedWithView, it)
    }
}



internal val TextInputLayout.textContent get() = editText?.text.toString().trim()
internal val TextView.textContent get() = text.trim().toString()
internal val CharSequence.textContent get() = this.trim().toString()

internal val TextView.hasContent get() = textContent.isNotEmpty()


internal fun Context.getColorrr(@ColorRes color: Int) = ContextCompat.getColor(this, color)
internal fun Fragment.getColorrr(@ColorRes color: Int) = requireContext().getColorrr(color)
internal fun Fragment.getColorState(@ColorRes color: Int) = ColorStateList.valueOf(getColorrr(color))

internal fun Fragment.getXmlDrawable(@XmlRes id: Int): Drawable {
    return Drawable.createFromXml(resources, resources.getXml(id))
}

internal fun View.hide() {
    isVisible = false
}

fun View.hideAtPosition() {
    visibility = View.INVISIBLE
}

fun View.isVisibleAtPosition(value: Boolean) {
    visibility = if(value) View.VISIBLE else View.INVISIBLE
}

internal fun View.show() {
    isVisible = true
}

internal fun hideViews(vararg views: View) = views.forEach { it.hide() }

internal fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG) {
    showSnackBar(message,this, length)
}

private fun showSnackBar(message: String, view: View, length: Int = Snackbar.LENGTH_LONG): Snackbar {
    val snackBar = Snackbar.make(view, message, length)
    if(length == Snackbar.LENGTH_INDEFINITE) snackBar.setAction("Ok") {}
    snackBar.show()
    return snackBar
}

internal fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

internal fun MaterialCardView.normalizeCornerRadius() {
    this.apply {
        val radius = resources.getDimension(R.dimen.default_corner_radius)

        val shape = shapeAppearanceModel.toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .setBottomLeftCornerSize(0f)
            .setAllCornerSizes(0f)
            .build()

        shapeAppearanceModel = shape
    }
}

 