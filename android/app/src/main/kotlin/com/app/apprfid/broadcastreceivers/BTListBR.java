package com.app.apprfid.broadcastreceivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.apprfid.GlobalStorage;


public class BTListBR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // If it's already paired, skip it, because it's been listed already
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                String listText = device.getName() + "\n" + device.getAddress();
                System.out.println("FlutterVolteoPlugin.onReceive-->" + listText);
                boolean isNew = true;
                for (int i = 0; i < GlobalStorage.getInstance().getDeviceList().size(); ++i) {
                    BluetoothDevice bluetoothDevice = GlobalStorage.getInstance().getDeviceList().get(i);
                    if (bluetoothDevice.getAddress().equals(device.getAddress())) {
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    GlobalStorage.getInstance().getDeviceList().add(device);
                }
            }

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

        }
    }
}
