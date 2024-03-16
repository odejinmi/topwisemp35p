package com.topwise.library

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.paylony.topwise.emvreader.DeviceTopUsdkServiceManager
import com.paylony.topwise.emvreader.printer.PrintTemplate
import com.topwise.cloudpos.aidl.AidlDeviceService
import com.topwise.cloudpos.aidl.card.AidlCheckCard
import com.topwise.cloudpos.aidl.card.AidlCheckCardListener
import com.topwise.cloudpos.aidl.emv.PCardLoadLog
import com.topwise.cloudpos.aidl.emv.PCardTransLog
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad
import com.topwise.cloudpos.aidl.pinpad.GetPinListener
import com.topwise.cloudpos.aidl.printer.AidlPrinter
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener
import com.topwise.cloudpos.aidl.system.AidlSystem
import com.topwise.library.model.CardAccountType
import com.topwise.library.model.DebitCardRequestDto
import com.topwise.library.util.BCDASCII
import com.topwise.library.util.HexUtil
import com.topwise.library.util.TripleDES
import com.topwise.library.util.emv.DeviceState
import com.topwise.library.util.emv.TransactionMonitor
import com.topwise.sdk.emv.EmvDeviceManager
import com.topwise.sdk.emv.EmvManager
import com.topwise.sdk.emv.EmvTransData
import com.topwise.sdk.emv.OnEmvProcessListener
import com.topwisemp35p.topwisemp35p.Topwisemp35pPlugin
import timber.log.Timber
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.internal.*
import kotlin.jvm.internal.Intrinsics.*

public class TopWiseDevice(activity: Context, isPrinter: Boolean? = null, isSystemService: Boolean? = null, callback: (TransactionMonitor) -> Unit) {

    private val callback: (TransactionMonitor) -> Unit

    lateinit var aidlPin: AidlPinpad
    private var activity: Context
    lateinit var aidlCard: AidlCheckCard
    private var transData: EmvTransData? = null
    private val printManager: AidlPrinter? = DeviceTopUsdkServiceManager.instance?.printManager

    private var printerDev: AidlPrinter? = null
    private var SEARCH_CARD_TIME = 100
    private var DIALOG_EXIT_APP = 3000
    var transactionAmount: String = ""
    private var isPrinter: Boolean? = null
    private var isSystemService: Boolean? = null

    private var TOPWISE_SERVICE_ACTION: String? = null
    private var conn: ServiceConnection? = null
    private var mListen: AidlPrinterListener
    private val systemService: AidlSystem? = null
    var generatedPinBlock: String = ""
    private val mWorkKeyIndex = 0
    private var TAG = ""
    var aidlPboc: EmvManager = EmvManager.getInstance()


    companion object {
        
    }

