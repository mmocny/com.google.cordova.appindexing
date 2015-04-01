package org.apache.cordova.appdeeplinking;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DeepLinking extends CordovaPlugin {
    public static final String LOG_TAG = "DeepLinking";

    public static final String INTENT_TYPE = "android.intent.action.VIEW";
    public static final String SCHEME_TYPE = "android-app";

    public DeepLinking() {
    }

    @Override
    protected void pluginInitialize() {
    }

    @Override
    public void onReset() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("registerCallbacks")) {
        registerCallbacks(args, callbackContext);
      } else {
        return false;
      }
      return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
      String uri = getDeepLinkFromIntent(intent);
      if (uri != null) {
        navigateTo(uri);
      }
    }


    private void registerCallbacks(CordovaArgs args, CallbackContext callbackContext) {
      // Application may have started with an intent
      // TODO: Can we prevent first flash of content by changing start url?
      Intent intent = cordova.getActivity().getIntent();
      String uri = getDeepLinkFromIntent(intent);

      PluginResult result = new PluginResult(PluginResult.Status.OK, uri);
      result.setKeepCallback(true);
      callbackContext.sendPluginResult(result);

      // Hack for startup:
      if (uri != null) {
        navigateTo(uri);
      }
    }

    private String getDeepLinkFromIntent(Intent intent) {
      if (intent == null || !intent.getAction().equals(INTENT_TYPE)) {
        return null;
      }
      final Uri uri = intent.getData();

      // TODO: support scheme packageID format, current only supports android-app:// format
      if (!uri.getScheme().equals(SCHEME_TYPE)) {
        return null;
      }

      // TODO: get everything after the www/ for multi page apps? / history.setState?
      return uri.getEncodedFragment();
    }

    private void navigateTo(final String uri) {
      // TODO: support changes to more than just hash!
      webView.sendJavascript("location.hash = '" + uri + "'");
    }
}

