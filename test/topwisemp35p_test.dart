import 'package:flutter_test/flutter_test.dart';
import 'package:topwisemp35p/topwisemp35p.dart';
import 'package:topwisemp35p/topwisemp35p_platform_interface.dart';
import 'package:topwisemp35p/topwisemp35p_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTopwisemp35pPlatform
    with MockPlatformInterfaceMixin
    implements Topwisemp35pPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
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
