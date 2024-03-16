#ifndef FLUTTER_PLUGIN_TOPWISEMP35P_PLUGIN_H_
#define FLUTTER_PLUGIN_TOPWISEMP35P_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace topwisemp35p {

class Topwisemp35pPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  Topwisemp35pPlugin();

  virtual ~Topwisemp35pPlugin();

  // Disallow copy and assign.
  Topwisemp35pPlugin(const Topwisemp35pPlugin&) = delete;
  Topwisemp35pPlugin& operator=(const Topwisemp35pPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace topwisemp35p

#endif  // FLUTTER_PLUGIN_TOPWISEMP35P_PLUGIN_H_
