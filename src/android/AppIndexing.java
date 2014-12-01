package com.google.cordova.appindexing;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaActivity;

import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AppIndexing extends CordovaPlugin {
    public static final String LOG_TAG = "AppIndexing";

    public static final String INTENT_TYPE = "android.intent.action.VIEW";
    public static final String SCHEME_TYPE = "android-app";

    /**
     * Constructor.
     */
    public AppIndexing() {
      Log.e(LOG_TAG, " XX Plugin Constructor");
      // TODO: Can we prevent first flash of content by changing start url?
    }

    /**
     * Called after plugin construction and fields have been initialized.
     */
    @Override
    protected void pluginInitialize() {
      Log.e(LOG_TAG, " XX Plugin Initialize");
    }

    /**
     * Called when the WebView does a top-level navigation or refreshes.
     *
     * Plugins should stop any long-running processes and clean up internal state.
     *
     * Does nothing by default.
     */
    @Override
    public void onReset() {
      Log.e(LOG_TAG, " XX Plugin Reset");

      Intent intent = cordova.getActivity().getIntent();
      Uri uri = getDeepLinkFromIntent(intent);
      if (uri != null) {
        navigateTo(uri);
      }
    }

    /**
     * The final call you receive before your activity is destroyed.
     */
    @Override
    public void onDestroy() {
      Log.e(LOG_TAG, " XX Plugin Destroy");
    }

    /**
     * Executes the request.
     *
     * This method is called from the WebView thread. To do a non-trivial amount of work, use:
     *     cordova.getThreadPool().execute(runnable);
     *
     * To run on the UI thread, use:
     *     cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments, wrapped with some Cordova helpers.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return                Whether the action was valid.
     */
    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("registerCallbacks")) {
        registerCallbacks(args, callbackContext);
      } else {
        return false;
      }
      return true;
    }

    /**
     * Called when the activity receives a new intent.
     */
    @Override
    public void onNewIntent(Intent intent) {
      Log.e(LOG_TAG, " XX NEW INTENT");

      Uri uri = getDeepLinkFromIntent(intent);
      if (uri != null) {
        navigateTo(uri);
      }

    }


    private void registerCallbacks(CordovaArgs args, CallbackContext callbackContext) {
      callbackContext.success();

      Intent intent = cordova.getActivity().getIntent();
      Uri uri = getDeepLinkFromIntent(intent);
      if (uri != null) {
        navigateTo(uri);
      }
    }

    private Uri getDeepLinkFromIntent(Intent intent) {
      if (intent == null || intent.getAction() != INTENT_TYPE) {
        // TODO: any other signals for deep links?
        return null;
      }
      final Uri data = intent.getData();
      /*
      if (data.getScheme() != SCHEME_TYPE) { // TODO: scheme packageID format?
        return null;
      }
      */
      return data;
    }

    private void navigateTo(final Uri uri) {
      /*
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          //webView.loadUrl("#" + data.getEncodedFragment());
          webView.sendJavascript("location.hash = '" + uri.getEncodedFragment() + "'"); // TODO: .getFragment?
        }
      });
      */
      webView.sendJavascript("location.hash = '" + uri.getEncodedFragment() + "'");
    }
}

