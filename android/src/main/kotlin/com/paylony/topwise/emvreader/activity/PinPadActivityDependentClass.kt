package com.paylony.topwise.emvreader.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.paylony.charackterEncoder.BCDASCII
import com.paylony.topwise.emvreader.DeviceTopUsdkServiceManager
import com.paylony.topwise.emvreader.app.PosApplication
import com.paylony.topwise.emvreader.cache.ConsumeData
import com.paylony.topwise.emvreader.card.CardManager
import com.paylony.topwise.emvreader.util.Format
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad
import com.topwise.cloudpos.aidl.pinpad.GetPinListener

class PinPadActivityDependentClass (private val PinpadActivity: Activity) {

    //private AidlPinpad mPinpadManager;
    private var mPinpad: AidlPinpad? = null

    private var mCardType = ConsumeData.CARD_TYPE_MAG

    private var mPinInput: String? = null

    private var mIntent: Intent? = null

    var mIsCancleInputKey = false

    private var mCardNo: String? = null
    fun initiate(){
        mPinpad = DeviceTopUsdkServiceManager.instance?.getPinpadManager(0)

        mCardType = PosApplication.getApp().mConsumeData?.cardType!!

        mCardNo = PosApplication.getApp().mConsumeData?.cardno
    }
    fun showMessage(message: String) {
        // Use the activity to display a toast message
        PinpadActivity.runOnUiThread {
            Toast.makeText(PinpadActivity, message, Toast.LENGTH_SHORT).show()
        }
    }


    fun showPinpadActivity(cardNo: String, amount: String) {
        object : Thread() {
            override fun run() {
                try {
                    mPinpad!!.setPinKeyboardMode(1)
                    mPinpad!!.getPin(getParam(cardNo, amount), mPinListener)
                } catch (e: RemoteException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private fun getParam(cardNo: String, amount: String): Bundle {
        Log.i("My Tag", "getParam()")
        val param = Bundle()
        param.putInt("wkeyid", 0x01)
        param.putInt("keytype", 0x01)
        param.putInt("key_type", 0x0d)
        param.putByteArray("random", null)
        param.putInt("inputtimes", 1)
        param.putInt("minlength", 4)
        param.putInt("maxlength", 4)
        param.putString("pan", cardNo)
        param.putString("tips", "RMB:$amount")
        param.putBoolean("is_lkl", false)
        return param
    }


    fun doSomethingActivityDependent() {
        // You can perform actions here that depend on the activity
        showMessage("This class depends on the Activity.")
    }

    private val mHandler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
//                    binding.pin.setText(mPinInput)
//                    PinpadActivity.setText(mPinInput)
                }

                else -> {
                }
            }
        }
    }

    private val mPinListener: GetPinListener = object : GetPinListener.Stub() {
        @Throws(RemoteException::class)
        override fun onInputKey(len: Int, msg: String) {
            Log.i(TAG, "onInputKey(), len = $len, msg = $msg")
            mPinInput = msg
            mHandler.sendEmptyMessage(1)
        }

        @Throws(RemoteException::class)
        override fun onError(errorCode: Int) {
            Log.i(TAG, "onError(), errorCode = $errorCode")
            if (ConsumeData.CARD_TYPE_MAG != mCardType) {
                CardManager.instance.setImportPin("")
            } else {
                showResult("Transaction is stoped")
            }
            CardManager.instance.stopCardDealService(PinpadActivity)
            PinpadActivity.finish()
        }

        @Throws(RemoteException::class)
        override fun onConfirmInput(pin: ByteArray?) {
            Log.i(TAG, "onConfirmInput(), pin = " + BCDASCII.bytesToHexString(pin))
            var isOnline = false
            if (mIntent != null) {
                val bundle = mIntent!!.extras
                if (bundle != null) {
                    isOnline = bundle.getBoolean("online")
                }
            }
            Log.d(TAG, "isOnline: $isOnline")
            PosApplication.getApp().mConsumeData?.setPin(pin)
            PosApplication.getApp().mConsumeData?.setPinBlock(
                BCDASCII.bytesToHexString(
                    Format.pinblock(
                        mCardNo,
                        BCDASCII.bytesToHexString(pin)
                    )
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
////                    .startActivity(PinpadActivity, bundle, PacketProcessActivity::class.java)
//                /*byte[] sendData = PosApplication.Companion.getApp().mConsumeData.getICData();
//                Log.d(TAG, BCDASCII.bytesToHexString(sendData));
//                JsonAndHttpsUtils.sendJsonData(mContext, BCDASCII.bytesToHexString(sendData));*/
//            } else {
////                if (ConsumeData.CARD_TYPE_MAG === mCardType) {
////                    //val intent = Intent(PinpadActivity, PacketProcessActivity::class.java)
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
            CardManager.instance.setImportPin(BCDASCII.bytesToHexString(pin))
            PinpadActivity.finish()

        }

        @Throws(RemoteException::class)
        override fun onCancelKeyPress() {
            Log.i(TAG, "onCancelKeyPress()")
            mIsCancleInputKey = true
            if (ConsumeData.CARD_TYPE_MAG != mCardType) {
                CardManager.instance.setImportPin("")
            }
            CardManager.instance.stopCardDealService(PinpadActivity)
            PinpadActivity.finish()
        }

        @Throws(RemoteException::class)
        override fun onStopGetPin() {
            Log.i(TAG, "onStopGetPin()")
            if (ConsumeData.CARD_TYPE_MAG != mCardType) {
                CardManager.instance.setImportPin("")
            }
            CardManager.instance.stopCardDealService(PinpadActivity)
            PinpadActivity.finish()
        }
    }


    private fun showResult(detail: String?) {
//        Log.i(TAG, "showResult(), detail = $detail")
//        val intent = Intent(this, ShowResultActivity::class.java)
//        intent.putExtra(
//            PacketProcessUtils.PACKET_PROCESS_TYPE,
//            PacketProcessUtils.PACKET_PROCESS_CONSUME
//        )
//        intent.putExtra("result_resDetail", detail)
//        startActivity(intent)
//        finish()
    }
}