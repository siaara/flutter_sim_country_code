package com.example.flutter_sim_country_code;

import android.content.Context;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterSimCountryCodePlugin */
class FlutterSimCountryCodePlugin : FlutterPlugin, MethodCallHandler {
  private lateinit var channel: MethodChannel
  private var context: Context? = null

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "getSimCountryCode" -> getSimCountryCode(result)
      else -> result.notImplemented()
    }
  }

  fun getSimCountryCode(@NonNull result: Result) {
     var manager: TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
     if (manager != null) {
       var countryId: String = manager.getSimCountryIso()
       if (countryId != null) {
         result.success(countryId.uppercase())
         return
       }
     }
     result.error("SIM_COUNTRY_CODE_ERROR", null, null)
  }


override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_sim_country_code")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    context = null
  }
}