    init {
        checkParameterIsNotNull(activity, "activity")
        checkParameterIsNotNull(callback, "callback")
        this.isPrinter = isPrinter
        this.isSystemService = isSystemService
        this.callback = callback
        this.DIALOG_EXIT_APP = 100
        this.SEARCH_CARD_TIME = 30000
        this.activity = activity

        try {
            var var10000: Boolean? = isPrinter
            if (var10000 == null) {
                var10000 = false
                throwNpe()
            }
            if (!var10000) {
                var10000 = isSystemService
                if (var10000 == null) {
                    var10000 = false
                    throwNpe()
                }
                if (!var10000) {
                    val var10001 = EmvManager.getInstance()
                    checkExpressionValueIsNotNull(var10001, "EmvManager.getInstance()")
                    this.aidlPboc = var10001
                    val var8 = EmvDeviceManager.getInstance()
                    checkExpressionValueIsNotNull(var8, "EmvDeviceManager.getInstance()")
                    val var9: AidlCheckCard = var8.detectCardManager
                    checkExpressionValueIsNotNull(
                        var9,
                        "EmvDeviceManager.getInstance().detectCardManager"
                    )
                    this.aidlCard = var9
                }
            }
        } catch (var6: Exception) {
            callback.invoke(
                TransactionMonitor(
                    DeviceState.FAILED,
                    "device error " + var6.message,
                    true,
                    null as DebitCardRequestDto?
                )
            )
        } catch (var7: IllegalStateException) {
            callback.invoke(
                TransactionMonitor(
                    DeviceState.FAILED,
                    "device error " + var7.message,
                    true,
                    null as DebitCardRequestDto?
                )
            )
        }
        this.TAG = "TPW-TestPrintActivity"
        this.TOPWISE_SERVICE_ACTION = "topwise_cloudpos_device_service"
        this.conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, serviceBinder: IBinder) {
                checkParameterIsNotNull(name, "name")
                checkParameterIsNotNull(serviceBinder, "serviceBinder")
                Log.d("TAG", "aidlService服务连接成功")
                Log.e("SEERE", "DDDDD")
                val serviceManager: AidlDeviceService = AidlDeviceService.Stub.asInterface(serviceBinder)
                try {
                    if (areEqual(this@TopWiseDevice.isPrinter(), true)) {
                        checkExpressionValueIsNotNull(serviceManager, "serviceManager")
                        this@TopWiseDevice.printerDev = AidlPrinter.Stub.asInterface(
                                serviceManager.getPrinter()
                            )
                        this@TopWiseDevice.getCallback().invoke(
                            TransactionMonitor(
                                DeviceState.PRINTER_SERVICE_CONNECTION,
                                "PRINTER_SERVICE_CONNECTION",
                                true,
                                null as DebitCardRequestDto?
                            )
                        )
                    } else {
                        var var10001: TransactionMonitor
                        //                        DeviceState var10003;

                        checkExpressionValueIsNotNull(serviceManager, "serviceManager")
                        //                        var10000.systemService = asInterface(serviceManager.getSystemService());
                        val var6: Function1<*, *> = this@TopWiseDevice.getCallback()
                        //                        TransactionMonitor var10001 = new TransactionMonitor;
                        val var10003: DeviceState = DeviceState.DEVICE_INFO
                        val var10004: AidlSystem = this@TopWiseDevice.systemService!!
                        if (var10004 == null) {
                            throwNpe()
                        }
                        val var7: String = var10004.getSerialNo()
                        checkExpressionValueIsNotNull(var7, "systemService!!.serialNo")
                        //                        var10001.<init>(var10003, var7, true, (DebitCardRequestDto)null);
//                        var6.invoke(var10001);
                    }
                } catch (var5: RemoteException) {
                    var5.printStackTrace()
                }
                Log.e("SEERE", "hghghg")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                checkParameterIsNotNull(name, "name")
                Log.d("TAG", "AidlService服务断开了")
            }
        } as ServiceConnection
        this.mListen = object : AidlPrinterListener.Stub() {
            override fun onError(i: Int) {
                this@TopWiseDevice.showMessage("PRINTER ERROR$i")
                this@TopWiseDevice.getCallback().invoke(
                    TransactionMonitor(
                        DeviceState.PRINTER_ERROR,
                        this@TopWiseDevice.getprintErrorInfo(i),
                        true,
                        null as DebitCardRequestDto?
                    )
                )
            }

            override fun onPrintFinish() {
                val endTime: String = this@TopWiseDevice.getCurTime()
                this@TopWiseDevice.showMessage("END TIME$endTime")
                this@TopWiseDevice.getCallback().invoke(
                    TransactionMonitor(
                        DeviceState.PRINTER_SUCCESS,
                        "PRINTER_SUCCESS",
                        true,
                        null as DebitCardRequestDto?
                    )
                )
            }
        } as AidlPrinterListener
    }

    fun printDoc(template: PrintTemplate) {
        printManager?.addRuiImage(template.printBitmap, 0);
        printManager?.printRuiQueue(object : AidlPrinterListener.Stub() {
            override fun onError(p0: Int) {
//                printListener.onError(p0)
            }

            override fun onPrintFinish() {
//                printListener.onPrintFinish()
            }

        })
    }

