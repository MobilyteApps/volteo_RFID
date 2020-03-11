package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.SwitchAction;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.util.Locale;

public class SwitchActionCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private TriState privateAsynchronousReportingEnabled;
    private SwitchAction privateDoublePressAction = SwitchAction.getValues()[0];
    private int privateDoublePressRepeatDelay;
    private TriState privateHapticFeedbackEnabled;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private SwitchAction privateSinglePressAction = SwitchAction.getValues()[0];
    private int privateSinglePressRepeatDelay;
    private TriState privateTakeNoAction;

    public SwitchActionCommand() {
        super(".sa");
        CommandParameters.setDefaultParametersFor(this);
        this.privateAsynchronousReportingEnabled = TriState.NOT_SPECIFIED;
        this.privateSinglePressAction = SwitchAction.NO_CHANGE;
        this.privateDoublePressAction = SwitchAction.NO_CHANGE;
        this.privateSinglePressRepeatDelay = -1;
        this.privateDoublePressRepeatDelay = -1;
    }

    public static int maximumRepeatDelay() {
        return 999;
    }

    public static int minimumRepeatDelay() {
        return 1;
    }

    public static SwitchActionCommand synchronousCommand() {
        SwitchActionCommand switchActionCommand = new SwitchActionCommand();
        switchActionCommand.setSynchronousCommandResponder(switchActionCommand);
        return switchActionCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getAsynchronousReportingEnabled() != TriState.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-a%s", new Object[]{getAsynchronousReportingEnabled().getArgument()}));
        }
        if (getSinglePressAction() != SwitchAction.NO_CHANGE) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-s%s", new Object[]{getSinglePressAction().getArgument()}));
        }
        if (getDoublePressAction() != SwitchAction.NO_CHANGE) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-d%s", new Object[]{getDoublePressAction().getArgument()}));
        }
        if (getSinglePressRepeatDelay() > 0) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-rs%d", new Object[]{Integer.valueOf(getSinglePressRepeatDelay())}));
        }
        if (getDoublePressRepeatDelay() > 0) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-rd%d", new Object[]{Integer.valueOf(getDoublePressRepeatDelay())}));
        }
        if (getHapticFeedbackEnabled() != TriState.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-h%s", new Object[]{getHapticFeedbackEnabled().getArgument()}));
        }
    }

    public final TriState getAsynchronousReportingEnabled() {
        return this.privateAsynchronousReportingEnabled;
    }

    public final SwitchAction getDoublePressAction() {
        return this.privateDoublePressAction;
    }

    public final int getDoublePressRepeatDelay() {
        return this.privateDoublePressRepeatDelay;
    }

    public final TriState getHapticFeedbackEnabled() {
        return this.privateHapticFeedbackEnabled;
    }

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
    }

    public final SwitchAction getSinglePressAction() {
        return this.privateSinglePressAction;
    }

    public final int getSinglePressRepeatDelay() {
        return this.privateSinglePressRepeatDelay;
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
        if (str.length() <= 1) {
            return super.responseDidReceiveParameter(str);
        }
        if (str.startsWith("a")) {
            setAsynchronousReportingEnabled(TriState.Parse(str.substring(1)));
            return true;
        } else if (str.startsWith("s")) {
            setSinglePressAction(SwitchAction.Parse(str.substring(1)));
            return true;
        } else if (str.startsWith("d")) {
            setDoublePressAction(SwitchAction.Parse(str.substring(1)));
            return true;
        } else if (str.startsWith("h")) {
            setHapticFeedbackEnabled(TriState.Parse(str.substring(1)));
            return true;
        } else if (str.length() <= 2 || !str.startsWith("r")) {
            return super.responseDidReceiveParameter(str);
        } else {
            if (str.startsWith("s", 1)) {
                setSinglePressRepeatDelay(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(2).trim(), new Object[0])));
                return true;
            } else if (!str.startsWith("d", 1)) {
                return super.responseDidReceiveParameter(str);
            } else {
                setDoublePressRepeatDelay(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(2).trim(), new Object[0])));
                return true;
            }
        }
    }

    public final void setAsynchronousReportingEnabled(TriState triState) {
        this.privateAsynchronousReportingEnabled = triState;
    }

    public final void setDoublePressAction(SwitchAction switchAction) {
        this.privateDoublePressAction = switchAction;
    }

    public final void setDoublePressRepeatDelay(int i) {
        if (i < minimumRepeatDelay() || i > maximumRepeatDelay()) {
            throw new IllegalArgumentException(String.format(Locale.US, "Double Press repeat delay (%d) is not in the range of %d to %d milliseconds", new Object[]{Integer.valueOf(i), Integer.valueOf(minimumRepeatDelay()), Integer.valueOf(maximumRepeatDelay())}));
        } else {
            this.privateDoublePressRepeatDelay = i;
        }
    }

    public final void setHapticFeedbackEnabled(TriState triState) {
        this.privateHapticFeedbackEnabled = triState;
    }

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
    }

    public final void setSinglePressAction(SwitchAction switchAction) {
        this.privateSinglePressAction = switchAction;
    }

    public final void setSinglePressRepeatDelay(int i) {
        if (i < minimumRepeatDelay() || i > maximumRepeatDelay()) {
            throw new IllegalArgumentException(String.format(Locale.US, "Single Press repeat delay (%d) is not in the range of %d to %d milliseconds", new Object[]{Integer.valueOf(i), Integer.valueOf(minimumRepeatDelay()), Integer.valueOf(maximumRepeatDelay())}));
        } else {
            this.privateSinglePressRepeatDelay = i;
        }
    }

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }
}
