package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.b.b;
import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.util.Locale;

public class SleepTimeoutCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private int mDuration;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;

    public SleepTimeoutCommand() {
        super(".st");
        CommandParameters.setDefaultParametersFor(this);
    }

    public static final int getMaximumDuration() {
        return 999;
    }

    public static final int getMinimumDuration() {
        return 15;
    }

    public static SleepTimeoutCommand synchronousCommand() {
        SleepTimeoutCommand sleepTimeoutCommand = new SleepTimeoutCommand();
        sleepTimeoutCommand.setSynchronousCommandResponder(sleepTimeoutCommand);
        return sleepTimeoutCommand;
    }

    public static SleepTimeoutCommand synchronousCommand(int i) {
        SleepTimeoutCommand sleepTimeoutCommand = new SleepTimeoutCommand();
        sleepTimeoutCommand.setSynchronousCommandResponder(sleepTimeoutCommand);
        sleepTimeoutCommand.setDuration(i);
        return sleepTimeoutCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getDuration() >= getMinimumDuration() && getDuration() <= getMaximumDuration()) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-t%d", new Object[]{Integer.valueOf(getDuration())}));
        }
    }

    public final int getDuration() {
        return this.mDuration;
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
        if (CommandParameters.parseParameterFor(this, str) || b.a(str)) {
            return true;
        }
        switch (str.charAt(0)) {
            case 't':
                setDuration(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(1).trim(), new Object[0])));
                return true;
            default:
                return super.responseDidReceiveParameter(str);
        }
    }

    public final void setDuration(int i) {
        if (i < getMinimumDuration() || i > getMaximumDuration()) {
            throw new IllegalArgumentException(String.format(Locale.US, "Duration (%d) is not in the range of %d to %d seconds", new Object[]{Integer.valueOf(i), Integer.valueOf(getMinimumDuration()), Integer.valueOf(getMaximumDuration())}));
        } else {
            this.mDuration = i;
        }
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
