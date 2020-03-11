package com.app.apprfid.asciiprotocol.responders;

import com.app.apprfid.asciiprotocol.enumerations.SwitchState;

public class SwitchResponder extends AsciiCommandResponderBase {
    private ISwitchStateReceivedDelegate privateDelegate = null;
    private SwitchState privateState;

    public SwitchResponder() {
        super(".ss");
    }

    public SwitchState getState() {
        return this.privateState;
    }

    public ISwitchStateReceivedDelegate getSwitchStateReceivedDelegate() {
        return this.privateDelegate;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z) || getResponseStarted() || !"SW".equals(str2)) {
            return false;
        }
        setState(SwitchState.Parse(str3));
        if (getSwitchStateReceivedDelegate() != null) {
            getSwitchStateReceivedDelegate().switchStateReceived(getState());
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void setState(SwitchState switchState) {
        this.privateState = switchState;
    }

    public void setSwitchStateReceivedDelegate(ISwitchStateReceivedDelegate iSwitchStateReceivedDelegate) {
        this.privateDelegate = iSwitchStateReceivedDelegate;
    }
}
