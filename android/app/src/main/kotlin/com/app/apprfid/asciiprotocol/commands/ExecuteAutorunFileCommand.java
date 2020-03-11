package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class ExecuteAutorunFileCommand extends AsciiSelfResponderCommandBase {
    public ExecuteAutorunFileCommand() {
        super(".ea");
    }

    public static ExecuteAutorunFileCommand synchronousCommand() {
        ExecuteAutorunFileCommand executeAutorunFileCommand = new ExecuteAutorunFileCommand();
        executeAutorunFileCommand.setSynchronousCommandResponder(executeAutorunFileCommand);
        return executeAutorunFileCommand;
    }
}
