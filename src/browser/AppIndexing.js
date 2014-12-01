var browser = require('cordova/platform');
var cordova = require('cordova');

module.exports = {
  test: function (success, error) {
    setTimeout(function () {
      success();
    }, 0);
  }
};

require("cordova/exec/proxy").add("AppIndexing", module.exports);
