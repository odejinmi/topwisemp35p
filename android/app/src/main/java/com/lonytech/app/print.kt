package com.lonytech.app

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.lonytech.topwisesdk.emvreader.TopWiseDevice
import com.lonytech.topwisesdk.emvreader.emv.TransactionMonitor
import com.lonytech.topwisesdk.emvreader.printer.*
import java.text.NumberFormat
import java.util.Locale

class print(val topWiseDevice : TopWiseDevice, val binding: Activity) {
    

    fun startPrint(call: Map<String, String?>) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding!!.activity.resources,
//            R.mipmap.ic_launcher
//        )
        val base64String: String = call["base64image"].toString()
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding, null)
        template.clear()

        template.add(
            ImageUnit(
                Align.CENTER,
                bitmap,
                550,
                70
            )
        )

        template.add(
            TextUnit(
                "TRANSACTION RECEIPT",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val copytype = call.get("copytype")!!
        template.add(
            TextUnit(
                "$copytype Copy",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call["marchantname"].toString()
        template.add(
            TextUnit(
                marchantname,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call["marchantaddress"].toString()
        template.add(
            TextUnit(
                marchantaddress,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val transactiontype: String = call["transactiontype"].toString()
        template.add(
            TextUnit(
                transactiontype,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val amount : Double = call["amount"]?.toDouble() ?:0.0  // Convert to Double for formatting
        val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(amount) // Format with commas
        template.add(
            TextUnit(
                "₦$formattedAmount",
                TextUnit.TextSize.NORMAL,
                Align.CENTER
            )
                .setBold(true)
        )
        val transactionstatus = call.get("transactionstatus")!!
        template.add(
            TextUnit(
                transactionstatus,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val serialno = call.get("serialno")!!
        template.add(
            1,
            TextUnit(
                "Serial No",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                serialno,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val terminalid = call["terminalid"]!!
        template.add(
            1,
            TextUnit(
                "Terminal ID",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                terminalid,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val rrn = call["rrn"]!!
        template.add(
            1,
            TextUnit(
                "RRN:",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                rrn,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val pan = call["pan"]
        if(pan != null) {
            template.add(
                1,
                TextUnit(
                    "Card No:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    pan,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val stan = call["stan"]
        if(stan != null) {
            template.add(
                1,
                TextUnit(
                    "STAN:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    stan,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val message = call.get("message")
        if(message != null) {
            template.add(
                1,
                TextUnit(
                    "Message:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    message,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessaccountname = call.get("businessaccountname")
        if(businessaccountname != null) {
            template.add(
                1,
                TextUnit(
                    "Business Account Name:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    businessaccountname,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessaccountnumber = call.get("businessaccountnumber")
        if(businessaccountnumber != null) {
            template.add(
                1,
                TextUnit(
                    "Business Account Number:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    stan,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessbank = call.get("businessbank")
        if(businessbank != null) {
            template.add(
                1,
                TextUnit(
                    "Business Bank:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    stan,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val accname = call.get("accountname")
        if(accname != null) {
            template.add(
                1,
                TextUnit(
                    "Acc Name:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    accname,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val accno = call.get("accountnumber")
        if(accno != null) {
            template.add(
                1,
                TextUnit(
                    "Acc No:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    accno,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val bankname = call.get("bank")
        if(bankname != null) {
            template.add(
                1,
                TextUnit(
                    "Bank Name:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    bankname,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val sessionid = call.get("sessionid")
        if(sessionid != null) {
            template.add(
                1,
                TextUnit(
                    "Session Id:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    sessionid,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val deviceid = call.get("deviceid")
        if(deviceid != null) {
            template.add(
                1,
                TextUnit(
                    "Device Id:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    deviceid,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val devicetype = call.get("devicetype")
        if(devicetype != null) {
            template.add(
                1,
                TextUnit(
                    "Device Type:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    devicetype,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val paymentname = call.get("paymentname")
        if(paymentname != null) {
            template.add(
                1,
                TextUnit(
                    "Payment Name:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    paymentname,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val paymentcode = call.get("paymentcode")
        if(paymentcode != null) {
            template.add(
                1,
                TextUnit(
                    "Payment Code:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    paymentcode,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val Phonenumber = call.get("phonenumber")
        if(Phonenumber != null) {
            template.add(
                1,
                TextUnit(
                    "Phone Number:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    Phonenumber,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val network = call.get("network")
        if(network != null) {
            template.add(
                1,
                TextUnit(
                    "Network:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    network,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val description = call.get("description")
        if(description != null) {
            template.add(
                1,
                TextUnit(
                    "Description:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    description,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val disco = call.get("disco")
        if(disco != null) {
            template.add(
                1,
                TextUnit(
                    "Disco:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    disco,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val meteraccname = call.get("meteraccname")
        if(meteraccname != null) {
            template.add(
                1,
                TextUnit(
                    "Meter Acc Name:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    meteraccname,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val meterno = call.get("meterno")
        if(meterno != null) {
            template.add(
                1,
                TextUnit(
                    "Meter No:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    meterno,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val token = call.get("token")
        if(token != null) {
            template.add(
                1,
                TextUnit(
                    "Token:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    token,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val unit = call.get("unit")
        if(unit != null) {
            template.add(
                1,
                TextUnit(
                    "Unit:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    unit,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val address = call.get("address")
        if(address != null) {
            template.add(
                1,
                TextUnit(
                    "Address:",
                    TextUnit.TextSize.SMALL,
                    Align.LEFT
                )
                    .setBold(true),
                1,
                TextUnit(
                    address,
                    TextUnit.TextSize.SMALL,
                    Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val datetime = call.get("datetime")!!
        template.add(
            1,
            TextUnit(
                "DATE & TIME",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                datetime,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val bottommessage = call.get("bottommessage")!!
        template.add(
            TextUnit(
                bottommessage,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )

        val appversion = call.get("appversion")!!
        template.add(
            TextUnit(
                "App Version " + appversion,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }

    fun starteodPrint(call: Map<String, Any>) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding!!.activity.resources,
//            R.mipmap.ic_launcher
//        )ham@gmail.com
        val base64String: String = call["base64image"].toString()
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding, null)
        template.clear()

        template.add(
            ImageUnit(
                Align.CENTER,
                bitmap,
                550,
                70
            )
        )

        template.add(
            TextUnit(
                "End of Day summary",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call["marchantname"].toString()
        template.add(
            TextUnit(
                marchantname,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call["marchantaddress"].toString()
        template.add(
            TextUnit(
                marchantaddress,
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val datetime: String = call["datetime"].toString()
        template.add(
            1,
            TextUnit(
                "DATE",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                datetime,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(
            1,
            TextUnit(
                "Time",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            2,
            TextUnit(
                "RRN",
                TextUnit.TextSize.SMALL,
                Align.CENTER
            )
                .setBold(true),
            1,
            TextUnit(
                "Amount",
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true),
            1,
            TextUnit(
                "Status",
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )

//        val numbers: Array<Map<String,String>> = call["details"]
//        for (number in numbers) {
//            val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(number.get("amount")) // Format with commas
//            template.add(
//                1,
//                TextUnit(
//                    number.get("created_at"),
//                    TextUnit.TextSize.SMALL,
//                    Align.LEFT
//                )
//                    .setBold(true),
//                2,
//                TextUnit(
//                    number.get("ref"),
//                    TextUnit.TextSize.SMALL,
//                    Align.RIGHT
//                )
//                    .setBold(true),
//                1,
//                TextUnit(
//                    number.get("amount"),
//                    TextUnit.TextSize.SMALL,
//                    Align.RIGHT
//                )
//                    .setBold(true),
//                1,
//                TextUnit(
//                    number.get("status"),
//                    TextUnit.TextSize.SMALL,
//                    Align.RIGHT
//                )
//                    .setBold(true)
//            )
//        }

        val totalapproved: String = call["totalapproved"].toString()
        template.add(
            1,
            TextUnit(
                "Total Approved",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                totalapproved,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val totalfailed: String = call["totalfailed"].toString()
        template.add(
            1,
            TextUnit(
                "Total Failed",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                totalfailed,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val totalcredit: String = call["totalcredit"].toString()
        template.add(
            1,
            TextUnit(
                "Total Credit",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                totalcredit,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        val totaldebit: String = call["totaldebit"].toString()
        template.add(
            1,
            TextUnit(
                "Total Debit",
                TextUnit.TextSize.SMALL,
                Align.LEFT
            )
                .setBold(true),
            1,
            TextUnit(
                totaldebit,
                TextUnit.TextSize.SMALL,
                Align.RIGHT
            )
                .setBold(true)
        )
        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }

    fun startCustomPrint(call: String) {

        Log.e("TAG", "startcustomPrint: $call")
        val printmodel = Printmodel.fromJson(call)
        val template: PrintTemplate = PrintTemplate.instance
        template.init(binding, null)
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
        Log.e("TAG", "startcustomPrint: hello boy")

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
                align(i.align!!), bitmap, bitmap.width, bitmap.height,
            )
        } else if (i.imageheight == null && i.imagewidth != null) {
            ImageUnit(
                align(i.align!!), bitmap, i.imagewidth, bitmap.height,
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


}