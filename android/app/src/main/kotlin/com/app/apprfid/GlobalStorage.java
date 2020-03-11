package com.app.apprfid;
import android.bluetooth.BluetoothDevice;


import com.app.apprfid.broadcastreceivers.BTListBR;

import java.util.ArrayList;
import java.util.List;

public class GlobalStorage {

    private List<BluetoothDevice> newDevicesList = new ArrayList<>();

    private static GlobalStorage _instance = null;

    private BTListBR btListBR;

    public static GlobalStorage getInstance() {

        if (_instance == null) {
                _instance = new GlobalStorage();
        }
        return _instance;
    }

    public List<BluetoothDevice> getDeviceList(){
        return newDevicesList;
    }

    public void setDeviceList(List<BluetoothDevice>  deviceList){
        newDevicesList.addAll(deviceList);
    }

    public void setBTListBR(BTListBR btListBR){
        this.btListBR=btListBR;

    }

    public BTListBR getBTListBR(){
        return this.btListBR;

    }

}

