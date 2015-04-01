var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onDeepLinkingReady');
channel.waitForInitialization('onDeepLinkingReady');

console.log('before cordova ready');

channel.onCordovaReady.subscribe(function() {
  console.log('after cordova ready');

  var success = function(url) {
    console.log('after success');
    // If url != null, we were launched with an intent
    console.log(url);
    channel.onDeepLinkingReady.fire();
  };
  var fail = function() {
    console.log('after fail');
    channel.onDeepLinkingReady.fire();
  };
  exec(success, fail, "DeepLinking", "registerCallbacks", []);
});

//module.exports;

// TODO: provide an event for app navigation
