#include "include/topwisemp35p/topwisemp35p_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "topwisemp35p_plugin.h"

void Topwisemp35pPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  topwisemp35p::Topwisemp35pPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
