package com.app.apprfid.asciiprotocol.responders;

import com.app.apprfid.asciiprotocol.enumerations.SwitchState;

public interface ISwitchStateReceivedDelegate {
    void switchStateReceived(SwitchState switchState);
}
