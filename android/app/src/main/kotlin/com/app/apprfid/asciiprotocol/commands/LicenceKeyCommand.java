package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.DeleteConfirmation;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.util.Locale;

public class LicenceKeyCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private DeleteConfirmation mDeleteLicenceKey = DeleteConfirmation.NOT_SPECIFIED;
    private String mLicenceKey = null;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;

    public LicenceKeyCommand() {
        super(".lk");
        CommandParameters.setDefaultParametersFor(this);
    }

    public static int maximumLicenceKeyLength() {
        return 255;
    }

    public static LicenceKeyCommand synchronousCommand() {
        LicenceKeyCommand licenceKeyCommand = new LicenceKeyCommand();
        licenceKeyCommand.setSynchronousCommandResponder(licenceKeyCommand);
        return licenceKeyCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getLicenceKey() != null) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-s\"%s\"", new Object[]{getLicenceKey()}));
        }
        if (getDeleteLicenceKey() == DeleteConfirmation.YES) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-d%s", new Object[]{getDeleteLicenceKey().getArgument()}));
        }
    }

    public final DeleteConfirmation getDeleteLicenceKey() {
        return this.mDeleteLicenceKey;
    }

    public final String getLicenceKey() {
        return this.mLicenceKey;
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
        return false;
    }

    public final boolean implementsTakeNoAction() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!"LK".equals(str2)) {
            return false;
        }
        setLicenceKey(str3);
        appendToResponse(str);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (!CommandParameters.parseParameterFor(this, str)) {
            return super.responseDidReceiveParameter(str);
        }
        return true;
    }

    public final void setDeleteLicenceKey(DeleteConfirmation deleteConfirmation) {
        this.mDeleteLicenceKey = deleteConfirmation;
    }

    public final void setLicenceKey(String str) {
        if (str == null || str.length() <= maximumLicenceKeyLength()) {
            this.mLicenceKey = str;
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "Licence key is too long (%d) - maximum length is %d characters", new Object[]{str, Integer.valueOf(maximumLicenceKeyLength())}));
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
