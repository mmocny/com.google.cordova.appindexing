var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onAppIndexingReady');
channel.waitForInitialization('onAppIndexingReady');

console.log('before cordova ready');

channel.onCordovaReady.subscribe(function() {
  console.log('after cordova ready');

  var success = function(url) {
    console.log('after success');
    console.log(url);
    // If url != null, we were launched with an intent
    channel.onAppIndexingReady.fire();
  };
  var fail = function() {
    console.log('after fail');
    channel.onAppIndexingReady.fire();
  };
  exec(success, fail, "AppIndexing", "registerCallbacks", []);
});

//module.exports;

// TODO: provide an event for app navigation
