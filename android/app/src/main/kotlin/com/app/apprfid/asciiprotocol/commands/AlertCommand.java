package com.app.apprfid.asciiprotocol.commands;


import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.AlertDuration;
import com.app.apprfid.asciiprotocol.enumerations.BuzzerTone;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

public class AlertCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private AlertDuration privateDuration = AlertDuration.getValues()[0];
    private TriState privateEnableBuzzer;
    private TriState privateEnableVibrator;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;
    private BuzzerTone privateTone = BuzzerTone.getValues()[0];

    public AlertCommand() {
        super(".al");
        CommandParameters.setDefaultParametersFor(this);
        setDuration(AlertDuration.NOT_SPECIFIED);
    }

    public static AlertCommand synchronousCommand() {
        AlertCommand alertCommand = new AlertCommand();
        alertCommand.setSynchronousCommandResponder(alertCommand);
        return alertCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getEnableBuzzer() != TriState.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-b%s", new Object[]{getEnableBuzzer().getArgument()}));
        }
        if (getEnableVibrator() != TriState.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-v%s", new Object[]{getEnableVibrator().getArgument()}));
        }
        if (getDuration() != AlertDuration.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-d%s", new Object[]{getDuration().getArgument()}));
        }
        if (getTone() != BuzzerTone.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-t%s", new Object[]{getTone().getArgument()}));
        }
    }

    public final AlertDuration getDuration() {
        return this.privateDuration;
    }

    public final TriState getEnableBuzzer() {
        return this.privateEnableBuzzer;
    }

    public final TriState getEnableVibrator() {
        return this.privateEnableVibrator;
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

    public final BuzzerTone getTone() {
        return this.privateTone;
    }

    public final boolean implementsReadParameters() {
        return true;
    }

    public final boolean implementsResetParameters() {
        return true;
    }

    public final boolean implementsTakeNoAction() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (CommandParameters.parseParameterFor(this, str)) {
            return true;
        }
        if (str.length() <= 1) {
            return super.responseDidReceiveParameter(str);
        }
        switch (str.charAt(0)) {
            case 'b':
                setEnableBuzzer(TriState.Parse(str.substring(1)));
                return true;
            case 'd':
                setDuration(AlertDuration.Parse(str.substring(1)));
                return true;
            case 't':
                setTone(BuzzerTone.Parse(str.substring(1)));
                return true;
            case 'v':
                setEnableVibrator(TriState.Parse(str.substring(1)));
                return true;
            default:
                return false;
        }
    }

    public final void setDuration(AlertDuration alertDuration) {
        this.privateDuration = alertDuration;
    }

    public final void setEnableBuzzer(TriState triState) {
        this.privateEnableBuzzer = triState;
    }

    public final void setEnableVibrator(TriState triState) {
        this.privateEnableVibrator = triState;
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

    public final void setTone(BuzzerTone buzzerTone) {
        this.privateTone = buzzerTone;
    }
}
