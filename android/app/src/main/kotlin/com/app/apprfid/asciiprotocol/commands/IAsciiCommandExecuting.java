package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.responders.IAsciiCommandResponder;

public interface IAsciiCommandExecuting {
    void addResponder(IAsciiCommandResponder iAsciiCommandResponder);

    void addSynchronousResponder();

    void clearResponders();

    void executeCommand(IAsciiCommand iAsciiCommand);

    Iterable getResponderChain();

    void removeResponder(IAsciiCommandResponder iAsciiCommandResponder);

    void removeSynchronousResponder();
}
