import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:topwisemp35p/print.dart';
import 'package:topwisemp35p/topwisemp35p.dart';
import 'package:topwisemp35p_example/receipt.dart';

import 'eod.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _topwisemp35pPlugin = Topwisemp35p();

  @override
  void initState() {
    super.initState();
    initPlatformState();
    start();
  }

  late String base64string;

  Future<void> start() async {
    final ByteData assetByteData = await rootBundle.load("asset/logo2.png");
    final Uint8List imagebytes = assetByteData.buffer.asUint8List();
    base64string = base64.encode(imagebytes); //convert bytes to base64 string
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await _topwisemp35pPlugin.deviceserialnumber() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            ElevatedButton(
              onPressed: () {
                _topwisemp35pPlugin.initialize("200").then((value) { print(value); });
              },
              child: const Text("start transaction"),

            ),
            ElevatedButton(
              onPressed: () {
                var args = Print(
                  base64image: base64string,
                  marchantname: "VERDANT MICROFINANCE BANK",
                  datetime: "27 Jan 2023,06:55AM",
                  terminalid: "2LUX4199",
                  merchantid: "2LUXAA00000001",
                  transactiontype: "CARD WITHDRAWAL",
                  copytype: "Merchant",
                  rrn: "561409897476",
                  stan: "904165",
                  pan: "539983******1954",
                  expiry: "2303",
                  transactionstatus: "DECLINED",
                  responsecode: "55",
                  message: "Incorrect PIN",
                  appversion: "1.5.3",
                  amount: "200",
                  bottommessage: "Buy Airtime and Pay Electricity bills here anytime!    AnyDAY!", marchantaddress: '', serialno: '',
                );
                _topwisemp35pPlugin.startprinting(args).then((value) {print(value);});
              },
              child: const Text("print withdraw"),
            ),
            ElevatedButton(
              onPressed: () {
                var args = Print(
                  rrn:"gfhj",
                  pan:"fsdgs",
                  expiry:"fdfs",
                  base64image: base64string,
                  marchantname: "VERDANT MICROFINANCE BANK",
                  datetime: "27 Jan 2023,06:55AM",
                  terminalid: "2LUX4199",
                  merchantid: "2LUXAA00000001",
                  transactiontype: "CARD WITHDRAWAL",
                  accountname: "ODEJINMI TOLUWALOPE ABRAHAM",
                  copytype: "Merchant",
                  stan: "904165",
                  accountnumber: "3076302098",
                  bank: "First Bank",
                  transactionstatus: "DECLINED",
                  responsecode: "55",
                  message: "Incorrect PIN",
                  appversion: "1.5.3",
                  amount: "200",
                  bottommessage: "Buy Airtime and Pay Electricity bills here anytime!    AnyDAY!", marchantaddress: '', serialno: '',
                );
                _topwisemp35pPlugin.startprinting(args).then((value) {print(value);});
              },
              child: const Text("print transfer"),
            ),
            ElevatedButton(
              onPressed: () async {

                // var printvalue = [
                //   {"image":base64string,"align":"center","imagewidth":30, "imageheight":30},
                //   {"text":[{"text":"MERCHANT NAME", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"Tolulope", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"DATE/TIME", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"TERMINAL ID", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"MERCHANT ID", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"*****************************************************"}]},
                //   {"text":[{"text":"transactiontype", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"accountname", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"Customer Copy", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"*****************************************************"}]},
                //   {"text":[{"text":"RRN:", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"STAN:", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"PAN:", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"CARD EXPIRY", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"ACCOUNT NUMBER:", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"BANK", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"transactionstatus", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"Response code", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"Message", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"App Version", "textsize":"normal","align":"left"},{"text":"tolulope", "textsize":"normal","align":"right"}]},
                //   {"text":[{"text":"", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"*****************************************************"}]},
                //   {"text":[{"text":"₦2000.00", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"*****************************************************"}]},
                //   {"text":[{"text":"bottommessage", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"*****************************************************"}]},
                //   {"text":[{"text":"", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"", "textsize":"normal","align":"center"}]},
                //   {"text":[{"text":"", "textsize":"normal","align":"center"}]},
                // ];
                // final List<Printmodel> printmodel = printmodelFromJson(jsonEncode(printvalue));
                // _topwisemp35pPlugin.startcustomprinting(printmodel).then((value) {print(value);});
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Receipt()),
                );
              },
              child: const Text("custom printing"),
            ),
            ElevatedButton(
              onPressed: () async {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const Eod()),
                );
              },
              child: const Text("Eod printing"),
            ),
          ],
        ),
      ),
    );
  }
}
