package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.IAsciiCommandResponder;

public interface IAsciiCommand {
    String getCommandLine();

    String getCommandName();

    double getMaxSynchronousWaitTime();

    IAsciiCommandResponder getSynchronousCommandResponder();

    void setMaxSynchronousWaitTime(double d);

    void setSynchronousCommandResponder(IAsciiCommandResponder iAsciiCommandResponder);
}
