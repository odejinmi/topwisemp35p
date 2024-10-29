
import 'package:flutter/material.dart';
import 'package:topwisemp35p/transaction_monitor.dart';
export 'package:topwisemp35p/transaction_monitor.dart';

import 'print.dart';
import 'topwisemp35p_platform_interface.dart';

class Topwisemp35p {
  Stream<dynamic> get stateStream {
    return Topwisemp35pPlatform.instance.stateStream;
  }
  Future<String?> getPlatformVersion() {
    return Topwisemp35pPlatform.instance.getPlatformVersion();
  }

  Future<String?> deviceserialnumber() {
    return Topwisemp35pPlatform.instance.deviceserialnumber();
  }

  void initialize() async {
    Topwisemp35pPlatform.instance.initialize();
  }

 void debitcard(String amount) async {
    Topwisemp35pPlatform.instance.debitcard(amount);
  }

 void enterpin(String amount) async {
    return Topwisemp35pPlatform.instance.enterpin(amount);
  }

 void cancelcardprocess() async {
    return Topwisemp35pPlatform.instance.cancelcardprocess();
  }

 void startkeyboard({ValueChanged<String>? onchange,
   Function? proceed,
   Function? cancel}) async {
    return Topwisemp35pPlatform.instance.startkeyboard(onchange: onchange,proceed: proceed, cancel: cancel);
  }

 void stopkeyboard() async {
    return Topwisemp35pPlatform.instance.stopkeyboard();
  }

 Future<TransactionMonitor> getcardsheme(String amount) async {
    return Topwisemp35pPlatform.instance.getcardsheme(amount);
  }

  Future<TransactionMonitor> startprinting (Print print) async {
    return Topwisemp35pPlatform.instance.startprinting(print);
  }
  Future<TransactionMonitor> starteodPrint (Map<String, dynamic> template) async {
    return Topwisemp35pPlatform.instance.starteodPrint(template);
  }
  Future<TransactionMonitor> startprintjson (List<Map<String, Object>> template) async {
    return Topwisemp35pPlatform.instance.startprintjson(template);
  }
  Future<TransactionMonitor> startcustomprinting (List<Widget> template) async {
    return Topwisemp35pPlatform.instance.startcustomprinting(template);
  }
}
