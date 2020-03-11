package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.ChargeState;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class BatteryStatusCommand extends AsciiSelfResponderCommandBase {
    private int privateBatteryLevel;
    private ChargeState privateChargeStatus;

    public BatteryStatusCommand() {
        super(".bl");
    }

    private void setBatteryLevel(int i) {
        this.privateBatteryLevel = i;
    }

    private void setChargeState(ChargeState chargeState) {
        this.privateChargeStatus = chargeState;
    }

    public static BatteryStatusCommand synchronousCommand() {
        BatteryStatusCommand batteryStatusCommand = new BatteryStatusCommand();
        batteryStatusCommand.setSynchronousCommandResponder(batteryStatusCommand);
        return batteryStatusCommand;
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setBatteryLevel(0);
    }

    public final int getBatteryLevel() {
        return this.privateBatteryLevel;
    }

    public final ChargeState getChargeStatus() {
        return this.privateChargeStatus;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if ("BP".equals(str2)) {
            setBatteryLevel(Integer.parseInt(str3.substring(0, str3.indexOf(37))));
        } else if (!"CH".equals(str2)) {
            return false;
        } else {
            setChargeState(ChargeState.Parse(str3.trim()));
        }
        appendToResponse(str);
        return true;
    }
}
