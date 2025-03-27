package com.lonytech.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lonytech.topwisesdk.emvreader.Terminal
import com.lonytech.topwisesdk.emvreader.TopWiseDevice

class MainActivity : AppCompatActivity() {
    var terminal: Terminal? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        terminal = Terminal(
            feeBearer = "",
            feeType = "",
            flatFee =  "",
            feeCent =  "",
            feeCap =  "",
            terminalId =  "",
            token =  "",
        )
        var cardpayment: cardpayment? = topWiseDevice?.let { cardpayment(it,this) }
        var print: print? = topWiseDevice?.let { print(it,this) }
    }


    val topWiseDevice by lazy {
        terminal?.let {
            TopWiseDevice(applicationContext, terminal = it) {
                var map1: MutableMap<String, Any> = mutableMapOf()
                if (it.transactionData != null) {
                    val transactionData = it.transactionData!!

                    map1 = mutableMapOf(
                        "amount" to transactionData.amount,
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
                        "deviceSerialNumber" to transactionData.deviceSerialNumber,
                        "dencryptedPinBlock" to transactionData.encryptedPinBlock,
                        "expirationDate" to transactionData.expirationDate,
                        "iccDataString" to transactionData.iccDataString,
                        "interfaceDeviceSerialNumber" to transactionData.interfaceDeviceSerialNumber,
                        "issuerApplicationData" to transactionData.issuerApplicationData,
                        "nibssIccSubset" to transactionData.nibssIccSubset,
                        "originalDeviceSerial" to transactionData.originalDeviceSerial,
                        "originalPan" to transactionData.originalPan,
                        "pinBlock" to transactionData.pinBlock,
                        "pinBlockDUKPT" to transactionData.pinBlockDUKPT,
                        "pinBlockTrippleDES" to transactionData.pinBlockDUKPT,
                        "plainPinKey" to transactionData.plainPinKey,
                        "terminalCapabilities" to transactionData.terminalCapabilities,
                        "terminalCountryCode" to transactionData.terminalCountryCode,
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

                }

                val map: MutableMap<String, Any> = mutableMapOf(
                    "state" to it.state.toString(),
                    "message" to it.message,
                    "status" to it.status,
                    "transactionData" to map1
                )

            }
        }
    }

}