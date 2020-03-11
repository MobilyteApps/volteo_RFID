package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public abstract class SwitchPressCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private int privateDuration;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;

    public SwitchPressCommand(String str) {
        super(str);
        CommandParameters.setDefaultParametersFor(this);
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getDuration() >= 0) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-t%d", new Object[]{Integer.valueOf(getDuration())}));
        }
    }

    public final int getDuration() {
        return this.privateDuration;
    }

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
    }

    public final TriState getTakeNoAction() {
        return this.privateTakeNoAction;
    }

    public final boolean implementsReadParameters() {
        return true;
    }

    public final boolean implementsResetParameters() {
        return true;
    }

    public final boolean implementsTakeNoAction() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (CommandParameters.parseParameterFor(this, str)) {
            return true;
        }
        if (str.length() < 1 || !str.startsWith("t")) {
            return super.responseDidReceiveParameter(str);
        }
        setDuration(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(1).trim(), new Object[0])));
        return true;
    }

    public final void setDuration(int i) {
        this.privateDuration = i;
    }

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
    }

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }
}
