package com.lonytech.topwisesdk.emvreader

import android.content.Context
import android.graphics.Bitmap
import android.os.RemoteException
import android.util.Log
import com.lonytech.topwisesdk.charackterEncoder.BCDASCII
import com.lonytech.topwisesdk.emvreader.app.PosApplication
import com.lonytech.topwisesdk.emvreader.card.CardManager
import com.lonytech.topwisesdk.emvreader.emv.CardReadChannel
import com.lonytech.topwisesdk.emvreader.emv.CardReadResult
import com.lonytech.topwisesdk.emvreader.emv.CardReadState
import com.lonytech.topwisesdk.emvreader.emv.Processor
import com.lonytech.topwisesdk.emvreader.emv.TransactionMonitor
import com.topwise.cloudpos.aidl.card.AidlCheckCardListener
import com.topwise.cloudpos.aidl.magcard.TrackData
import com.topwise.cloudpos.aidl.printer.AidlPrinter
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener
import com.lonytech.topwisesdk.emvreader.printer.PrintTemplate
import com.lonytech.topwisesdk.emvreader.util.Format
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TopWiseDevice(val context: Context, val terminal: Terminal, callback: (TransactionMonitor) -> Unit) {
    private val callback: (TransactionMonitor) -> Unit
    init {
        this.callback = callback
    }

    private val printManager: AidlPrinter? = DeviceTopUsdkServiceManager.instance?.printManager

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

    fun printDoc(template: Bitmap) {
        printManager?.addRuiImage(template, 0);
        printManager?.printRuiQueue(object : AidlPrinterListener.Stub() {
            override fun onError(p0: Int) {
//                printListener.onError(p0)
            }

            override fun onPrintFinish() {
//                printListener.onPrintFinish()
            }

        })
    }


    fun enterpin(directpin: String) {

// Convert the string to a ByteArray
        val pin: ByteArray = BCDASCII.hexStringToBytes(directpin)
//        val pin = call.argument<String>("pin")!!
        Log.i("TAG", "onConfirmInput(), pin = " + BCDASCII.bytesToHexString(pin))
        var mCardNo = PosApplication.getApp().mConsumeData?.cardno
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

    val serialnumber: String
        get() = DeviceTopUsdkServiceManager.instance?.systemManager?.serialNo!!


    private val mCheckCard by lazy { DeviceTopUsdkServiceManager.instance?.getCheckCard() }
    val searchCardTime: Int
        get() = 30000
    val job = Job()
    private val coroutineContext = job + Dispatchers.Main
    private val mainScope = MainScope()
    fun closeCardReader() {
        cancelCheckCard()
        CardManager.instance.stopCardDealService(context)
    }
     fun readCard(amount: String, transactiontype: String, accDetails: String) {
        CardManager.instance.stopCardDealService(context)
        PosApplication.getApp().mConsumeData?.amount = amount
        PosApplication.getApp().mConsumeData?.transactionType = transactiontype
        PosApplication.getApp().mConsumeData?.accDetails = accDetails
        PosApplication.getApp().transactionType = PosApplication.CONSUME
        PosApplication.getApp().processor = Processor.INTERSWITCH
        read()
//        return cardConsumeEmitter
    }

    fun getCardScheme(amount: String) {
        CardManager.instance.stopCardDealService(context)
        PosApplication.getApp().transactionType = PosApplication.CARD_SCHEME
        PosApplication.getApp().mConsumeData?.amount = amount
        PosApplication.getApp().processor = Processor.INTERSWITCH
        getScheme()
    }
    private val SEARCH_CARD_TIME: Int = 30000

    private fun read() {
//        this.callback.invoke(TransactionMonitor(
//                CardReadState.Loading,
//                "device error ",
//                true,
//                null as CardReadResult?
//        ))
//        cardConsumeEmitter.emit(CardReadState.Loading)
        CardManager.instance.initCardExceptionCallBack(object : CardManager.CardExceptionCallBack {
            override fun callBackTimeOut() {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CardReadTimeOut,
                            "card time out ",
                            true,
                            null as CardReadResult?
                    )
                    )
            }

            override fun callBackError(errorCode: Int) {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CallBackError,
                            "card time out $errorCode",
                            true,
                            null as CardReadResult?
                    )
                    )
            }

            override fun callBackCanceled() {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CallBackCanceled,
                            "card canceled ",
                            true,
                            null as CardReadResult?
                    )
                    )
            }

            override fun callBackTransResult(result: Int) {

            }

            override fun finishPreActivity() {

            }
        })

        CardManager.instance.setCardFoundListener(object : CardManager.Card {
            override fun searching() {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.Loading,
                        "card time out",
                        true,
                        null as CardReadResult?
                )
                )
            }

            override fun onCardDetected(pan: String) {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CardDetected,
                            pan,
                            true,
                            null as CardReadResult?
                    )
                    )
            }

            override fun onCardReadResult(cardReadResult: CardReadResult) {
                    val shola =  CardReadResult(
                            issuerApplicationData = cardReadResult.issuerApplicationData,
                            applicationVersionNumber = cardReadResult.applicationVersionNumber,
                            transactionType = cardReadResult.transactionType,
                            amount = cardReadResult.amount,
                            amountAuthorized = cardReadResult.amountAuthorized,
                            cardSeqenceNumber = cardReadResult.cardSeqenceNumber,
                            unifiedPaymentIccData = cardReadResult.unifiedPaymentIccData,
                            deviceSerialNumber = cardReadResult.deviceSerialNumber,
                            iccDataString = cardReadResult.iccDataString,
                            authorizationResponseCode = cardReadResult.authorizationResponseCode,
                            nibssIccSubset = cardReadResult.nibssIccSubset,
                            applicationTransactionCounter = cardReadResult.applicationTransactionCounter,
                            terminalVerificationResults = cardReadResult.terminalVerificationResults,
                            expirationDate = cardReadResult.expirationDate,
                            applicationPrimaryAccountNumber = cardReadResult.applicationPrimaryAccountNumber,
                            applicationPANSequenceNumber = cardReadResult.applicationPANSequenceNumber,
                            transactionDate = cardReadResult.transactionDate,
                            cryptogramInformationData = cardReadResult.cryptogramInformationData,
                            dedicatedFileName = cardReadResult.dedicatedFileName,
                            transactionSequenceNumber = cardReadResult.transactionSequenceNumber,
                            transactionSequenceCounter = cardReadResult.transactionSequenceCounter,
                            cryptogram = cardReadResult.cryptogram,
                            track2Data = cardReadResult.track2Data,
                            cardholderVerificationMethod = cardReadResult.cardholderVerificationMethod,
                            applicationInterchangeProfile = cardReadResult.applicationInterchangeProfile,
                            pinBlockDUKPT = cardReadResult.pinBlockDUKPT,
                            pinBlockTrippleDES = cardReadResult.pinBlockTrippleDES,
                            cardScheme = cardReadResult.cardScheme,
                            applicationDiscretionaryData = cardReadResult.applicationDiscretionaryData,
                            unpredictableNumber = cardReadResult.unpredictableNumber,
                            interfaceDeviceSerialNumber = cardReadResult.interfaceDeviceSerialNumber,
                            encryptedPinBlock = cardReadResult.encryptedPinBlock,
                            terminalType = cardReadResult.terminalType,
                            cardHolderName = cardReadResult.cardHolderName,
                            originalDeviceSerial = cardReadResult.originalDeviceSerial,
                            transactionCurrencyCode = cardReadResult.transactionCurrencyCode,
                            terminalCountryCode = cardReadResult.terminalCountryCode,
                            cashBackAmount = cardReadResult.cashBackAmount,
                            terminalCapabilities = cardReadResult.terminalCapabilities,
                            plainPinKey = cardReadResult.plainPinKey,
                            originalPan = cardReadResult.originalPan,
                    )
//                    callback.invoke(
//                        TransactionMonitor(
//                            CardReadState.CardData,
//                            "card time out",
//                            true,
//                            shola
//                        )
//                    )
                val data = mapOf(
                    "accountType" to cardReadResult.amount,
                    "amount" to PosApplication.getApp().mConsumeData?.amount, // typed amount
                    "fee" to fees1,
                    "totalAmount" to ((PosApplication.getApp().mConsumeData?.amount?.toIntOrNull()
                        ?: 0) + fees.toInt()).toString(), // total and final amount to read with card
                    "type" to PosApplication.getApp().mConsumeData?.transactionType,
                    "acc_details" to PosApplication.getApp().mConsumeData?.accDetails,
                    "amountAuthorized" to cardReadResult.amountAuthorized,
                    "applicationDiscretionaryData" to cardReadResult.applicationDiscretionaryData,
                    "applicationInterchangeProfile" to cardReadResult.applicationInterchangeProfile,
                    "applicationIssuerData" to cardReadResult.applicationIssuerData,
                    "applicationPANSequenceNumber" to cardReadResult.applicationPANSequenceNumber,
                    "applicationPrimaryAccountNumber" to cardReadResult.applicationPrimaryAccountNumber,
                    "applicationTransactionCounter" to cardReadResult.applicationTransactionCounter,
                    "applicationVersionNumber" to cardReadResult.applicationVersionNumber,
                    "authorizationResponseCode" to cardReadResult.authorizationResponseCode,
                    "cardHolderName" to cardReadResult.cardHolderName,
                    "cardScheme" to cardReadResult.cardScheme,
                    "cardSeqenceNumber" to cardReadResult.cardSeqenceNumber,
                    "cardholderVerificationMethod" to cardReadResult.cardholderVerificationMethod,
                    "cashBackAmount" to cardReadResult.cashBackAmount,
                    "cryptogram" to cardReadResult.cryptogram,
                    "cryptogramInformationData" to cardReadResult.cryptogramInformationData,
                    "dedicatedFileName" to cardReadResult.dedicatedFileName,
                    "deviceSerialNumber" to serialnumber,
                    "encryptedPinBlock" to cardReadResult.encryptedPinBlock,
                    "expirationDate" to cardReadResult.expirationDate,
                    "iccDataString" to cardReadResult.iccDataString,
                    "interfaceDeviceSerialNumber" to cardReadResult.interfaceDeviceSerialNumber,
                    "issuerApplicationData" to cardReadResult.issuerApplicationData,
                    "nibssIccSubset" to cardReadResult.nibssIccSubset,
                    "originalDeviceSerial" to cardReadResult.originalDeviceSerial,
                    "originalPan" to cardReadResult.originalPan,
                    "pinBlock" to cardReadResult.pinBlock,
                    "pinBlockDUKPT" to cardReadResult.pinBlockDUKPT,
                    "pinBlockTrippleDES" to cardReadResult.pinBlockTrippleDES,
                    "plainPinKey" to cardReadResult.plainPinKey,
                    "terminalCapabilities" to cardReadResult.terminalCapabilities,
                    "terminalCountryCode" to cardReadResult.terminalCountryCode,
                    "terminalId" to  terminal.terminalId,
                    "terminalType" to cardReadResult.terminalType,
                    "terminalVerificationResults" to cardReadResult.terminalVerificationResults,
                    "track2Data" to cardReadResult.track2Data,
                    "transactionCurrencyCode" to cardReadResult.transactionCurrencyCode,
                    "transactionDate" to cardReadResult.transactionDate,
                    "transactionSequenceCounter" to cardReadResult.transactionSequenceCounter,
                    "transactionSequenceNumber" to cardReadResult.transactionSequenceNumber,
                    "transactionType" to cardReadResult.transactionType,
                    "unifiedPaymentIccData" to cardReadResult.unifiedPaymentIccData,
                    "unpredictableNumber" to cardReadResult.unpredictableNumber
                )
                CoroutineScope(IO).launch {
                    val response = ApiClient.post(
                        endpoint = "payment/card/debit",
                        token = terminal.token.toString(),
                        context = context,
                        data = data
                    )
                    Log.d("Response", response.toString())
                }
            }
        })
        CardManager.instance.startCardDealService(context)
    }

    private fun getScheme() {
        this.callback.invoke(
            TransactionMonitor(
                CardReadState.Loading,
                "device error ",
                true,
                null as CardReadResult?
        )
        )
        CardManager.instance.initCardExceptionCallBack(object : CardManager.CardExceptionCallBack {
            override fun callBackTimeOut() {
                mainScope.launch {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CardReadTimeOut,
                            "card time out ",
                            true,
                            null as CardReadResult?
                    )
                    )
                }
            }

            override fun callBackError(errorCode: Int) {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.CallBackError,
                        "card time out $errorCode",
                        true,
                        null as CardReadResult?
                )
                )
            }

            override fun callBackCanceled() {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.CallBackCanceled,
                        "card canceled",
                        true,
                        null as CardReadResult?
                )
                )
            }

            override fun callBackTransResult(result: Int) {

            }

            override fun finishPreActivity() {

            }
        })
        CardManager.instance.setCardFoundListener(object : CardManager.Card {
            override fun searching() {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.Loading,
                        "Loading ",
                        true,
                        null as CardReadResult?
                )
                )
            }

            override fun onCardDetected(pan: String) {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.CardDetected,
                        "card detected",
                        true,
                        null as CardReadResult?
                )
                )
            }

            override fun onCardReadResult(cardReadResult: CardReadResult) {
                val shola =  CardReadResult(
                        issuerApplicationData = cardReadResult.issuerApplicationData,
                        applicationVersionNumber = cardReadResult.applicationVersionNumber,
                        transactionType = cardReadResult.transactionType,
                        amount = cardReadResult.amount,
                        amountAuthorized = cardReadResult.amountAuthorized,
                        cardSeqenceNumber = cardReadResult.cardSeqenceNumber,
                        unifiedPaymentIccData = cardReadResult.unifiedPaymentIccData,
                        deviceSerialNumber = cardReadResult.deviceSerialNumber,
                        iccDataString = cardReadResult.iccDataString,
                        authorizationResponseCode = cardReadResult.authorizationResponseCode,
                        nibssIccSubset = cardReadResult.nibssIccSubset,
                        applicationTransactionCounter = cardReadResult.applicationTransactionCounter,
                        terminalVerificationResults = cardReadResult.terminalVerificationResults,
                        expirationDate = cardReadResult.expirationDate,
                        applicationPrimaryAccountNumber = cardReadResult.applicationPrimaryAccountNumber,
                        applicationPANSequenceNumber = cardReadResult.applicationPANSequenceNumber,
                        transactionDate = cardReadResult.transactionDate,
                        cryptogramInformationData = cardReadResult.cryptogramInformationData,
                        dedicatedFileName = cardReadResult.dedicatedFileName,
                        transactionSequenceNumber = cardReadResult.transactionSequenceNumber,
                        transactionSequenceCounter = cardReadResult.transactionSequenceCounter,
                        cryptogram = cardReadResult.cryptogram,
                        track2Data = cardReadResult.track2Data,
                        cardholderVerificationMethod = cardReadResult.cardholderVerificationMethod,
                        applicationInterchangeProfile = cardReadResult.applicationInterchangeProfile,
                        pinBlockDUKPT = cardReadResult.pinBlockDUKPT,
                        pinBlockTrippleDES = cardReadResult.pinBlockTrippleDES,
                        cardScheme = cardReadResult.cardScheme,
                        applicationDiscretionaryData = cardReadResult.applicationDiscretionaryData,
                        unpredictableNumber = cardReadResult.unpredictableNumber,
                        interfaceDeviceSerialNumber = cardReadResult.interfaceDeviceSerialNumber,
                        encryptedPinBlock = cardReadResult.encryptedPinBlock,
                        terminalType = cardReadResult.terminalType,
                        cardHolderName = cardReadResult.cardHolderName,
                        originalDeviceSerial = cardReadResult.originalDeviceSerial,
                        transactionCurrencyCode = cardReadResult.transactionCurrencyCode,
                        terminalCountryCode = cardReadResult.terminalCountryCode,
                        cashBackAmount = cardReadResult.cashBackAmount,
                        terminalCapabilities = cardReadResult.terminalCapabilities,
                        plainPinKey = cardReadResult.plainPinKey,
                        originalPan = cardReadResult.originalPan,
                )
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.CardData,
                        "card time out",
                        true,
                        shola
                )
                )
            }
        })
        CardManager.instance.setCardSchemeListener(object : CardManager.CardSchemeListener {
            override fun onCardScheme(cardScheme: String, maskedPan: String) {
                callback.invoke(
                    TransactionMonitor(
                        CardReadState.CardDetected,
                        "card detected $cardScheme, $maskedPan",
                        true,
                        null as CardReadResult?
                )
                )
            }

        })
        CardManager.instance.startCardDealService(context)
    }


    private fun cancelCheckCard() {
        synchronized(this) {
            try {
                if (mCheckCard != null) {
                    mCheckCard!!.cancelCheckCard()
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    suspend fun onCardRemoved(): CardReadResult {
        TODO("Not yet implemented")
    }

    val fees1: String
        get() {

            return when (terminal.feeType) {
                "flat" -> terminal.flatFee ?: "0"
                "percent" -> {
                    val charge = (terminal.feeCent?.toDoubleOrNull() ?: 0.0) / 100 * (PosApplication.getApp().mConsumeData?.amount?.toIntOrNull()
                        ?: 0)
                    val feeCap = terminal.feeCap?.toDoubleOrNull() ?: 0.0
                    if (charge >= feeCap) {
                        terminal.feeCap ?: "0"
                    } else {
                        "%.2f".format(charge)
                    }
                }
                else -> "0"
            }
        }

    val fees: String
        get() {
            return if (terminal.feeBearer != "customer") {
                "0"
            } else {
                println("charge")
                println(fees1)
                fees1
            }
        }
}