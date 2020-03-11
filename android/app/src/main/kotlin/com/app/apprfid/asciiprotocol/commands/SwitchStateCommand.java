package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.SwitchState;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class SwitchStateCommand extends AsciiSelfResponderCommandBase {
    private SwitchState privateState = SwitchState.getValues()[0];

    public SwitchStateCommand() {
        super(".ss");
    }

    private void setState(SwitchState switchState) {
        this.privateState = switchState;
    }

    public static SwitchStateCommand synchronousCommand() {
        SwitchStateCommand switchStateCommand = new SwitchStateCommand();
        switchStateCommand.setSynchronousCommandResponder(switchStateCommand);
        return switchStateCommand;
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setState(SwitchState.OFF);
    }

    public final SwitchState getState() {
        return this.privateState;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!getResponseStarted()) {
            return false;
        }
        if (!"SW".equals(str2)) {
            return false;
        }
        setState(SwitchState.Parse(str3));
        appendToResponse(str);
        return true;
    }
}
