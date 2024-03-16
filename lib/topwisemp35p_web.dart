// In order to *not* need this ignore, consider extracting the "web" version
// of your plugin as a separate package, instead of inlining it in the same
// package as the core of your plugin.
// ignore: avoid_web_libraries_in_flutter
import 'dart:html' as html show window;

import 'package:flutter_web_plugins/flutter_web_plugins.dart';

import 'topwisemp35p_platform_interface.dart';

/// A web implementation of the Topwisemp35pPlatform of the Topwisemp35p plugin.
class Topwisemp35pWeb extends Topwisemp35pPlatform {
  /// Constructs a Topwisemp35pWeb
  Topwisemp35pWeb();

  static void registerWith(Registrar registrar) {
    Topwisemp35pPlatform.instance = Topwisemp35pWeb();
  }

  /// Returns a [String] containing the version of the platform.
  @override
  Future<String?> getPlatformVersion() async {
    final version = html.window.navigator.userAgent;
    return version;
  }
}
