package com.ehmaugbogo.otpinverification

import android.app.Activity
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.TimeUnit



/**
 * @author Ehma Ugbogo
 * @version 1.0
 * @since 17 Apr 2021
 */


fun FragmentActivity.mockApiCall(onSuccess: ()-> Unit){
    runAtInterval(3) {
        onSuccess.invoke()
        false
    }
}

fun Fragment.runAtInterval(intervalInSec: Long, reExecute: ()-> Boolean) =
    requireActivity().runAtInterval(intervalInSec, reExecute)

fun Activity.runAtInterval(intervalInSec: Long, reExecute: ()-> Boolean){
    RunAtInterval(intervalInSec, Handler(mainLooper), reExecute)
}

private class RunAtInterval(private val intervalInSec: Long, private val handler: Handler, reExecute: ()-> Boolean) {
    private val runnable = {
        if(reExecute()) startNewSession()
        else releaseResource()
    }

    init {
        startNewSession()
    }

    private fun startNewSession(){
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(intervalInSec))
    }

    private fun releaseResource() {
        handler.removeCallbacks(runnable)
    }

}

