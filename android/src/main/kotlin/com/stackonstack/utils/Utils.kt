package com.stackonstack.utils

import android.app.AlertDialog
import android.content.Context
import com.paylony.topwise.emvreader.DeviceTopUsdkServiceManager
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    val transactionRef: String
        get() = SimpleDateFormat("yyMMddhhmmss", Locale.ENGLISH).format(Date())

    fun getExceptionMessage(e: Throwable?): String {
        return when (e) {
            is SocketTimeoutException -> {
                "Timeout, try again later"
            }

            is UnknownHostException -> {
                "weak or no Internet Exception"
            }

            else -> {
                e?.message ?: e?.localizedMessage ?: "An Error Occured"
            }
        }
    }

    fun showDialog(
        context: Context, desc: String?,
        buttonText: String = "OK", action: () -> Unit = {}
    ) {
        try {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setMessage(desc)
            builder.setPositiveButton(
                buttonText
            ) { dialog, _ ->
                dialog.dismiss()
                action()
            }
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    val serialnumber: String
        get() = DeviceTopUsdkServiceManager.instance?.systemManager?.serialNo!!
}