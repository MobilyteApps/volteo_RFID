package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class SleepCommand extends AsciiSelfResponderCommandBase {
    public SleepCommand() {
        super(".sl");
    }

    public static SleepCommand synchronousCommand() {
        SleepCommand sleepCommand = new SleepCommand();
        sleepCommand.setSynchronousCommandResponder(sleepCommand);
        return sleepCommand;
    }
}
