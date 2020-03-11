package com.app.apprfid.asciiprotocol.commands;

public class SwitchDoublePressUserActionCommand extends SwitchPressUserActionCommand {
    public SwitchDoublePressUserActionCommand() {
        super(".dp", "DP");
    }

    public static SwitchDoublePressUserActionCommand synchronousCommand() {
        SwitchDoublePressUserActionCommand switchDoublePressUserActionCommand = new SwitchDoublePressUserActionCommand();
        switchDoublePressUserActionCommand.setSynchronousCommandResponder(switchDoublePressUserActionCommand);
        return switchDoublePressUserActionCommand;
    }
}
