package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class FactoryDefaultsCommand extends AsciiSelfResponderCommandBase {
    public FactoryDefaultsCommand() {
        super(".fd");
    }

    public static FactoryDefaultsCommand synchronousCommand() {
        FactoryDefaultsCommand factoryDefaultsCommand = new FactoryDefaultsCommand();
        factoryDefaultsCommand.setSynchronousCommandResponder(factoryDefaultsCommand);
        return factoryDefaultsCommand;
    }
}
