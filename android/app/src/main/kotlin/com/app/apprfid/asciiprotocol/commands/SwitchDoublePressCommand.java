package com.app.apprfid.asciiprotocol.commands;

public class SwitchDoublePressCommand extends SwitchPressCommand {
    public SwitchDoublePressCommand() {
        super(".pd");
    }

    public static SwitchDoublePressCommand synchronousCommand() {
        SwitchDoublePressCommand switchDoublePressCommand = new SwitchDoublePressCommand();
        switchDoublePressCommand.setSynchronousCommandResponder(switchDoublePressCommand);
        return switchDoublePressCommand;
    }

    public static SwitchDoublePressCommand synchronousCommand(int i) {
        SwitchDoublePressCommand switchDoublePressCommand = new SwitchDoublePressCommand();
        switchDoublePressCommand.setSynchronousCommandResponder(switchDoublePressCommand);
        switchDoublePressCommand.setDuration(i);
        return switchDoublePressCommand;
    }
}
