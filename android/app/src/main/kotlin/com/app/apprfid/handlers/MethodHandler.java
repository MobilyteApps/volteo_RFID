package com.app.apprfid.handlers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.os.Build;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.app.apprfid.FlutterVolteoPlugin;
import com.app.apprfid.GlobalStorage;
import com.app.apprfid.asciiprotocol.AsciiCommander;
import com.app.apprfid.util.General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MethodHandler implements MethodChannel.MethodCallHandler {
private String TAG="MethodHandler";
    MethodChannel.Result result;
    MethodCall call;
    AsciiCommander commander;
    BluetoothAdapter mBtAdapter;
    Activity context;
    public MethodHandler(AsciiCommander commander,BluetoothAdapter bluetoothAdapter,Activity context){
        General.easyPrinter("MethodHandler",TAG,"Constructor");
        this.commander=commander;
        this.mBtAdapter=bluetoothAdapter;
        this.context=context;
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        General.easyPrinter("onMethodCall",TAG,call.method);
        this.result = result;
        this.call = call;
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("init")) {

            if (this.commander != null) {
                this.result.success("Done");
            } else {
                result.success("Unable to Init Commander");
            }
        } else if (call.method.equals("connect")) {
            if (this.commander != null) {
                this.result.success("Connected");
            } else {
                this.result.success("Commander is NUll,not initialized");
            }
        } else if (call.method.equals("disconnect")) {
            if (this.commander != null) {
                this.commander.disconnect();
            } else {
                this.result.error("Commander is NUll,not initialized", null, null);
            }
        } else if (call.method.equals("getDevicesList")) {

            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            List<Map<String, Object>> devices = new ArrayList<>();

            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", bluetoothDevice.getName());
                map.put("address", bluetoothDevice.getAddress());
                devices.add(map);
            }

            for (BluetoothDevice bluetoothDevice : GlobalStorage.getInstance().getDeviceList()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", bluetoothDevice.getName());
                map.put("address", bluetoothDevice.getAddress());
                devices.add(map);
            }

            this.result.success(devices);

        } else if (call.method.equals("newDevicesList")) {

        } else if (call.method.equals("cancelDiscovery")) {

            if (mBtAdapter.isDiscovering())
                mBtAdapter.cancelDiscovery();

            context.unregisterReceiver(GlobalStorage.getInstance().getBTListBR());

        } else if (call.method.equals("startDiscovery")) {

            System.out.println("FlutterVolteoPlugin.onMethodCall--> START DISCOVERING");
            if (mBtAdapter.isDiscovering()) {
                context.unregisterReceiver(GlobalStorage.getInstance().getBTListBR());
                mBtAdapter.cancelDiscovery();
            }
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            mBtAdapter.startDiscovery();
            context.registerReceiver(GlobalStorage.getInstance().getBTListBR(), filter);

        } else if (call.method.equals("connectToDevice")) {

            String address = call.argument("address");
            General.easyPrinter("onMethodCall",TAG,address);

            BluetoothDevice mDevice = mBtAdapter.getRemoteDevice(address);
            if (mDevice != null) {
                this.commander.connect(mDevice);
                General.easyPrinter("onMethodCall",TAG,"connected");
                FlutterVolteoPlugin.setUpModel();
                this.result.success(address);

            }
     }
        else if (call.method.equals("getReaderStatus")) {

            String connectionMsg = "";
            switch (this.commander.getConnectionState()) {
                case CONNECTED:
                    connectionMsg += this.commander.getConnectedDeviceName();

                    break;
                case CONNECTING:
                    break;
                default:

            }
            this.result.success(connectionMsg);

        } else if (call.method.equals("stopReceiving")) {
//            LocalBroadcastManager.getInstance(registrar.context()).unregisterReceiver(mCommanderMessageReceiver);

        } else if (call.method.equals("dispose")) {
//            registrar.activity().unregisterReceiver(mReceiver);
//            registrar.activity().unregisterReceiver(mBlueToothReceiver);

            //LocalBroadcastManager.getInstance(registrar.context()).unregisterReceiver(mCommanderMessageReceiver);
        } else {
            result.notImplemented();
        }
    }
}
