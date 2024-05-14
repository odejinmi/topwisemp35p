package com.a5starcompany.topwisemp35p

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import com.a5starcompany.topwisemp35p.emvreader.TopWiseDevice
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.a5starcompany.topwisemp35p.emvreader.DeviceTopUsdkServiceManager
import com.a5starcompany.topwisemp35p.emvreader.printer.*
import com.a5starcompany.topwisemp35p.emvreader.emv.CardReadState


const val ERROR_CODE_PAYMENT_INITIALIZATION = "INIT_PAYMENT_ERROR"

class MethodCallHandlerImpl(
    messenger: BinaryMessenger?,
    private val binding: ActivityPluginBinding
) :
    MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener, PluginRegistry.RequestPermissionsResultListener {

    private var channel: MethodChannel? = null
    private var result: MethodChannel.Result? = null


    init {
        channel = MethodChannel(messenger!!, "topwisemp35p")

        channel?.setMethodCallHandler(this)

        binding.addActivityResultListener(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        this.result = result

        when (call.method) {

//            "initialize" -> {
//                initialise(call)
//            }

            "debitcard" -> {
                        makePayment(call)
            }

             "getcardsheme" -> {
                 getcardsheme(call)
            }

            "serialnumber" -> {
                val map: MutableMap<String, Any> = mutableMapOf()
                map["state"] = "1"
                map["message"] = serialnumber
                map["status"] = true
                result.success(serialnumber)
            }


            "startprint" -> {
                startPrint(call)
            }

            "starteodPrint" -> {
                starteodPrint(call)
            }

            "startcustomprint" -> {
                startCustomPrint(call)
            }



            else -> {
                result.notImplemented()
            }
        }

    }

    /**
     * It is invoked when making transaction
     * @param arg is the data that was passed in from the flutter side to make payment
     */
    private fun makePayment(call: MethodCall) {

        if (!(call.arguments is Map<*,*>)) {
            result?.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
            return
        }

        val amount = call.argument<String>("amount")!!
        topWiseDevice.readCard(amount)
    }

    private fun getcardsheme(call: MethodCall) {

        if (!(call.arguments is Map<*,*>)) {
            result?.error(ERROR_CODE_PAYMENT_INITIALIZATION, "Invalid input(s)", null)
            return
        }

        val amount = call.argument<String>("amount")!!
        topWiseDevice.getCardScheme(amount)
    }

    val serialnumber: String
        get() = DeviceTopUsdkServiceManager.instance?.systemManager?.serialNo!!


    private val topWiseDevice by lazy {
        TopWiseDevice(binding.activity) {
            when (it.state) {
                CardReadState.CallBackCanceled -> {
//                    emit(EmvUiState.CallBackCanceled)

                    Log.e("TAG", "startcustomPrint: call back canceled ${String}")
                }

                CardReadState.CallBackError -> {
//                    emit(EmvUiState.CallBackError(it.errorCode))

                    Log.e("TAG", "startcustomPrint: call back error ${String}")
                }

                CardReadState.CallBackTransResult -> {
//                    emit(EmvUiState.CallBackTransResult)

                    Log.e("TAG", "startcustomPrint: call back transresult  ${String}")
                }


                CardReadState.CardData -> {

                    Log.e("TAG", "startcustomPrint: card data")
                    if (it.transactionData != null) {
                        it.transactionData!!.applicationPrimaryAccountNumber.let {
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
                        val map: MutableMap<String, Any> = mutableMapOf()
                        val map1: MutableMap<String, Any> = mutableMapOf()
                        val transactionData = it.transactionData!!
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

                        map["state"] = "1"
                        map["message"] = "successful"
                        map["status"] = true
                        map["transactionData"] = map1

                        Log.e("TAG", "startcustomPrint: card detected ${map}")
//            accountType = AccountType.valueOf(data?.getStringExtra(ACCOUNT_TYPE).toString())
//            readCard()


                        result?.success(map)
                    }
//                        debitCard(it.cardReadResult)
                }


                CardReadState.CardDetected -> {

                    Log.e("TAG", "startcustomPrint: card detected ")
//                    Toast.makeText(
//                            binding.activity,
//                            "amount is required, ",
//                            Toast.LENGTH_SHORT
//                    ).show()
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

                    Log.e("TAG", "startcustomPrint: card time out")
//                    emit(EmvUiState.CardReadTimeOut)

                }

                CardReadState.Loading -> {
                    Log.e("TAG", "startcustomPrint: loading")
//                        showProgressBar(true, "Please insert your card")
                }

                else -> {}
            }
        }
    }


    private fun startPrint(call: MethodCall) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding.activity.resources,
//            R.mipmap.ic_launcher
//        )
        val base64String: String = call.argument<String>("base64image")!!
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding.activity, null)
        template.clear()

        template.add(ImageUnit(Align.CENTER, bitmap, 550, 70))

        template.add(
            TextUnit(
                "TRANSACTION RECEIPT",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val copytype = call.argument<String>("copytype")!!
        template.add(
            TextUnit(
                copytype + " Copy",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call.argument<String>("marchantname")!!
        template.add(
            TextUnit(
                marchantname,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call.argument<String>("marchantaddress")!!
        template.add(
            TextUnit(
                marchantaddress,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val transactiontype = call.argument<String>("transactiontype")!!
        template.add(
            TextUnit(
                transactiontype,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val amount = call.argument<String>("amount")!!
        template.add(
            TextUnit(
                "₦" + amount,
                TextUnit.TextSize.NORMAL,
                Align.CENTER
            )
                .setBold(true)
        )
        val transactionstatus = call.argument<String>("transactionstatus")!!
        template.add(
            TextUnit(
                transactionstatus, TextUnit.TextSize.SMALL, Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val serialno = call.argument<String>("serialno")!!
        template.add(
            1,
            TextUnit("Serial No", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(serialno, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val terminalid = call.argument<String>("terminalid")!!
        template.add(
            1,
            TextUnit("Terminal ID", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(terminalid, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val rrn = call.argument<String>("rrn")!!
        template.add(
            1,
            TextUnit("RRN:", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(rrn, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val pan = call.argument<String>("pan")
        if(pan != null) {
            template.add(
                1,
                TextUnit("Card No:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(pan, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val stan = call.argument<String>("stan")
        if(stan != null) {
            template.add(
                1,
                TextUnit("STAN:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(stan, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val businessaccountname = call.argument<String>("businessaccountname")
        if(businessaccountname != null) {
            template.add(
                1,
                TextUnit("Business Account Name:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(stan, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val businessaccountnumber = call.argument<String>("businessaccountnumber")
        if(businessaccountnumber != null) {
            template.add(
                1,
                TextUnit("Business Account Number:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(stan, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val businessbank = call.argument<String>("businessbank")
        if(businessbank != null) {
            template.add(
                1,
                TextUnit("Business Bank:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(stan, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val accname = call.argument<String>("accountname")
        if(accname != null) {
            template.add(
                1,
                TextUnit("Acc Name:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(accname, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val accno = call.argument<String>("accountnumber")
        if(accno != null) {
            template.add(
                1,
                TextUnit("Acc No:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(accno, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val bankname = call.argument<String>("bank")
        if(bankname != null) {
            template.add(
                1,
                TextUnit("Bank Name:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(bankname, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val sessionid = call.argument<String>("sessionid")
        if(sessionid != null) {
            template.add(
                1,
                TextUnit("Session Id:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(sessionid, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val deviceid = call.argument<String>("deviceid")
        if(deviceid != null) {
            template.add(
                1,
                TextUnit("Device Id:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(deviceid, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val devicetype = call.argument<String>("devicetype")
        if(devicetype != null) {
            template.add(
                1,
                TextUnit("Device Type:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(devicetype, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val paymentname = call.argument<String>("paymentname")
        if(paymentname != null) {
            template.add(
                1,
                TextUnit("Payment Name:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(paymentname, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val paymentcode = call.argument<String>("paymentcode")
        if(paymentcode != null) {
            template.add(
                1,
                TextUnit("Payment Code:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(paymentcode, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val Phonenumber = call.argument<String>("phonenumber")
        if(Phonenumber != null) {
            template.add(
                1,
                TextUnit("Phone Number:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(Phonenumber, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val network = call.argument<String>("network")
        if(network != null) {
            template.add(
                1,
                TextUnit("Network:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(network, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val description = call.argument<String>("description")
        if(description != null) {
            template.add(
                1,
                TextUnit("Description:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(description, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val disco = call.argument<String>("disco")
        if(disco != null) {
            template.add(
                1,
                TextUnit("Disco:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(disco, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val meteraccname = call.argument<String>("meteraccname")
        if(meteraccname != null) {
            template.add(
                1,
                TextUnit("Meter Acc Name:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(meteraccname, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val meterno = call.argument<String>("meterno")
        if(meterno != null) {
            template.add(
                1,
                TextUnit("Meter No:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(meterno, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val token = call.argument<String>("token")
        if(token != null) {
            template.add(
                1,
                TextUnit("Token:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(token, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val unit = call.argument<String>("unit")
        if(unit != null) {
            template.add(
                1,
                TextUnit("Unit:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(unit, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val address = call.argument<String>("address")
        if(address != null) {
            template.add(
                1,
                TextUnit("Address:", TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                1,
                TextUnit(address, TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }
        val datetime = call.argument<String>("datetime")!!
        template.add(
            1,
            TextUnit("DATE & TIME", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(datetime, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        template.add(starttextUnit())
        val bottommessage = call.argument<String>("bottommessage")!!
        template.add(
            TextUnit(
                bottommessage,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val appversion = call.argument<String>("appversion")!!
        template.add(
            TextUnit(
                "App Version "+appversion,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }
    private fun starteodPrint(call: MethodCall) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding.activity.resources,
//            R.mipmap.ic_launcher
//        )ham@gmail.com
        val base64String: String = call.argument<String>("base64image")!!
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding.activity, null)
        template.clear()

        template.add(ImageUnit(Align.CENTER, bitmap, 550, 70))

        template.add(
            TextUnit(
                "End of Day summary",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call.argument<String>("marchantname")!!
        template.add(
            TextUnit(
                marchantname,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call.argument<String>("marchantaddress")!!
        template.add(
            TextUnit(
                marchantaddress,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val datetime = call.argument<String>("datetime")!!
        template.add(
            1,
            TextUnit("DATE", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(datetime, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(
            1,
            TextUnit("Time", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            2,
            TextUnit("RRN", TextUnit.TextSize.SMALL, Align.CENTER)
                .setBold(true),
            1,
            TextUnit("Amount", TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true),
            1,
            TextUnit("Status", TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )

        val numbers = call.argument<List<MutableMap<String, String>>>("details")!!
        for (number in numbers) {
            template.add(
                1,
                TextUnit(number.get("created_at"), TextUnit.TextSize.SMALL, Align.LEFT)
                    .setBold(true),
                2,
                TextUnit(number.get("ref"), TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true),
                1,
                TextUnit(number.get("amount"), TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true),
                1,
                TextUnit(number.get("status"), TextUnit.TextSize.SMALL, Align.RIGHT)
                    .setBold(true)
            )
        }

        val totalapproved = call.argument<String>("totalapproved")!!
        template.add(
            1,
            TextUnit("Total Approved", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(totalapproved, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val totalfailed = call.argument<String>("totalfailed")!!
        template.add(
            1,
            TextUnit("Total Failed", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(totalfailed, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val totalcredit = call.argument<String>("totalcredit")!!
        template.add(
            1,
            TextUnit("Total Credit", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(totalcredit, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        val totaldebit = call.argument<String>("totaldebit")!!
        template.add(
            1,
            TextUnit("Total Debit", TextUnit.TextSize.SMALL, Align.LEFT)
                .setBold(true),
            1,
            TextUnit(totaldebit, TextUnit.TextSize.SMALL, Align.RIGHT)
                .setBold(true)
        )
        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }

    private fun startCustomPrint(call: MethodCall) {

        val marchantname = call.argument<String>("textprint")
        Log.e("TAG", "startcustomPrint: $marchantname",)
        val printmodel = Printmodel.fromJson(marchantname!!)
        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding.activity, null)
        template.clear()

        for (i in printmodel) {
            if (i.data.size == 1 && i.data[0].image != null) {
                template.add(imageUnit(i.data[0]))

            } else if (i.data[0].text != null && i.data.size == 2) {
                template.add(
                    i.data[0].flex,
                    textUnit(i.data[0]),
                    i.data[1].flex,
                    textUnit(i.data[1])
                )
            } else {
                template.add(textUnit(i.data[0]))
            }
        }
        Log.e("TAG", "startcustomPrint: hello boy",)

        val nums = arrayOf(1, 5, 10)
        for (i in nums) {
            template.add(
                TextUnit(
                    "",
                    TextUnit.TextSize.SMALL,
                    Align.CENTER
                )
            )
        }

            topWiseDevice.printDoc(template) //perform print operation


    }

    private fun imageUnit(i: Datum): ImageUnit {
        val base64String = i.image
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return if (i.imageheight == null && i.imagewidth == null) {
            ImageUnit(
                align(i.align!!) , bitmap, bitmap.width, bitmap.height,
            )
        } else if (i.imageheight == null && i.imagewidth != null) {
            ImageUnit(
                align(i.align!!) , bitmap, i.imagewidth, bitmap.height,
            )
        } else if (i.imageheight != null && i.imagewidth == null) {
            ImageUnit(
                align(i.align!!), bitmap, bitmap.width, i.imageheight,
            )
        } else {
            ImageUnit(
                align(i.align!!), bitmap, i.imagewidth!!, i.imageheight!!,
            )

        }

    }


    private fun textUnit(text: Datum): TextUnit {
        return if (text.align == null) {
            TextUnit(text.text)
        } else {
            TextUnit(
                text.text,
                textSize(text.textsize!!),
                align(text.align)
            )

        }
            .setBold(text.bold == true)
            .setWordWrap(text.textwrap == true)

    }
    private fun starttextUnit(): TextUnit {
        return TextUnit("*****************************************************")
            .setWordWrap(false)
            .setBold(true)

    }

    private fun spacetextUnit(): TextUnit {
        return TextUnit(
            "\n\n\n\n\n",
            TextUnit.TextSize.SMALL,
            Align.CENTER
        )

    }

    private fun align(align: String): Align {
        return when (align) {
            "center" -> {
                Align.CENTER
            }

            "right" -> {
                Align.RIGHT
            }

            else -> {
                Align.LEFT
            }
        }
    }

    private fun textSize(align: String): Int {
        return when (align) {
            "normal" -> {
                TextUnit.TextSize.NORMAL
            }

            "large" -> {
                TextUnit.TextSize.LARGE
            }

            "small" -> {
                TextUnit.TextSize.SMALL
            }

            else -> {
                TextUnit.TextSize.XLARGE
            }
        }
    }

//    private fun time(timestamp: String): String {
//        val formatter = DateTimeFormatter.ISO_DATE_TIME
//        val parsedDateTime = LocalDateTime.parse(timestamp, formatter)
//
//        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
//        val formattedTime = parsedDateTime.format(timeFormatter)
//        return formattedTime
//    }



    /**
     * this is the call back that is invoked when the activity result returns a value after calling
     * startActivityForResult().
     * @param data is the intent that has the bundle where we can get our result [MonnifyTransactionResponse]
     * @param requestCode if it matches with our [REQUEST_CODE] it means the result if the one we
     * asked for.
     * @param resultCode, it is okay if it equals [RESULT_OK]
     */
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
//
//        return true
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
//        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
//            val map: MutableMap<String, Any> = mutableMapOf()
//            val map1: MutableMap<String, Any> = mutableMapOf()
//
//            map1["amountAuthorized"] = data?.getStringExtra("amountAuthorized").toString()
//            map1["applicationDiscretionaryData"] =
//                data?.getStringExtra("applicationDiscretionaryData").toString()
//            map1["applicationInterchangeProfile"] =
//                data?.getStringExtra("applicationInterchangeProfile").toString()
//            map1["applicationIssuerData"] =
//                data?.getStringExtra("applicationIssuerData").toString()
//            map1["applicationPANSequenceNumber"] =
//                data?.getStringExtra("applicationPANSequenceNumber").toString()
//            map1["applicationPrimaryAccountNumber"] =
//                data?.getStringExtra("applicationPrimaryAccountNumber").toString()
//            map1["applicationTransactionCounter"] =
//                data?.getStringExtra("applicationTransactionCounter").toString()
//            map1["applicationVersionNumber"] =
//                data?.getStringExtra("applicationVersionNumber").toString()
//            map1["authorizationResponseCode"] =
//                data?.getStringExtra("authorizationResponseCode").toString()
//            map1["cardHolderName"] = data?.getStringExtra("cardHolderName").toString()
//            map1["cardScheme"] = data?.getStringExtra("cardScheme").toString()
//            map1["cardSeqenceNumber"] = data?.getStringExtra("cardSeqenceNumber").toString()
//            map1["cardholderVerificationMethod"] =
//                data?.getStringExtra("cardholderVerificationMethod").toString()
//            map1["cashBackAmount"] = data?.getStringExtra("cashBackAmount").toString()
//            map1["cryptogram"] = data?.getStringExtra("cryptogram").toString()
//            map1["cryptogramInformationData"] =
//                data?.getStringExtra("cryptogramInformationData").toString()
//            map1["dedicatedFileName"] = data?.getStringExtra("dedicatedFileName").toString()
//            map1["deviceSerialNumber"] = data?.getStringExtra("deviceSerialNumber").toString()
//            map1["dencryptedPinBlock"] = data?.getStringExtra("encryptedPinBlock").toString()
//            map1["expirationDate"] = data?.getStringExtra("expirationDate").toString()
//            map1["iccDataString"] = data?.getStringExtra("iccDataString").toString()
//            map1["interfaceDeviceSerialNumber"] =
//                data?.getStringExtra("interfaceDeviceSerialNumber").toString()
//            map1["issuerApplicationData"] =
//                data?.getStringExtra("issuerApplicationData").toString()
//            map1["nibssIccSubset"] = data?.getStringExtra("nibssIccSubset").toString()
//            map1["originalDeviceSerial"] =
//                data?.getStringExtra("originalDeviceSerial").toString()
//            map1["originalPan"] = data?.getStringExtra("originalPan").toString()
//            map1["pinBlock"] = data?.getStringExtra("pinBlock").toString()
//            map1["pinBlockDUKPT"] = data?.getStringExtra("pinBlockDUKPT").toString()
//            map1["pinBlockTrippleDES"] = data?.getStringExtra("pinBlockDUKPT").toString()
//            map1["plainPinKey"] = data?.getStringExtra("plainPinKey").toString()
//            map1["terminalCapabilities"] =
//                data?.getStringExtra("terminalCapabilities").toString()
//            map1["terminalCountryCode"] = data?.getStringExtra("terminalCountryCode").toString()
//            map1["terminalType"] = data?.getStringExtra("terminalType").toString()
//            map1["terminalVerificationResults"] =
//                data?.getStringExtra("terminalVerificationResults").toString()
//            map1["track2Data"] = data?.getStringExtra("track2Data").toString()
//            map1["transactionCurrencyCode"] =
//                data?.getStringExtra("transactionCurrencyCode").toString()
//            map1["transactionDate"] = data?.getStringExtra("transactionDate").toString()
//            map1["transactionSequenceCounter"] =
//                data?.getStringExtra("transactionSequenceCounter").toString()
//            map1["transactionSequenceNumber"] =
//                data?.getStringExtra("transactionSequenceNumber").toString()
//            map1["transactionType"] = data?.getStringExtra("transactionType").toString()
//            map1["unifiedPaymentIccData"] =
//                data?.getStringExtra("unifiedPaymentIccData").toString()
//            map1["unpredictableNumber"] = data?.getStringExtra("unpredictableNumber").toString()
//
//            map["state"] = "1"
//            map["message"] = "successful"
//            map["status"] = true
//            map["transactionData"] = map1
////            accountType = AccountType.valueOf(data?.getStringExtra(ACCOUNT_TYPE).toString())
////            readCard()
//
//
//            result?.success(map)
        }
        return true
//
    }

    /**
     * dispose the channel when this handler detaches from the activity
     */
    fun dispose() {
        channel?.setMethodCallHandler(null)
        channel = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        TODO("Not yet implemented")
    }
}