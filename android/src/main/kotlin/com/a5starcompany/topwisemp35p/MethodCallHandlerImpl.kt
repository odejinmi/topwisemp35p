package com.a5starcompany.topwisemp35p

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
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
    MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener,
    PluginRegistry.RequestPermissionsResultListener, EventChannel.StreamHandler{

    private var channel: MethodChannel? = null
    private var result: MethodChannel.Result? = null
    private var eventchannel : EventChannel? = null
    private var eventSink: EventChannel.EventSink? = null


    init {
        channel = MethodChannel(messenger!!, "topwisemp35p")

        channel?.setMethodCallHandler(this)

        eventchannel = EventChannel(messenger!!, "topwisemp35pevent")
        eventchannel?.setStreamHandler(this)

        binding.addActivityResultListener(this)
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
    }

    override fun onCancel(arguments: Any?) {
        eventSink = null
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
            var map1: MutableMap<String, Any> = mutableMapOf()
            if (it.transactionData != null) {
                val transactionData = it.transactionData!!

                val maskedPan = transactionData.applicationPrimaryAccountNumber.let { pan ->
                    val stars = "*".repeat(pan.length - 9)
                    pan.take(5) + stars + pan.takeLast(4)
                }

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
            binding.activity.runOnUiThread {
                eventSink?.success(map)
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