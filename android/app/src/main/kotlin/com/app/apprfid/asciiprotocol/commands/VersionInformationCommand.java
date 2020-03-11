package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class VersionInformationCommand extends AsciiSelfResponderCommandBase {
    private String privateAntennaSerialNumber;
    private String privateAsciiProtocol;
    private String privateBluetoothAddress;
    private String privateBluetoothVersion;
    private String privateBootloaderVersion;
    private String privateFirmwareVersion;
    private String privateManufacturer;
    private String privateRadioBootloaderVersion;
    private String privateRadioFirmwareVersion;
    private String privateRadioSerialNumber;
    private String privateSerialNumber;

    public VersionInformationCommand() {
        super(".vr");
    }

    private void setAntennaSerialNumber(String str) {
        this.privateAntennaSerialNumber = str;
    }

    private void setAsciiProtocol(String str) {
        this.privateAsciiProtocol = str;
    }

    private void setBootloaderVersion(String str) {
        this.privateBootloaderVersion = str;
    }

    private void setFirmwareVersion(String str) {
        this.privateFirmwareVersion = str;
    }

    private void setManufacturer(String str) {
        this.privateManufacturer = str;
    }

    private void setRadioBootloaderVersion(String str) {
        this.privateRadioBootloaderVersion = str;
    }

    private void setRadioFirmwareVersion(String str) {
        this.privateRadioFirmwareVersion = str;
    }

    private void setRadioSerialNumber(String str) {
        this.privateRadioSerialNumber = str;
    }

    private void setSerialNumber(String str) {
        this.privateSerialNumber = str;
    }

    public static VersionInformationCommand synchronousCommand() {
        VersionInformationCommand versionInformationCommand = new VersionInformationCommand();
        versionInformationCommand.setSynchronousCommandResponder(versionInformationCommand);
        return versionInformationCommand;
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setAntennaSerialNumber("");
        setAsciiProtocol("");
        setBootloaderVersion("");
        setFirmwareVersion("");
        setManufacturer("");
        setRadioBootloaderVersion("");
        setRadioFirmwareVersion("");
        setRadioSerialNumber("");
        setSerialNumber("");
        setBluetoothAddress("");
        setBluetoothVersion("");
    }

    public final String getAntennaSerialNumber() {
        return this.privateAntennaSerialNumber;
    }

    public final String getAsciiProtocol() {
        return this.privateAsciiProtocol;
    }

    public final String getBluetoothAddress() {
        return this.privateBluetoothAddress;
    }

    public final String getBluetoothVersion() {
        return this.privateBluetoothVersion;
    }

    public final String getBootloaderVersion() {
        return this.privateBootloaderVersion;
    }

    public final String getFirmwareVersion() {
        return this.privateFirmwareVersion;
    }

    public final String getManufacturer() {
        return this.privateManufacturer;
    }

    public final String getRadioBootloaderVersion() {
        return this.privateRadioBootloaderVersion;
    }

    public final String getRadioFirmwareVersion() {
        return this.privateRadioFirmwareVersion;
    }

    public final String getRadioSerialNumber() {
        return this.privateRadioSerialNumber;
    }

    public final String getSerialNumber() {
        return this.privateSerialNumber;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if ("MF".equals(str2)) {
            setManufacturer(str3.trim());
        } else if ("US".equals(str2)) {
            setSerialNumber(str3.trim());
        } else if ("PV".equals(str2)) {
            setAsciiProtocol(str3.trim());
        } else if ("UF".equals(str2)) {
            setFirmwareVersion(str3.trim());
        } else if ("UB".equals(str2)) {
            setBootloaderVersion(str3.trim());
        } else if ("RS".equals(str2)) {
            setRadioSerialNumber(str3.trim());
        } else if ("RF".equals(str2)) {
            setRadioFirmwareVersion(str3.trim());
        } else if ("RB".equals(str2)) {
            setRadioBootloaderVersion(str3.trim());
        } else if ("AS".equals(str2)) {
            setAntennaSerialNumber(str3.trim());
        } else if ("BA".equals(str2)) {
            setBluetoothAddress(str3.trim());
        } else if (!"BV".equals(str2)) {
            return false;
        } else {
            setBluetoothVersion(str3.trim());
        }
        appendToResponse(str);
        return true;
    }

    public final void setBluetoothAddress(String str) {
        this.privateBluetoothAddress = str;
    }

    public final void setBluetoothVersion(String str) {
        this.privateBluetoothVersion = str;
    }
}
