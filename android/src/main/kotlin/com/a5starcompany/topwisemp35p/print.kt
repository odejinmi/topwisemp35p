package com.a5starcompany.topwisemp35p

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.a5starcompany.topwisemp35p.emvreader.printer.*
import com.lonytech.topwisesdk.emvreader.TopWiseDevice
import com.lonytech.topwisesdk.emvreader.emv.TransactionMonitor
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import java.text.NumberFormat
import java.util.Locale

class print(val topWiseDevice : TopWiseDevice, val binding: ActivityPluginBinding) {


    fun startPrint(call: MethodCall) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding!!.activity.resources,
//            R.mipmap.ic_launcher
//        )
        val base64String: String = call.argument<String>("base64image")!!
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: com.lonytech.topwisesdk.emvreader.printer.PrintTemplate = com.lonytech.topwisesdk.emvreader.printer.PrintTemplate.instance
        template.init(binding!!.activity, null)
        template.clear()

        template.add(
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER,
                bitmap,
                550,
                70
            )
        )

        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "TRANSACTION RECEIPT",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )

        val copytype = call.argument<String>("copytype")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                copytype + " Copy",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call.argument<String>("marchantname")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                marchantname,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call.argument<String>("marchantaddress")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                marchantaddress,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val transactiontype = call.argument<String>("transactiontype")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                transactiontype,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )

        val amount = call.argument<String>("amount")!!.toDouble() // Convert to Double for formatting
        val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(amount) // Format with commas
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "₦$formattedAmount",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.NORMAL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        val transactionstatus = call.argument<String>("transactionstatus")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                transactionstatus,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val serialno = call.argument<String>("serialno")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Serial No",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                serialno,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val terminalid = call.argument<String>("terminalid")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Terminal ID",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                terminalid,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val rrn = call.argument<String>("rrn")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "RRN:",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                rrn,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val pan = call.argument<String>("pan")
        if(pan != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Card No:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    pan,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val stan = call.argument<String>("stan")
        if(stan != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "STAN:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    stan,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val message = call.argument<String>("message")
        if(message != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Message:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    message,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessaccountname = call.argument<String>("businessaccountname")
        if(businessaccountname != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Business Account Name:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    businessaccountname,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessaccountnumber = call.argument<String>("businessaccountnumber")
        if(businessaccountnumber != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Business Account Number:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    stan,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val businessbank = call.argument<String>("businessbank")
        if(businessbank != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Business Bank:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    stan,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val accname = call.argument<String>("accountname")
        if(accname != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Acc Name:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    accname,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val accno = call.argument<String>("accountnumber")
        if(accno != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Acc No:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    accno,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val bankname = call.argument<String>("bank")
        if(bankname != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Bank Name:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    bankname,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val sessionid = call.argument<String>("sessionid")
        if(sessionid != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Session Id:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    sessionid,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val deviceid = call.argument<String>("deviceid")
        if(deviceid != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Device Id:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    deviceid,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val devicetype = call.argument<String>("devicetype")
        if(devicetype != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Device Type:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    devicetype,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val paymentname = call.argument<String>("paymentname")
        if(paymentname != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Payment Name:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    paymentname,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val paymentcode = call.argument<String>("paymentcode")
        if(paymentcode != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Payment Code:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    paymentcode,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val Phonenumber = call.argument<String>("phonenumber")
        if(Phonenumber != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Phone Number:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    Phonenumber,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val network = call.argument<String>("network")
        if(network != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Network:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    network,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val description = call.argument<String>("description")
        if(description != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Description:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    description,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val disco = call.argument<String>("disco")
        if(disco != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Disco:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    disco,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val meteraccname = call.argument<String>("meteraccname")
        if(meteraccname != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Meter Acc Name:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    meteraccname,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val meterno = call.argument<String>("meterno")
        if(meterno != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Meter No:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    meterno,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val token = call.argument<String>("token")
        if(token != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Token:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    token,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val unit = call.argument<String>("unit")
        if(unit != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Unit:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    unit,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val address = call.argument<String>("address")
        if(address != null) {
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "Address:",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    address,
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }
        val datetime = call.argument<String>("datetime")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "DATE & TIME",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                datetime,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val bottommessage = call.argument<String>("bottommessage")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                bottommessage,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )

        val appversion = call.argument<String>("appversion")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "App Version " + appversion,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }
    fun starteodPrint(call: MethodCall) {

        Log.e("TAG", "startcustomPrint: ${call}")

//        val bitmap: Bitmap = BitmapFactory.decodeResource(
//            binding!!.activity.resources,
//            R.mipmap.ic_launcher
//        )ham@gmail.com
        val base64String: String = call.argument<String>("base64image")!!
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val template: com.lonytech.topwisesdk.emvreader.printer.PrintTemplate = com.lonytech.topwisesdk.emvreader.printer.PrintTemplate.instance
        template.init(binding!!.activity, null)
        template.clear()

        template.add(
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER,
                bitmap,
                550,
                70
            )
        )

        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "End of Day summary",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        val marchantname: String = call.argument<String>("marchantname")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                marchantname,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        val marchantaddress: String = call.argument<String>("marchantaddress")!!
        template.add(
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                marchantaddress,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true)
        )
        template.add(starttextUnit())
        val datetime = call.argument<String>("datetime")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "DATE",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                datetime,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        template.add(starttextUnit())

        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Time",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            2,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "RRN",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Amount",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Status",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )

        val numbers = call.argument<List<MutableMap<String, String>>>("details")!!
        for (number in numbers) {
            val formattedAmount = NumberFormat.getNumberInstance(Locale.US).format(number.get("amount")) // Format with commas
            template.add(
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    number.get("created_at"),
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
                )
                    .setBold(true),
                2,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    number.get("ref"),
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    number.get("amount"),
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true),
                1,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    number.get("status"),
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
                )
                    .setBold(true)
            )
        }

        val totalapproved = call.argument<String>("totalapproved")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Total Approved",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                totalapproved,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val totalfailed = call.argument<String>("totalfailed")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Total Failed",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                totalfailed,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val totalcredit = call.argument<String>("totalcredit")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Total Credit",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                totalcredit,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        val totaldebit = call.argument<String>("totaldebit")!!
        template.add(
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                "Total Debit",
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            )
                .setBold(true),
            1,
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                totaldebit,
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            )
                .setBold(true)
        )
        template.add(spacetextUnit())

        topWiseDevice.printDoc(template) //perform print operation

    }

    fun startCustomPrint(call: MethodCall) {

        val marchantname = call.argument<String>("textprint")
        Log.e("TAG", "startcustomPrint: $marchantname")
        val printmodel = Printmodel.fromJson(marchantname!!)
        val template: com.lonytech.topwisesdk.emvreader.printer.PrintTemplate = com.lonytech.topwisesdk.emvreader.printer.PrintTemplate.instance
        template.init(binding!!.activity, null)
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
                com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                    "",
                    com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
                    com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
                )
            )
        }

        topWiseDevice.printDoc(template) //perform print operation


    }

    private fun imageUnit(i: Datum): com.lonytech.topwisesdk.emvreader.printer.ImageUnit {
        val base64String = i.image
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return if (i.imageheight == null && i.imagewidth == null) {
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                align(i.align!!), bitmap, bitmap.width, bitmap.height,
            )
        } else if (i.imageheight == null && i.imagewidth != null) {
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                align(i.align!!), bitmap, i.imagewidth, bitmap.height,
            )
        } else if (i.imageheight != null && i.imagewidth == null) {
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                align(i.align!!), bitmap, bitmap.width, i.imageheight,
            )
        } else {
            com.lonytech.topwisesdk.emvreader.printer.ImageUnit(
                align(i.align!!), bitmap, i.imagewidth!!, i.imageheight!!,
            )

        }

    }


    private fun textUnit(text: Datum): com.lonytech.topwisesdk.emvreader.printer.TextUnit {
        return if (text.align == null) {
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(text.text)
        } else {
            com.lonytech.topwisesdk.emvreader.printer.TextUnit(
                text.text,
                textSize(text.textsize!!),
                align(text.align)
            )

        }
            .setBold(text.bold == true)
            .setWordWrap(text.textwrap == true)

    }
    private fun starttextUnit(): com.lonytech.topwisesdk.emvreader.printer.TextUnit {
        return com.lonytech.topwisesdk.emvreader.printer.TextUnit("*****************************************************")
            .setWordWrap(false)
            .setBold(true)

    }

    private fun spacetextUnit(): com.lonytech.topwisesdk.emvreader.printer.TextUnit {
        return com.lonytech.topwisesdk.emvreader.printer.TextUnit(
            "\n\n\n\n\n",
            com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL,
            com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
        )

    }

    private fun align(align: String): com.lonytech.topwisesdk.emvreader.printer.Align {
        return when (align) {
            "center" -> {
                com.lonytech.topwisesdk.emvreader.printer.Align.CENTER
            }

            "right" -> {
                com.lonytech.topwisesdk.emvreader.printer.Align.RIGHT
            }

            else -> {
                com.lonytech.topwisesdk.emvreader.printer.Align.LEFT
            }
        }
    }

    private fun textSize(align: String): Int {
        return when (align) {
            "normal" -> {
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.NORMAL
            }

            "large" -> {
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.LARGE
            }

            "small" -> {
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.SMALL
            }

            else -> {
                com.lonytech.topwisesdk.emvreader.printer.TextUnit.TextSize.XLARGE
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