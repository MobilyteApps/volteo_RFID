package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public abstract class SwitchPressUserActionCommand extends AsciiSelfResponderCommandBase {
    private String mHeader;
    private IAsciiCommand privateActionCommand = null;
    private String privateValue = null;

    public SwitchPressUserActionCommand(String str, String str2) {
        super(str);
        this.mHeader = str2;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        if (getActionCommand() != null) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-s%s", new Object[]{getActionCommand().getCommandLine()}));
        }
        if (getValue() != null) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-s%s", new Object[]{getValue()}));
        }
    }

    public IAsciiCommand getActionCommand() {
        return this.privateActionCommand;
    }

    public String getValue() {
        return this.privateValue;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!getResponseStarted()) {
            return false;
        }
        if (!this.mHeader.equals(str2)) {
            return false;
        }
        setValue(str3);
        appendToResponse(str);
        return true;
    }

    public void setActionCommand(IAsciiCommand iAsciiCommand) {
        this.privateActionCommand = iAsciiCommand;
    }

    public void setValue(String str) {
        this.privateValue = str;
    }
}
