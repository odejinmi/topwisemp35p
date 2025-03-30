package com.lonytech.topwisesdk

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.a5starcompany.topwisemp35p.emvreader.TopWiseDevice
import com.a5starcompany.topwisemp35p.emvreader.app.PosApplication
import com.a5starcompany.topwisemp35p.emvreader.emv.CardReadState
import com.a5starcompany.topwisemp35p.emvreader.emv.TransactionMonitor
import com.a5starcompany.topwisemp35p.emvreader.printer.PrintTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Paylony (val context: Context,  val terminal: Terminal, callback: (TransactionMonitor) -> Unit) {

    var transactiontype: String = ""
    var accDetails : String = ""
    fun readCard(amount: String, transactiontype: String, accDetails: String){
        this.transactiontype = transactiontype
        this.accDetails = accDetails
        topWiseDevice.readCard(amount)
    }

    fun printDoc(template: PrintTemplate) {
        topWiseDevice.printDoc(template)
    }

//    fun printDoc(template: Bitmap) {
//        topWiseDevice.printDoc(template)
//    }

    fun enterpin(directpin: String) {
        topWiseDevice.enterpin(directpin)
    }

    val serialnumber: String
        get() = topWiseDevice.serialnumber

    val topWiseDevice by lazy {
        TopWiseDevice(context) {
                val map1: MutableMap<String, Any> = mutableMapOf()
                val map: MutableMap<String, Any> = mutableMapOf(
                    "state" to it.state.toString(),
                    "message" to it.message,
                    "status" to it.status,
                    "transactionData" to map1
                )
                println(map)
                if (it.state == CardReadState.CardData) {
                    if (it.transactionData != null) {
                        val transactionData = it.transactionData!!
                        val data = mapOf(
                            "accountType" to transactionData.amount,
                            "amount" to PosApplication.getApp().mConsumeData?.amount, // typed amount
                            "fee" to fees1,
                            "totalAmount" to ((PosApplication.getApp().mConsumeData?.amount?.toIntOrNull()
                                ?: 0) + fees.toInt()).toString(), // total and final amount to read with card
                            "type" to this.transactiontype,
                            "acc_details" to this.accDetails,
                            "amountAuthorized" to transactionData.amountAuthorized,
                            "applicationDiscretionaryData" to transactionData.applicationDiscretionaryData,
                            "applicationInterchangeProfile" to transactionData.applicationInterchangeProfile,
                            "applicationIssuerData" to transactionData.applicationIssuerData,
                            "applicationPANSequenceNumber" to transactionData.applicationPANSequenceNumber,
                            "applicationPrimaryAccountNumber" to transactionData.applicationPrimaryAccountNumber,
                            "applicationTransactionCounter" to transactionData.applicationTransactionCounter,
                            "applicationVersionNumber" to transactionData.applicationVersionNumber,
                            "authorizationResponseCode" to transactionData.authorizationResponseCode,
                            "cardHolderName" to transactionData.cardHolderName,
                            "cardScheme" to transactionData.cardScheme,
                            "cardSeqenceNumber" to transactionData.cardSeqenceNumber,
                            "cardholderVerificationMethod" to transactionData.cardholderVerificationMethod,
                            "cashBackAmount" to transactionData.cashBackAmount,
                            "cryptogram" to transactionData.cryptogram,
                            "cryptogramInformationData" to transactionData.cryptogramInformationData,
                            "dedicatedFileName" to transactionData.dedicatedFileName,
                            "deviceSerialNumber" to serialnumber,
                            "encryptedPinBlock" to transactionData.encryptedPinBlock,
                            "expirationDate" to transactionData.expirationDate,
                            "iccDataString" to transactionData.iccDataString,
                            "interfaceDeviceSerialNumber" to transactionData.interfaceDeviceSerialNumber,
                            "issuerApplicationData" to transactionData.issuerApplicationData,
                            "nibssIccSubset" to transactionData.nibssIccSubset,
                            "originalDeviceSerial" to transactionData.originalDeviceSerial,
                            "originalPan" to transactionData.originalPan,
                            "pinBlock" to transactionData.pinBlock,
                            "pinBlockDUKPT" to transactionData.pinBlockDUKPT,
                            "pinBlockTrippleDES" to transactionData.pinBlockTrippleDES,
                            "plainPinKey" to transactionData.plainPinKey,
                            "terminalCapabilities" to transactionData.terminalCapabilities,
                            "terminalCountryCode" to transactionData.terminalCountryCode,
                            "terminalId" to  terminal.terminalId,
                            "terminalType" to transactionData.terminalType,
                            "terminalVerificationResults" to transactionData.terminalVerificationResults,
                            "track2Data" to transactionData.track2Data,
                            "transactionCurrencyCode" to transactionData.transactionCurrencyCode,
                            "transactionDate" to transactionData.transactionDate,
                            "transactionSequenceCounter" to transactionData.transactionSequenceCounter,
                            "transactionSequenceNumber" to transactionData.transactionSequenceNumber,
                            "transactionType" to transactionData.transactionType,
                            "unifiedPaymentIccData" to transactionData.unifiedPaymentIccData,
                            "unpredictableNumber" to transactionData.unpredictableNumber
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            val response = ApiClient.post(
                                endpoint = "payment/card/debit",
                                token = terminal.token.toString(),
                                context = context,
                                data = data
                            )
                            Log.d("Response", response.toString())
                        }

                    }
                }else {
                    callback.invoke(
                        TransactionMonitor(
                            CardReadState.CardData,
                            it.message,
                            it.status,
                            it.transactionData
                        )
                    )
                }

            }
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