//    fun printDoc(template: PrintTemplate) {
//        checkParameterIsNotNull(template, "template")
//        Log.e("PRINTER", "3")
//        try {
////            template.add(TextUnit("\n\n\n\n"))
//            var var10000 = printerDev
//            if (var10000 == null) {
//                throwNpe()
//            }
//            var10000!!.addRuiImage(template.printBitmap, 0)
//            var10000 = printerDev
//            if (var10000 == null) {
//                throwNpe()
//            }
//            var10000!!.printRuiQueue(mListen)
//        } catch (var3: java.lang.Exception) {
//            var3.printStackTrace()
//        }
//    }

    fun getSEARCH_CARD_TIME(): Int {
        return SEARCH_CARD_TIME
    }

    fun isPrinter(): Boolean? {
        return isPrinter
    }

    private fun getCurTime(): String {
        val date = Date(System.currentTimeMillis())
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        val var10000 = format.format(date)
        checkExpressionValueIsNotNull(var10000, "format.format(date)")
        return var10000
    }
    fun getMListen(): AidlPrinterListener {
        return mListen
    }

    fun getprintErrorInfo(errorCode: Int): String {
        val var10000: String
        var10000 = when (errorCode) {
            1 -> "Out of paper, please load paper. error code：$errorCode"
            2 -> "Printing error, high temperature! error code:$errorCode"
            3 -> "Printing error, unknown error! error code:$errorCode"
            4 -> "Printing error, device not open! error code:$errorCode"
            5 -> "Printing error, the device is busy! error code:$errorCode"
            6 -> "Printing error, print bitmap width overflow! error code:$errorCode"
            7 -> "Printing error, print bitmap error! error code:$errorCode"
            8 -> "Printing error, printing barcode error! error code:$errorCode"
            9 -> "Printing error, parameter error! error code:$errorCode"
            10 -> "Print error, print text error! Error code:$errorCode"
            11 -> "Printing error, mac verification error! error code:$errorCode"
            else -> "Printing error, unknown error! error code:$errorCode"
        }
        return var10000
    }

    fun getPrinterDev(): AidlPrinter? {
        return this.printerDev
    }
    fun startEmv(transAmount: String) {
        checkParameterIsNotNull(transAmount, "transAmount")
        var var5 = BigDecimal(transAmount)
        var var10001: String = var5.toString()
        checkExpressionValueIsNotNull(var10001, "(transAmount.toBigDecimal()).toString()")
        this.transactionAmount = var10001
        this.callback.invoke(TransactionMonitor(DeviceState.INSERT_CARD, "INSERT CARD", true, null as DebitCardRequestDto?))
        try  {
            var var10000: EmvDeviceManager? = EmvDeviceManager.getInstance()
            checkExpressionValueIsNotNull(var10000, "EmvDeviceManager.getInstance()")
            var10000!!.getDetectCardManager().checkCard(true, true, true, this.SEARCH_CARD_TIME, (object : AidlCheckCardListener.Stub() {
                @kotlin.Throws(RemoteException::class)
                override fun onFindMagCard(trackData: com.topwise.cloudpos.aidl.magcard.TrackData) {
                    checkParameterIsNotNull(trackData, "trackData")
                    this@TopWiseDevice .showResult("find mag card")
                }
                @kotlin.Throws(RemoteException::class)
                override fun onSwipeCardFail() {}
                @kotlin.Throws(RemoteException::class)
                override fun onFindICCard() {
                    this@TopWiseDevice .setTransData(EmvTransData(2, 0.toByte(), 1, false))
                    this@TopWiseDevice .aidlPboc.startEmvProcess(this@TopWiseDevice .getTransData(), (this@TopWiseDevice.EmvListener()) as OnEmvProcessListener?)
                }
                @kotlin.Throws(RemoteException::class)
                override fun onFindRFCard() {
                    this@TopWiseDevice .setTransData(EmvTransData(4, 0.toByte(), 1, false))
                    this@TopWiseDevice .aidlPboc.startEmvProcess(this@TopWiseDevice .getTransData(), (this@TopWiseDevice.EmvListener()) as OnEmvProcessListener?)
                }
                @kotlin.Throws(RemoteException::class)
                override fun onTimeout() {
                    this@TopWiseDevice .showResult("Time Out")
                    this@TopWiseDevice .getCallback().invoke(TransactionMonitor(DeviceState.READCARD_FAILED, "TIME OUT", true, null as DebitCardRequestDto?))
                }
                @kotlin.Throws(RemoteException::class)
                override fun onCanceled() {
                    this@TopWiseDevice .showResult("User cancel search card")
                    this@TopWiseDevice .getCallback().invoke(TransactionMonitor(DeviceState.READCARD_FAILED, "CANCELLED", true, null as DebitCardRequestDto?))
                }
                @kotlin.Throws(RemoteException::class)
                override fun onError(i: Int) {
                    this@TopWiseDevice .aidlCard.cancelCheckCard()
                    this@TopWiseDevice .getCallback().invoke(TransactionMonitor(DeviceState.READCARD_FAILED, "check card error " + i, true, null as DebitCardRequestDto?))
                    this@TopWiseDevice .showResult("check card error " + i)
                }
            }) as AidlCheckCardListener?)
        }catch (var6: RemoteException) {
            var6.printStackTrace()
            this.callback.invoke(TransactionMonitor(DeviceState.READCARD_FAILED, "check card error " + var6.message, true, null as DebitCardRequestDto?))
        }
    }


    private fun showResult(tip: String) {
        Log.e("MainPageActivity.TAG", tip)
        Timber.e(tip, *arrayOfNulls<Any>(0))
    }

    fun getTransData(): EmvTransData? {
        return transData
    }

    fun setTransData(var1: EmvTransData?) {
        transData = var1
    }
    @JvmName("getAidlCardInternal")
    fun getAidlCard(): AidlCheckCard {
        val var10000 = aidlCard
        if (var10000 == null) {
            throwUninitializedPropertyAccessException("aidlCard")
        }
        return var10000
    }
    @JvmName("setAidlCardInternal")
    fun setAidlCard(var1: AidlCheckCard) {
        checkParameterIsNotNull(var1, "<set-?>")
        aidlCard = var1
    }

    fun getActivity(): Context {
        return this.activity
    }

    fun setActivity(var1: Context) {
        checkParameterIsNotNull(var1, "<set-?>")
        this.activity = var1
    }
    fun getCallback(): (TransactionMonitor) -> Unit {
        return this.callback
    }
    @JvmName("getTransactionAmountInternal")
    fun getTransactionAmount(): String {
        val var10000 = transactionAmount
        if (var10000 == null) {
            throwUninitializedPropertyAccessException("transactionAmount")
        }
        return var10000
    }
    @JvmName("setTransactionAmountInternal")
    fun setTransactionAmount(var1: String) {
        checkParameterIsNotNull(var1, "<set-?>")
        transactionAmount = var1
    }
    @JvmName("getAidlPbocInternal")
    fun getAidlPboc(): EmvManager {
        val var10000 = aidlPboc
        if (var10000 == null) {
            throwUninitializedPropertyAccessException("aidlPboc")
        }
        return var10000
    }
    @JvmName("setAidlPbocInternal")
    fun setAidlPboc(var1: EmvManager) {
        checkParameterIsNotNull(var1, "<set-?>")
        aidlPboc = var1
    }
    fun getPan(track2Data: String?): String {
        // Early return for null input:
        if (track2Data == null) {
            return ""  // Return an empty string instead of throwing an exception
        }

        // Simplify indexOf call and handle potential -1 return:
        val indexOfToken = track2Data.indexOf("D", 0, false)
        if (indexOfToken == -1) {
            return ""  // Return an empty string if "D" is not found
        }

        // Use direct substring call and avoid unnecessary variables:
        val pan = track2Data.substring(0, indexOfToken)

        // No need for checkExpressionValueIsNotNull if substring is not null:
        return pan
    }
    private fun getPinParam(pinType: Byte): Bundle {
        val bundle = Bundle()
        bundle.putInt("wkeyid", this.mWorkKeyIndex)
        bundle.putInt("keytype", pinType.toInt())
        bundle.putInt("inputtimes", 1)
        bundle.putInt("minlength", 4)
        bundle.putInt("maxlength", 12)
        bundle.putString("pan", this.getPan(this.getTrack2()))
        bundle.putString("tips", "RMB:2000.00")
        bundle.putString("input_pin_mode", "0,4,5,6")
        return bundle
    }

    private fun getTrack2(): String {
        Log.i("MainPageActivity.TAG", "getTrack2()")
        val track2Tag = arrayOf("57")
        val track2TlvList: ByteArray = this.getTlv(track2Tag)
        val temp = ByteArray(track2TlvList.size - 2)
        System.arraycopy(track2TlvList, 2, temp, 0, temp.size)
        val var10001 = BCDASCII.bytesToHexString(temp)
        checkExpressionValueIsNotNull(var10001, "BCDASCII.bytesToHexString(temp)")
        return processTrack2(var10001)
    }

    private fun processTrack2(track: String?): String {
        // Early return for null input:
        if (track == null) {
            return ""  // Return an empty string instead of throwing an exception
        }

        val builder = StringBuilder()
        val length = track.length

        for (i in 0 until length) {
            val subStr = track.substring(i, i + 1)

            // Append if not ending with "F":
            if (!subStr.endsWith("F")) {
                builder.append(subStr)
            }
        }

        return builder.toString()
    }

    private fun getTlv(tags: Array<String>): ByteArray {
        val sb = java.lang.StringBuilder()
        val var6 = tags.size
        for (var4 in 0 until var6) {
            val tag = tags[var4]
            val var10000 = aidlPboc
            if (var10000 == null) {
                throwUninitializedPropertyAccessException("aidlPboc")
            }
            val tempByte = var10000!!.getTlv(tag)
            val strResult = BCDASCII.bytesToHexString(tempByte)
            sb.append(strResult)
        }
        val var9 = BCDASCII.hexStringToBytes(sb.toString())
        checkExpressionValueIsNotNull(var9, "BCDASCII.hexStringToBytes(sb.toString())")
        return var9
    }
    @JvmName("getAidlPinInternal")
    fun getAidlPin(): AidlPinpad {
        val var10000 = aidlPin
        if (var10000 == null) {
            throwUninitializedPropertyAccessException("aidlPin")
        }
        return var10000
    }
    @JvmName("setAidlPinInternal")
    fun setAidlPin(var1: AidlPinpad) {
        checkParameterIsNotNull(var1, "<set-?>")
        this.aidlPin = var1
    }
    @JvmName("getGeneratedPinBlockInternal")
    fun getGeneratedPinBlock(): String {
        val var10000 = generatedPinBlock
        if (var10000 == null) {
            throwUninitializedPropertyAccessException("generatedPinBlock")
        }
        return var10000
    }
    @JvmName("setGeneratedPinBlockInternal")
    fun setGeneratedPinBlock(var1: String) {
        checkParameterIsNotNull(var1, "<set-?>")
        generatedPinBlock = var1
    }
    fun getExpiryDate(track2Data: String?): String {
        // Early return for null input:
        if (track2Data == null) {
            return ""  // Return an empty string instead of throwing an exception
        }

        // Find the index of the "D" token, handling potential -1 return:
        val indexOfToken = track2Data.indexOf("D", 0, false)
        if (indexOfToken == -1) {
            return ""  // Return an empty string if "D" is not found
        }

        // Extract the expiry date substring directly:
        val expiryDate = track2Data.substring(indexOfToken + 1, indexOfToken + 5)

        // No need for checkExpressionValueIsNotNull if substring is not null:
        return expiryDate
    }

    fun getServiceCode(track2Data: String): String {
        // Early return for empty input:
        if (track2Data.isEmpty()) {
            return ""  // Return an empty string if input is empty
        }

        // Find the index of the "D" token, handling potential -1 return:
        val indexOfToken = track2Data.indexOf("D", 0, false)
        if (indexOfToken == -1) {
            return ""  // Return an empty string if "D" is not found
        }

        // Extract the service code substring directly, ensuring bounds:
        val endIndex = minOf(indexOfToken + 8, track2Data.length)  // Ensure endIndex is within bounds
        val serviceCode = track2Data.substring(indexOfToken + 5, endIndex)

        return serviceCode
    }

    fun getAcquiringInstitutionIdCode(track2Data: String?): String {
        if (track2Data == null) {
            throwNpe()
        }
        val var3: Byte = 0
        val var4: Byte = 6
        val var5 = false
        return if (track2Data == null) {
            throw TypeCastException("null cannot be cast to non-null type java.lang.String")
        } else {
            val var10000 = track2Data.substring(var3.toInt(), var4.toInt())
            checkExpressionValueIsNotNull(
                var10000,
                "(this as java.lang.Strin…ing(startIndex, endIndex)"
            )
            var10000
        }
    }

    private fun closeLed() {
        val var10000 = DeviceManager.getInstance()
        checkExpressionValueIsNotNull(var10000, "DeviceManager.getInstance()")
        val mAidlLed = var10000.ledManager
        try {
            mAidlLed?.setLed(0, false)
        } catch (var3: RemoteException) {
            var3.printStackTrace()
        }
    }
    private fun getConsume55(): String {
        Log.i("MainPageActivity.TAG", "getConsume55()")
        val consume55Tag = arrayOf(
            "9F26",
            "9F27",
            "9F10",
            "9F37",
            "9F36",
            "95",
            "9A",
            "9C",
            "9F02",
            "5F2A",
            "5F34",
            "82",
            "9F1A",
            "9F03",
            "9F33",
            "84",
            "9F34",
            "9F35",
            "9F41"
        )
        val consume55TlvList = getTlv(consume55Tag)
        val filed55 = BCDASCII.bytesToHexString(consume55TlvList)
        Log.d("MainPageActivity.TAG", "setConsume55 consume55TlvList : $filed55")
        checkExpressionValueIsNotNull(filed55, "filed55")
        return filed55
    }
    private fun getSeqNum(): String {
        Log.i("MainPageActivity.TAG", "getSeqNum()")
        val seqNumTag = arrayOf("5F34")
        val seqNumTlvList = getTlv(seqNumTag)
        var cardSeqNum: String = ""
        if (seqNumTlvList != null) {
            var var10000 = BCDASCII.bytesToHexString(seqNumTlvList)
            checkExpressionValueIsNotNull(var10000, "BCDASCII.bytesToHexString(seqNumTlvList)")
            cardSeqNum = var10000
            val var5 = cardSeqNum.length - 2
            val var6 = cardSeqNum.length
            val var7 = false
            if (cardSeqNum == null) {
                throw TypeCastException("null cannot be cast to non-null type java.lang.String")
            }
            var10000 = cardSeqNum.substring(var5, var6)
            checkExpressionValueIsNotNull(
                var10000,
                "(this as java.lang.Strin…ing(startIndex, endIndex)"
            )
            cardSeqNum = var10000
        }
        Log.d("MainPageActivity.TAG", "setSeqNum : $cardSeqNum")
        return cardSeqNum
    }
    inner class EmvListener : OnEmvProcessListener {
        @Throws(RemoteException::class)
        override fun requestImportAmount(arg0: Int) {
            Timber.e("requestImportAmount", *arrayOfNulls<Any>(0))
            this@TopWiseDevice.getAidlPboc().importAmount(this@TopWiseDevice.getTransactionAmount())
        }

        @Throws(RemoteException::class)
        override fun requestAidSelect(times: Int, arg1: Array<String>) {
            checkParameterIsNotNull(arg1, "arg1")
            this@TopWiseDevice.showResult("please choice application")
            var str = ""
            var i = 0
            val var5 = arg1.size
            while (i < var5) {
                str = str + arg1[i]
                ++i
            }
//            this@TopWiseDevice.getAidlPboc().importAidSelectRes(1)
        }

        @Throws(RemoteException::class)
        override fun finalAidSelect() {
            this@TopWiseDevice.showResult("finalAidSelect")
            this@TopWiseDevice.getAidlPboc().setTlv("9F1A", BCDASCII.hexStringToBytes("0566"))
            this@TopWiseDevice.getAidlPboc().setTlv("5F2A", BCDASCII.hexStringToBytes("0566"))
            this@TopWiseDevice.getAidlPboc().setTlv("9f3c", BCDASCII.hexStringToBytes("0566"))
            this@TopWiseDevice.getAidlPboc().importFinalAidSelectRes(true)
        }

        @Throws(RemoteException::class)
        override fun onConfirmCardInfo(cardNo: String) {
            checkParameterIsNotNull(cardNo, "cardNo")
            Log.v("MainPageActivity.TAG", "onConfirmCardInfo")
            this@TopWiseDevice.showResult("Card No: $cardNo")
            this@TopWiseDevice.showResult("Confirm")
            this@TopWiseDevice.getAidlPboc().importConfirmCardInfoRes(true)
        }

        @Throws(RemoteException::class)
        override fun requestImportPin(
            type: Int,
            lasttimeFlag: Boolean,
            amt: String,
            pinRetryTimes: Int
        ) {
        }

        @Throws(RemoteException::class)
        fun requestImportPin(type: Int, lastFlag: Boolean, amount: String) {
            checkParameterIsNotNull(amount, "amount")
            Log.v("MainPageActivity.TAG", "requestImportPin")
            this@TopWiseDevice.showResult("please input Pin")
            this@TopWiseDevice.getCallback().invoke(
                TransactionMonitor(
                    DeviceState.INPUT_PIN,
                    "Input PIN",
                    true,
                    null as DebitCardRequestDto?
                )
            )
            val pinType = (if (type == 3) 0 else 1).toByte()
            val bundle: Bundle = this@TopWiseDevice.getPinParam(pinType)
            val var10000: TopWiseDevice = this@TopWiseDevice
            val var10001 = DeviceManager.getInstance()
            checkExpressionValueIsNotNull(var10001, "DeviceManager.getInstance()")
            val var7 = var10001.pinpadManager
            checkExpressionValueIsNotNull(var7, "DeviceManager.getInstance().pinpadManager")
            var10000.setAidlPin(var7)
            this@TopWiseDevice.getAidlPin()
                .getPin(bundle, this@TopWiseDevice.MyGetPinListener() as GetPinListener)
        }


        @Throws(RemoteException::class)
        override fun onRequestOnline() {
            showResult("Request online")
            val seqNum = getSeqNum()
            showResult("seqNum $seqNum")
            val track2 = getTrack2()
            showResult("track2 $track2")
            val filed55Data = getConsume55()
            showResult("filed55Data $filed55Data")
            Date()
            SimpleDateFormat("MMddkkmmss", Locale.getDefault())
            SimpleDateFormat("kkmmss", Locale.getDefault())
            val stan = System.currentTimeMillis().toString().takeLast(6)
            val var9 = System.currentTimeMillis().toString()
            val var10 = false
            if (var9 == null) {
                throw TypeCastException("null cannot be cast to non-null type kotlin.CharSequence")
            } else {
                val rrn = (var9 as CharSequence).reversed().toString().take(12)
                val panSequenceNumber: Ref.ObjectRef<*> =
                    Ref.ObjectRef<Any?>()
                var var10001 = seqNum
                if (seqNum == null) {
                    var10001 = "001"
                }
                panSequenceNumber.element = var10001
                 val getTrack2PanSequenceNumber1 = ""

                Log.e("MainPageActivity.TAG", "pinblock1")
                val decryptedStringPinBlock = TripleDES.threeDesDecrypt(
                    getGeneratedPinBlock(),
                    "D55DEAF4850BA81610FB1A9101F8CE67"
                )
                Log.e("MainPageActivity.TAG", "pinblock2")
                val cardPan = getPan(track2)
                if (cardPan.length >= 4) {
                    val var16 = cardPan.length - 4
                    val var17 = false
                    if (cardPan == null) {
                        throw TypeCastException("null cannot be cast to non-null type java.lang.String")
                    }
                    checkExpressionValueIsNotNull(
                        cardPan.substring(var16),
                        "(this as java.lang.String).substring(startIndex)"
                    )
                } else {
                    val var66 = ""
                }
                val var68: (TransactionMonitor) -> Unit = getCallback()
                val var69 = DeviceState.INFO
                val var10011 = getExpiryDate(track2)
//                val var10013: String = getTrack2PanSequenceNumber1().invoke()
                val var10013: String = " "
                val var10014 = getServiceCode(track2)
                val var10019 = getAcquiringInstitutionIdCode(track2)
                val var15 = getTransactionAmount()
                val var40 = "CUSTOMER"
                val var39 = "N/A"
                val var38 = "N/A"
                val var37 = "566"
                val var34 = "N/A"
                val var32 = "510101511344101"
                val var23 = true
                val var22 = "Card read successfully"
                val var67 = false
                val var42 = BigDecimal(var15)
                val var43 = CardAccountType.SAVINGS
                val var61 = DebitCardRequestDto(
                    cardPan,
                    stan,
                    rrn,
                    decryptedStringPinBlock,
                    filed55Data,
                    track2,
                    var32,
                    var10011,
                    var34,
                    var10013,
                    var10014,
                    var37,
                    var38,
                    var39,
                    var40,
                    var10019,
                    var42,
                    var43
                )
                var68.invoke(TransactionMonitor(var69, var22, var23, var61))
                getAidlPboc().importOnlineResp(true, "00", null as String?)
            }
        }

        @Throws(RemoteException::class)
        override fun onTransResult(arg0: Int) {
            Log.e("MainPageActivity.TAG", "onTransResult(+$arg0)")
            val var10000 = DeviceManager.getInstance()
            checkExpressionValueIsNotNull(var10000, "DeviceManager.getInstance()")
            var10000.rfCard.close()
            this@TopWiseDevice.closeLed()
            when (arg0) {
                1 -> this@TopWiseDevice.showResult("Allow trading")
                2 -> this@TopWiseDevice.showResult("Refuse to deal")
                3 -> {
                    this@TopWiseDevice.getCallback().invoke(
                        TransactionMonitor(
                            DeviceState.READCARD_FAILED,
                            "Card reading failed, try again.",
                            true,
                            null as DebitCardRequestDto?
                        )
                    )
                    this@TopWiseDevice.showResult("Stop trading")
                }

                4 -> this@TopWiseDevice.showResult("Downgrade")
                5, 6 -> {
                    this@TopWiseDevice.getCallback().invoke(
                        TransactionMonitor(
                            DeviceState.READCARD_FAILED,
                            "Unknown exception, try again.",
                            true,
                            null as DebitCardRequestDto?
                        )
                    )
                    this@TopWiseDevice.showResult("Unknown exception")
                }

                else -> {
                    this@TopWiseDevice.getCallback().invoke(
                        TransactionMonitor(
                            DeviceState.READCARD_FAILED,
                            "Unknown exception, try again.",
                            true,
                            null as DebitCardRequestDto?
                        )
                    )
                    this@TopWiseDevice.showResult("Unknown exception")
                }
            }
        }

        @Throws(RemoteException::class)
        override fun requestUserAuth(certType: Int, certno: String) {
            checkParameterIsNotNull(certno, "certno")
            this@TopWiseDevice.showResult("requestUserAuth")
            this@TopWiseDevice.getAidlPboc().importUserAuthRes(true)
        }

        @Throws(RemoteException::class)
        override fun requestTipsConfirm(arg0: String) {
            checkParameterIsNotNull(arg0, "arg0")
            this@TopWiseDevice.showResult("requestTipsConfirm")
            this@TopWiseDevice.getAidlPboc().importMsgConfirmRes(true)
        }

        @Throws(RemoteException::class)
        override fun requestEcashTipsConfirm() {
            this@TopWiseDevice.showResult("requestEcashTipsConfirm")
            this@TopWiseDevice.getAidlPboc().importECashTipConfirmRes(false)
        }

        @Throws(RemoteException::class)
        override fun onReadCardTransLog(arg0: Array<PCardTransLog>) {
            checkParameterIsNotNull(arg0, "arg0")
        }

        @Throws(RemoteException::class)
        override fun onReadCardOffLineBalance(
            arg0: String,
            arg1: String,
            arg2: String,
            arg3: String
        ) {
            checkParameterIsNotNull(arg0, "arg0")
            checkParameterIsNotNull(arg1, "arg1")
            checkParameterIsNotNull(arg2, "arg2")
            checkParameterIsNotNull(arg3, "arg3")
        }

        @Throws(RemoteException::class)
        override fun onReadCardLoadLog(arg0: String, arg1: String, arg2: Array<PCardLoadLog>) {
            checkParameterIsNotNull(arg0, "arg0")
            checkParameterIsNotNull(arg1, "arg1")
            checkParameterIsNotNull(arg2, "arg2")
        }

        @Throws(RemoteException::class)
        override fun onError(arg0: Int) {
            this@TopWiseDevice.showResult("onError $arg0")
            Log.v("MainPageActivity.TAG", "onError $arg0")
            this@TopWiseDevice.getAidlPboc().endEmv()
            if (this@TopWiseDevice.getAidlCard() != null) {
                this@TopWiseDevice.getAidlCard().cancelCheckCard()
            }
        }
    }

    private fun showMessage(tip: String) {
        Timber.e(tip, *arrayOfNulls<Any>(0))
    }
    inner class MyGetPinListener : GetPinListener.Stub() {
        @Throws(RemoteException::class)
        override fun onStopGetPin() {
            Timber.e("onStopGetPin ", *arrayOfNulls<Any>(0))
        }

        @Throws(RemoteException::class)
        override fun onInputKey(len: Int, arg1: String) {
            checkParameterIsNotNull(arg1, "arg1")
            Log.v("MainPageActivity.TAG", "onInputKey len $len arg1 $arg1")
            Timber.e("input pin $arg1", *arrayOfNulls<Any>(0))
            this@TopWiseDevice.getCallback().invoke(
                TransactionMonitor(
                    DeviceState.PIN_DATA,
                    arg1, true, null as DebitCardRequestDto?
                )
            )
        }

        @Throws(RemoteException::class)
        override fun onError(errorCode: Int) {
            Log.v("MainPageActivity.TAG", "onError $errorCode")
            Timber.e("input error code $errorCode", *arrayOfNulls<Any>(0))
        }

        @Throws(RemoteException::class)
        override fun onConfirmInput(arg0: ByteArray) {
            checkParameterIsNotNull(arg0, "arg0")
            Log.v("MainPageActivity.TAG", "input success")
            Log.e("ADFASDFSADF", HexUtil.bcd2str(arg0) + " =====>")
            Timber.e("get Pin " + HexUtil.bcd2str(arg0), *arrayOfNulls<Any>(0))
            val var10000: TopWiseDevice = this@TopWiseDevice
            val var10001 = HexUtil.bcd2str(arg0)
            checkExpressionValueIsNotNull(var10001, "HexUtil.bcd2str(arg0)")
            var10000.setGeneratedPinBlock(var10001)
            this@TopWiseDevice.getAidlPboc().importPin(HexUtil.bcd2str(arg0))
        }

        @Throws(RemoteException::class)
        override fun onCancelKeyPress() {
            Log.v("MainPageActivity.TAG", "onCancelKeyPress ")
            Timber.e("onCancelKeyPress ", *arrayOfNulls<Any>(0))
        }
    }

}