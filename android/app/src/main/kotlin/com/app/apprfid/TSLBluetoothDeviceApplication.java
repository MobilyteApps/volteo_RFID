package com.app.apprfid;

import com.app.apprfid.asciiprotocol.AsciiCommander;

import io.flutter.app.FlutterApplication;



public class TSLBluetoothDeviceApplication extends FlutterApplication  {
    private static AsciiCommander commander = null;

    public AsciiCommander getCommander() {
        return commander;
    }

    public void setCommander(AsciiCommander asciiCommander) {
        commander = asciiCommander;
    }
}
