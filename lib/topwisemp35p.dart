
import 'package:flutter/material.dart';
import 'package:topwisemp35p/transaction_monitor.dart';

import 'print.dart';
import 'topwisemp35p_platform_interface.dart';

class Topwisemp35p {
  Future<String?> getPlatformVersion() {
    return Topwisemp35pPlatform.instance.getPlatformVersion();
  }

  Future<String?> deviceserialnumber() {
    return Topwisemp35pPlatform.instance.deviceserialnumber();
  }

  Future<TransactionMonitor> initialize(String amount) async {
    return Topwisemp35pPlatform.instance.debitcard(amount);
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
