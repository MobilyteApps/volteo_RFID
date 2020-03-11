package com.app.apprfid.asciiprotocol.commands;

public class SwitchSinglePressCommand extends SwitchPressCommand {
    public SwitchSinglePressCommand() {
        super(".ps");
    }

    public static SwitchSinglePressCommand synchronousCommand() {
        SwitchSinglePressCommand switchSinglePressCommand = new SwitchSinglePressCommand();
        switchSinglePressCommand.setSynchronousCommandResponder(switchSinglePressCommand);
        return switchSinglePressCommand;
    }

    public static SwitchSinglePressCommand synchronousCommand(int i) {
        SwitchSinglePressCommand switchSinglePressCommand = new SwitchSinglePressCommand();
        switchSinglePressCommand.setSynchronousCommandResponder(switchSinglePressCommand);
        switchSinglePressCommand.setDuration(i);
        return switchSinglePressCommand;
    }
}
