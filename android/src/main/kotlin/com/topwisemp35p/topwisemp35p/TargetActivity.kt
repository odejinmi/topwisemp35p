package com.example.paylony_pos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paylony.topwise.emvreader.CardReaderImpl
import com.paylony.topwise.emvreader.card.CardManager
import com.paylony.topwise.emvreader.emv.CardReadResult
import com.paylony.topwise.emvreader.emv.CardReadState
import com.topwisemp35p.topwisemp35p.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        supportActionBar?.hide()
//        receiptObject.agentAddress = "Lekki Phase 1"
//        receiptObject.agentName = "Paylony Test Agent"
//        receiptObject.receiptNumber = Utils.transactionRef.takeLast(6)
//        receiptObject.terminalId = "2UBANG37"
//        receiptObject.paymentType = "CASHOUT"

        val amount = intent.getStringExtra("amount")!!
        readCard(amount)
    }


    fun readCard(amount: String) {
        lifecycleScope.launch {
            val cardManager = CardReaderImpl(this@TargetActivity)
            cardManager.readCard(amount).collect {
                when (it) {
                    CardReadState.CallBackCanceled -> {
//                    emit(EmvUiState.CallBackCanceled)
                    }

                    is CardReadState.CallBackError -> {
//                    emit(EmvUiState.CallBackError(it.errorCode))
                    }

                    CardReadState.CallBackTransResult -> {
//                    emit(EmvUiState.CallBackTransResult)
                    }


                    is CardReadState.CardData -> {

                        it.cardReadResult.applicationPrimaryAccountNumber.let {
                            val numbersOfStars =
                                it.length - (it.take(5).length + it.takeLast(4).length)
                            var stars = ""
                            for (i in 1..numbersOfStars)
                                stars += "*"
//                            receiptObject.maskedPan = it.take(5) + stars + it.takeLast(4)
                        }
//                        receiptObject.cardType = "savings"
//                        receiptObject.transactionAmount = "NGN${amount}"
//                        receiptObject.dateAndTime = SimpleDateFormat("dd/MM/yyyy a").format(Date())
//                        it.cardReadResult.accountType = "savings"

                        val intent = Intent()
//                        val map: MutableMap<String, Any> = mutableMapOf()
                        val map1: MutableMap<String, Any> = mutableMapOf()
                        val transactionData = it.cardReadResult
//                        map1["pinBlock"] = transactionData.pinBlock
//                        map1["track2Data"] = transactionData.track2Data
                        map1["amount"] = transactionData.amount
//                        map1["accountType"] = transactionData.accountType
                        map1["amountAuthorized"] = transactionData.amountAuthorized
                        intent.putExtra("amountAuthorized", transactionData.amountAuthorized)
                        map1["applicationDiscretionaryData"] = transactionData.applicationDiscretionaryData
                        intent.putExtra("applicationDiscretionaryData", transactionData.applicationDiscretionaryData)
                        map1["applicationInterchangeProfile"] = transactionData.applicationInterchangeProfile
                        intent.putExtra("applicationInterchangeProfile", transactionData.applicationInterchangeProfile)
                        map1["applicationIssuerData"] = transactionData.applicationIssuerData
                        intent.putExtra("applicationIssuerData", transactionData.applicationIssuerData)
                        map1["applicationPANSequenceNumber"] = transactionData.applicationPANSequenceNumber
                        intent.putExtra("applicationPANSequenceNumber", transactionData.applicationPANSequenceNumber)
                        map1["applicationPrimaryAccountNumber"] = transactionData.applicationPrimaryAccountNumber
                        intent.putExtra("applicationPrimaryAccountNumber", transactionData.applicationPrimaryAccountNumber)
                        map1["applicationTransactionCounter"] = transactionData.applicationTransactionCounter
                        intent.putExtra("applicationTransactionCounter", transactionData.applicationTransactionCounter)
                        map1["applicationVersionNumber"] = transactionData.applicationVersionNumber
                        intent.putExtra("applicationVersionNumber", transactionData.applicationVersionNumber)
                        map1["authorizationResponseCode"] = transactionData.authorizationResponseCode
                        intent.putExtra("authorizationResponseCode", transactionData.authorizationResponseCode)
                        map1["cardHolderName"] = transactionData.cardHolderName
                        intent.putExtra("cardHolderName", transactionData.cardHolderName)
                        map1["cardScheme"] = transactionData.cardScheme
                        intent.putExtra("cardScheme", transactionData.cardScheme)
                        map1["cardSeqenceNumber"] = transactionData.cardSeqenceNumber
                        intent.putExtra("cardSeqenceNumber", transactionData.cardSeqenceNumber)
                        map1["cardholderVerificationMethod"] = transactionData.cardholderVerificationMethod
                        intent.putExtra("cardholderVerificationMethod", transactionData.cardholderVerificationMethod)
                        map1["cashBackAmount"] = transactionData.cashBackAmount
                        intent.putExtra("cashBackAmount", transactionData.cashBackAmount)
                        map1["cryptogram"] = transactionData.cryptogram
                        intent.putExtra("cryptogram", transactionData.cryptogram)
                        map1["cryptogramInformationData"] = transactionData.cryptogramInformationData
                        intent.putExtra("cryptogramInformationData", transactionData.cryptogramInformationData)
                        map1["dedicatedFileName"] = transactionData.dedicatedFileName
                        intent.putExtra("dedicatedFileName", transactionData.dedicatedFileName)
                        map1["deviceSerialNumber"] = transactionData.deviceSerialNumber
                        intent.putExtra("deviceSerialNumber", transactionData.deviceSerialNumber)
                        map1["dencryptedPinBlock"] = transactionData.encryptedPinBlock
                        intent.putExtra("encryptedPinBlock", transactionData.encryptedPinBlock)
                        map1["expirationDate"] = transactionData.expirationDate
                        intent.putExtra("expirationDate", transactionData.expirationDate)
                        map1["iccDataString"] = transactionData.iccDataString
                        intent.putExtra("iccDataString", transactionData.iccDataString)
                        map1["interfaceDeviceSerialNumber"] = transactionData.interfaceDeviceSerialNumber
                        intent.putExtra("interfaceDeviceSerialNumber", transactionData.interfaceDeviceSerialNumber)
                        map1["issuerApplicationData"] = transactionData.issuerApplicationData
                        intent.putExtra("issuerApplicationData", transactionData.issuerApplicationData)
                        map1["nibssIccSubset"] = transactionData.nibssIccSubset
                        intent.putExtra("nibssIccSubset", transactionData.nibssIccSubset)
                        map1["originalDeviceSerial"] = transactionData.originalDeviceSerial
                        intent.putExtra("originalDeviceSerial", transactionData.originalDeviceSerial)
                        map1["originalPan"] = transactionData.originalPan
                        intent.putExtra("originalPan", transactionData.originalPan)
                        map1["pinBlock"] = transactionData.pinBlock
                        intent.putExtra("pinBlock", transactionData.pinBlock)
                        map1["pinBlockDUKPT"] = transactionData.pinBlockDUKPT
                        intent.putExtra("pinBlockDUKPT", transactionData.pinBlockDUKPT)
                        map1["pinBlockTrippleDES"] = transactionData.pinBlockDUKPT
                        intent.putExtra("pinBlockDUKPT", transactionData.pinBlockDUKPT)
                        map1["plainPinKey"] = transactionData.plainPinKey
                        intent.putExtra("plainPinKey", transactionData.plainPinKey)
                        map1["terminalCapabilities"] = transactionData.terminalCapabilities
                        intent.putExtra("terminalCapabilities", transactionData.terminalCapabilities)
                        map1["terminalCountryCode"] = transactionData.terminalCountryCode
                        intent.putExtra("terminalCountryCode", transactionData.terminalCountryCode)
                        map1["terminalType"] = transactionData.terminalType
                        intent.putExtra("terminalType", transactionData.terminalType)
                        map1["terminalVerificationResults"] = transactionData.terminalVerificationResults
                        intent.putExtra("terminalVerificationResults", transactionData.terminalVerificationResults)
                        map1["track2Data"] = transactionData.track2Data
                        intent.putExtra("track2Data", transactionData.track2Data)
                        map1["transactionCurrencyCode"] = transactionData.transactionCurrencyCode
                        intent.putExtra("transactionCurrencyCode", transactionData.transactionCurrencyCode)
                        map1["transactionDate"] = transactionData.transactionDate
                        intent.putExtra("transactionDate", transactionData.transactionDate)
                        map1["transactionSequenceCounter"] = transactionData.transactionSequenceCounter
                        intent.putExtra("transactionSequenceCounter", transactionData.transactionSequenceCounter)
                        map1["transactionSequenceNumber"] = transactionData.transactionSequenceNumber
                        intent.putExtra("transactionSequenceNumber", transactionData.transactionSequenceNumber)
                        map1["transactionType"] = transactionData.transactionType
                        intent.putExtra("transactionType", transactionData.transactionType)
                        map1["unifiedPaymentIccData"] = transactionData.unifiedPaymentIccData
                        intent.putExtra("unifiedPaymentIccData", transactionData.unifiedPaymentIccData)
                        map1["unpredictableNumber"] = transactionData.unpredictableNumber
                        intent.putExtra("unpredictableNumber", transactionData.unpredictableNumber)

//                        map["state"] = it.state.toString()
//                        map["message"] = it.message
//                        map["status"] = it.status
//                        map["transactionData"] = map1
//                        intent.putExtra("bank", network)
//                        intent.putExtra("image", image)
                        setResult(RESULT_OK, intent)
                        finish()
//                        debitCard(it.cardReadResult)
                    }


                    is CardReadState.CardDetected -> {
                        Toast.makeText(
                            applicationContext,
                            "amount is required, " + amount,
                            Toast.LENGTH_SHORT
                        ).show()
//////                        val intent = Intent()
//////                        intent.setClass(this@MainActivity, PinpadActivity3::class.java)
//////
//////                        /********* Jeremy  modify 20200602
//////                         * goto the input pin activity
//////                         * pass the pin type to PinpadActivity
//////                         */
//////                        val bundle = Bundle()
//////                        bundle.putInt("keytype", 0)
//////                        bundle.putBoolean("online", true)
//////                        if (bundle != null) {
//////                            intent.putExtras(bundle)
//////                        }
//////                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//////                        startActivity(intent)
////                        showProgressBar(false, "")
//                    emit(EmvUiState.CardDetected(it.cardHolderName, it.cardMaskedPan))
                    }

                    CardReadState.CardReadTimeOut -> {
//                    emit(EmvUiState.CardReadTimeOut)

                    }

                    CardReadState.Loading -> {
//                        showProgressBar(true, "Please insert your card")
                    }

                    else -> {}
                }
            }
        }
    }
}