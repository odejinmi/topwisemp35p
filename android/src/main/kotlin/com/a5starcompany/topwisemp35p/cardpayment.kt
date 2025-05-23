package com.a5starcompany.topwisemp35p

import com.a5starcompany.topwisemp35p.emvreader.card.CardMoniterService
import com.a5starcompany.topwisemp35p.emvreader.TopWiseDevice
import com.a5starcompany.topwisemp35p.emvreader.util.StringUtil
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class cardpayment(val topWiseDevice : TopWiseDevice, val result: MethodChannel.Result, val binding: ActivityPluginBinding) {


    /**
     * It is invoked when making transaction
     * @param arg is the data that was passed in from the flutter side to make payment
     */
    fun makePayment(call: MethodCall) {

        if (!(call.arguments is Map<*,*>)) {
            result.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
            return
        }

        val amount = call.argument<String>("amount")!!
        topWiseDevice.readCard(amount)
    }

    fun enterpin(call: MethodCall) {

        if (!(call.arguments is Map<*,*>)) {
            result.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
            return
        }
// Convert the string to a ByteArray
        val directpin: String = call.argument<String>("pin")!!
        topWiseDevice.enterpin(directpin)
    }

    fun cancelcardprocess() {

//        if (!(call.arguments is Map<*,*>)) {
//            result.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
//            return
//        }
//
//        val amount = call.argument<String>("amount")!!

        topWiseDevice.closeCardReader()
    }

    fun getcardsheme(call: MethodCall) {

        if (!(call.arguments is Map<*,*>)) {
            result.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
            return
        }

        val amount = call.argument<String>("amount")!!
        topWiseDevice.getCardScheme(amount)

    }

}