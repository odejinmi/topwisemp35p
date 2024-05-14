package com.a5starcompany.topwisemp35p

import androidx.annotation.NonNull
import com.a5starcompany.topwisemp35p.MethodCallHandlerImpl

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger


/** Topwisemp35pPlugin */
class Topwisemp35pPlugin: FlutterPlugin, ActivityAware {
  private var methodCallHandler: MethodCallHandlerImpl? = null
  private var pluginBinding: FlutterPlugin.FlutterPluginBinding? = null


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    pluginBinding = flutterPluginBinding
  }


  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    pluginBinding = null
  }


  /**
   * It initialises the [methodCallHandler]
   */
  private fun initializeMethodHandler(messenger: BinaryMessenger?, binding: ActivityPluginBinding) {
    methodCallHandler = MethodCallHandlerImpl(messenger,  binding)

  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    initializeMethodHandler(pluginBinding?.binaryMessenger,  binding)


  }

  override fun onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity()
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    methodCallHandler?.dispose()
    methodCallHandler = null

  }

}
