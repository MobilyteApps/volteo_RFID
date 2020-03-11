package com.app.apprfid.asciiprotocol.commands;

public class SwitchSinglePressUserActionCommand extends SwitchPressUserActionCommand {
    public SwitchSinglePressUserActionCommand() {
        super(".sp", "SP");
    }

    public static SwitchSinglePressUserActionCommand synchronousCommand() {
        SwitchSinglePressUserActionCommand switchSinglePressUserActionCommand = new SwitchSinglePressUserActionCommand();
        switchSinglePressUserActionCommand.setSynchronousCommandResponder(switchSinglePressUserActionCommand);
        return switchSinglePressUserActionCommand;
    }
}
