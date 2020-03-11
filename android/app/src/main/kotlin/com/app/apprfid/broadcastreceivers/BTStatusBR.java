package com.app.apprfid.broadcastreceivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.apprfid.FlutterVolteoPlugin;


public class BTStatusBR  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        String bluetoothState = "bluetooth_off";
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    bluetoothState = "bluetooth_off";
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    bluetoothState = "bluetooth_turning_off";
                    break;
                case BluetoothAdapter.STATE_ON:
                    bluetoothState = "bluetooth_on";
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    bluetoothState = "bluetooth_turning_on";
                    break;
            }
             FlutterVolteoPlugin.dataStreamHandler.sendData(bluetoothState);
        }
    }

}
