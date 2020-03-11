package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class WriteAutorunFileCommand extends AsciiSelfResponderCommandBase {
    private IAsciiCommand privateAutorunCommand = null;
    private String privateValue = null;

    public WriteAutorunFileCommand() {
        super(".wa");
        setCaptureNonLibraryResponses(true);
    }

    public static WriteAutorunFileCommand synchronousCommand() {
        WriteAutorunFileCommand writeAutorunFileCommand = new WriteAutorunFileCommand();
        writeAutorunFileCommand.setSynchronousCommandResponder(writeAutorunFileCommand);
        return writeAutorunFileCommand;
    }

    public IAsciiCommand getAutorunCommand() {
        return this.privateAutorunCommand;
    }

    public String getCommandLine() {
        String commandName = getCommandName();
        String str = "";
        if (getAutorunCommand() != null) {
            str = getAutorunCommand().getCommandLine();
        } else if (getValue() != null) {
            str = getValue();
        }
        return commandName + " " + str;
    }

    public String getValue() {
        return this.privateValue;
    }

    public void setAutorunCommand(IAsciiCommand iAsciiCommand) {
        this.privateAutorunCommand = iAsciiCommand;
    }

    public void setValue(String str) {
        this.privateValue = str;
    }
}
