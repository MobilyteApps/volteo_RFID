package com.app.apprfid.asciiprotocol.commands;


import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class AbortCommand extends AsciiSelfResponderCommandBase implements IAsciiCommand {
    public AbortCommand() {
        super(".ab");
    }

    public static AbortCommand synchronousCommand() {
        AbortCommand abortCommand = new AbortCommand();
        abortCommand.setSynchronousCommandResponder(abortCommand);
        return abortCommand;
    }
}
