// MCC and MNC codes on Wikipedia
// http://en.wikipedia.org/wiki/Mobile_country_code

// Mobile Network Codes (MNC) for the international identification plan for public networks and subscriptions
// http://www.itu.int/pub/T-SP-E.212B-2014

// class TelephonyManager
// http://developer.android.com/reference/android/telephony/TelephonyManager.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/TelephonyManager.java

// permissions
// http://developer.android.com/training/permissions/requesting.html

// Cordova Permissions API
// https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#android-permissions

package com.pbakondy;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;

import android.telephony.TelephonyManager;

public class Sim extends CordovaPlugin {
  private static final String GET_SIM_INFO = "getSimInfo";

  private CallbackContext callback;

  @SuppressLint("HardwareIds")
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    callback = callbackContext;

    if (GET_SIM_INFO.equals(action)) {
      Context context = this.cordova.getActivity().getApplicationContext();
      TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      String countryCode = manager.getSimCountryIso();
      String simOperator = manager.getSimOperator();
      String carrierName = manager.getSimOperatorName();
      int callState = manager.getCallState();
      int dataActivity = manager.getDataActivity();
      int networkType = manager.getNetworkType();
      int phoneType = manager.getPhoneType();
      int simState = manager.getSimState();
      boolean isNetworkRoaming = manager.isNetworkRoaming();
      String mcc = "";
      String mnc = "";
      if (simOperator.length() >= 3) {
        mcc = simOperator.substring(0, 3);
        mnc = simOperator.substring(3);
      }
      JSONObject result = new JSONObject();
      result.put("carrierName", carrierName);
      result.put("countryCode", countryCode);
      result.put("mcc", mcc);
      result.put("mnc", mnc);
      result.put("callState", callState);
      result.put("dataActivity", dataActivity);
      result.put("networkType", networkType);
      result.put("phoneType", phoneType);
      result.put("simState", simState);
      result.put("isNetworkRoaming", isNetworkRoaming);
      callbackContext.success(result);
      return true;
    } else {
      return false;
    }
  }
}
