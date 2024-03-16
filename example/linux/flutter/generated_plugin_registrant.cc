//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <topwisemp35p/topwisemp35p_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) topwisemp35p_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "Topwisemp35pPlugin");
  topwisemp35p_plugin_register_with_registrar(topwisemp35p_registrar);
}
