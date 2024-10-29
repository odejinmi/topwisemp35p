import 'package:flutter/src/foundation/basic_types.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:topwisemp35p/print.dart';
import 'package:topwisemp35p/topwisemp35p.dart';
import 'package:topwisemp35p/topwisemp35p_platform_interface.dart';
import 'package:topwisemp35p/topwisemp35p_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:topwisemp35p/transaction_monitor.dart';

class MockTopwisemp35pPlatform
    with MockPlatformInterfaceMixin
    implements Topwisemp35pPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  void cancelcardprocess() {
    // TODO: implement cancelcardprocess
  }

  @override
  void debitcard(String amount) {
    // TODO: implement debitcard
  }

  @override
  Future<String> deviceserialnumber() {
    // TODO: implement deviceserialnumber
    throw UnimplementedError();
  }

  @override
  void enterpin(String amount) {
    // TODO: implement enterpin
  }

  @override
  Future<TransactionMonitor> getcardsheme(String amount) {
    // TODO: implement getcardsheme
    throw UnimplementedError();
  }

  @override
  Future<TransactionMonitor> printreceipt(Print print) {
    // TODO: implement printreceipt
    throw UnimplementedError();
  }

  @override
  Future<TransactionMonitor> startcustomprinting(List<Widget> template) {
    // TODO: implement startcustomprinting
    throw UnimplementedError();
  }

  @override
  Future<TransactionMonitor> starteodPrint(Map<String, dynamic> template) {
    // TODO: implement starteodPrint
    throw UnimplementedError();
  }

  @override
  void startkeyboard({ValueChanged<String>? onchange, Function? proceed, Function? cancel}) {
    // TODO: implement startkeyboard
  }

  @override
  Future<TransactionMonitor> startprinting(Print print) {
    // TODO: implement startprinting
    throw UnimplementedError();
  }

  @override
  Future<TransactionMonitor> startprintjson(List<Map<String, Object>> template) {
    // TODO: implement startprintjson
    throw UnimplementedError();
  }

  @override
  // TODO: implement stateStream
  Stream get stateStream => throw UnimplementedError();

  @override
  void stopkeyboard() {
    // TODO: implement stopkeyboard
  }
}

void main() {
  final Topwisemp35pPlatform initialPlatform = Topwisemp35pPlatform.instance;

  test('$MethodChannelTopwisemp35p is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTopwisemp35p>());
  });

  test('getPlatformVersion', () async {
    Topwisemp35p topwisemp35pPlugin = Topwisemp35p();
    MockTopwisemp35pPlatform fakePlatform = MockTopwisemp35pPlatform();
    Topwisemp35pPlatform.instance = fakePlatform;

    expect(await topwisemp35pPlugin.getPlatformVersion(), '42');
  });
}
