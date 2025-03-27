package com.lonytech.app

import android.app.Activity
import android.util.Log
import com.lonytech.topwisesdk.charackterEncoder.BCDASCII
import com.lonytech.topwisesdk.emvreader.DeviceTopUsdkServiceManager
import com.lonytech.topwisesdk.emvreader.app.PosApplication
import com.lonytech.topwisesdk.emvreader.card.*
import com.lonytech.topwisesdk.emvreader.TopWiseDevice
import com.lonytech.topwisesdk.emvreader.util.*

class cardpayment(val topWiseDevice : TopWiseDevice, val binding: Activity) {


    /**
     * It is invoked when making transaction
     * @param arg is the data that was passed in from the flutter side to make payment
     */
    fun makePayment(amount: String) {
        topWiseDevice.readCard(amount,"","")
    }

    fun enterpin(directpin: String) {

        val pin: ByteArray = BCDASCII.hexStringToBytes(directpin)
//        val pin = call.argument<String>("pin")!!
        Log.i("TAG", "onConfirmInput(), pin = " + BCDASCII.bytesToHexString(pin))
        val mCardNo = PosApplication.getApp().mConsumeData?.cardno
//        var finalPan = ""
//        mCardNo?.let {
//            val numbersOfStars =
//                mCardNo.length - (mCardNo.take(5).length + mCardNo.takeLast(4).length)
//            var stars = ""
//            for (i in 1..numbersOfStars)
//                stars += "*"
//            finalPan = mCardNo.take(5) + stars + mCardNo!!.takeLast(4)
//        }
         PosApplication.getApp().mConsumeData?.pin = pin
         PosApplication.getApp().mConsumeData?.pinBlock = BCDASCII.bytesToHexString(
            Format.pinblock(
                mCardNo,
                directpin
            )
        )

//            if (isOnline) {
//                //socket通信
//                val bundle = Bundle()
//                bundle.putInt(
//                    PacketProcessUtils.PACKET_PROCESS_TYPE,
//                    PacketProcessUtils.PACKET_PROCESS_CONSUME
//                )
////                CardManager.getInstance()
////                    .startActivity(this@PinpadActivity, bundle, PacketProcessActivity::class.java)
//                /*byte[] sendData = PosApplication.Companion.getApp().mConsumeData.getICData();
//                Log.d(TAG, BCDASCII.bytesToHexString(sendData));
//                JsonAndHttpsUtils.sendJsonData(mContext, BCDASCII.bytesToHexString(sendData));*/
//            } else {
////                if (ConsumeData.CARD_TYPE_MAG === mCardType) {
////                    //val intent = Intent(this@PinpadActivity, PacketProcessActivity::class.java)
////                    intent.putExtra(
////                        PacketProcessUtils.PACKET_PROCESS_TYPE,
////                        PacketProcessUtils.PACKET_PROCESS_CONSUME
////                    )
////                    startActivity(intent)
////                } else {
////
////                }
//
//                if (pin == null) {
//                    CardManager.instance.setImportPin("000000")
//                } else {
//
//                }
//            }

        CardManager.instance.setImportPin(directpin)
    }

    fun cancelcardprocess() {

//        if (!(call.arguments is Map<*,*>)) {
//            result?.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
//            return
//        }
//
//        val amount = call.argument<String>("amount")!!

        CardManager.instance.stopCardDealService(binding)
    }

    fun getcardsheme(amount: String) {
        topWiseDevice.getCardScheme(amount)

    }

}