var exec = require('cordova/exec');

var PLUGIN_NAME = "DeviceCheck";

var DeviceCheck = function(){};

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'DeviceCheck', 'coolMethod', [arg0]);
};

DeviceCheck.getCPU = function(arg0, onSuccess, onError){
  exec(onSuccess, onError, PLUGIN_NAME, "getCPU", [arg0]);  
};

DeviceCheck.getRAM = function(arg0, onSuccess, onError){
    exec(onSuccess, onError, PLUGIN_NAME, "getRAM", [arg0]);
}

DeviceCheck.getDeviceName = function(arg0, onSuccess, onError){
  exec(onSuccess, onError, PLUGIN_NAME, "getDevice", [arg0]);
}
DeviceCheck.getDeviceSDK = function(arg0, onSuccess, onError){
  exec(onSuccess, onError, PLUGIN_NAME, 'getDeviceSDK', [arg0]);
}

module.exports = DeviceCheck;
