import 'dart:async';

import 'package:flutter/services.dart';

class FlutterVolteoPlugin {
  static const MethodChannel _channel =
      const MethodChannel('flutter_volteo_plugin');

  static const EventChannel stream = const EventChannel('data_stream');
  static const EventChannel rssi_stream = const EventChannel('rssi_stream');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> init() async {
    FlutterVolteoPlugin.startDiscovery();
    return await FlutterVolteoPlugin.getReaderStatus();
  }

  static void startDiscovery() async {
    await _channel.invokeMethod('startDiscovery');
  }

  static Future<List<DeviceDescription>> availableDevices() async {
    try {
      final List<dynamic> devices =
          await _channel.invokeMethod('getDevicesList');

      return devices.map((dynamic device) {
        return DeviceDescription(
            name: device['name'], address: device['address']);
      }).toList();
    } catch (e) {
      print(e.message);
      return <DeviceDescription>[];
    }
  }

  static void disconnect() async {
    final String msg = await _channel.invokeMethod("disconnect");
    print("MSGGG-> $msg");
  }

  static Future<Map<dynamic, dynamic>> newDeviceList() async {
    final Map<dynamic, dynamic> msg =
        await _channel.invokeMethod("newDevicesList");
    return msg;
  }

  static void cancelDiscovery() async {
    final String msg = await _channel.invokeMethod("cancelDiscovery");
    print("MSGGG-> $msg");
  }

  static Future<String> connectToDevice(String deviceAddress) async {
    try {
      return await _channel
          .invokeMethod("connectToDevice", {"address": deviceAddress});
    } catch (e) {
      print(e.message);
      return e.message;
    }
  }

  static Future<String> getReaderStatus() async {
    try {
      return await _channel.invokeMethod("getReaderStatus");
    } catch (e) {
      print(e.message);
      return e.message;
    }
  }

  static void startReceiving() async {
    final String msg = await _channel.invokeMethod("startReceiving");
    print("MSGGG-> $msg");
    //getReceivedMsg();
  }

  static void stopReceiving() async {
    final String msg = await _channel.invokeMethod("stopReceiving");
    print("MSGGG-> $msg");
  }

  static void getReceivedMsg() async {
    final Map<dynamic, dynamic> msg =
        await _channel.invokeMethod("getReceivedMsg");
    print("MSGGG-> $msg");
  }

  static void dispose() async {
    final String msg = await _channel.invokeMethod("dispose");
    print("MSGGG-> $msg");
  }

  static Future<int> getPowerBarMax() async {
    return await _channel.invokeMethod("getPowerBarMax");
  }

  static Future<int> getPowerBarLimits() async {
    return await _channel.invokeMethod("getPowerBarLimits");
  }

  static Future<int> mPowerLevel(int progress) async {
    return await _channel.invokeMethod("mPowerLevel", {"progress": progress});
  }

  static Future<int> setmDatabanks(int index) async {
    return await _channel.invokeMethod("setmDatabanks", {"index": index});
  }

  static Future<String> read() async {
    return await _channel.invokeMethod("read");
  }

  static Future<String> write() async {
    return await _channel.invokeMethod("write");
  }

  static Future<String> mTargetTagEditTextChangedListener() async {
    return await _channel.invokeMethod("mTargetTagEditTextChangedListener");
  }

  static Future<String> mWordAddressEditTextChangedListener() async {
    return await _channel.invokeMethod("mWordAddressEditTextChangedListener");
  }

  static Future<String> mWordCountEditTextChangedListener() async {
    return await _channel.invokeMethod("mWordCountEditTextChangedListener");
  }

  static Future<String> mDataEditTextChangedListener() async {
    return await _channel.invokeMethod("mDataEditTextChangedListener");
  }
}

class DeviceDescription {
  DeviceDescription({this.name, this.address});
  final String name, address;
}
