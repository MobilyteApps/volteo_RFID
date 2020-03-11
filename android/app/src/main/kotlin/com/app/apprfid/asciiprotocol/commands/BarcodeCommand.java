package com.app.apprfid.asciiprotocol.commands;



import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.parameters.IResponseParameters;
import com.app.apprfid.asciiprotocol.parameters.ResponseParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;
import com.app.apprfid.asciiprotocol.responders.IBarcodeReceivedDelegate;
import com.app.apprfid.b.b;

import java.util.Locale;

public class BarcodeCommand extends AsciiSelfResponderCommandBase implements ICommandParameters, IResponseParameters {
    private IBarcodeReceivedDelegate mBarcodeReceivedDelegate = null;
    private String privateData;
    private TriState privateIncludeDateTime;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;
    private TriState privateUseAlert;
    private TriState privateUseEscapeCharacter;
    private int scanTime;

    public BarcodeCommand() {
        super(".bc");
        CommandParameters.setDefaultParametersFor(this);
        ResponseParameters.setDefaultParametersFor(this);
        setMaxSynchronousWaitTime(11.0d);
    }

    private String convertEscapeSequences(String str) {
        String replace = str.replace("\u001b\r", "\r").replace("\u001b\n", "\n").replace("\u001b\u001b", Constants.BARCODE_ESCAPE_CHARACTER);
        return (replace.length() <= 0 || replace.charAt(replace.length() + -1) != Constants.BARCODE_ESCAPE_CHARACTER.charAt(0)) ? replace : replace.substring(0, replace.length() - 1);
    }

    private void informDelegateResponseDidComplete() {
        if (this.mBarcodeReceivedDelegate != null && !b.a(getData())) {
            this.mBarcodeReceivedDelegate.barcodeReceived(getData());
        }
    }

    private void setData(String str) {
        this.privateData = str;
    }

    public static BarcodeCommand synchronousCommand() {
        BarcodeCommand barcodeCommand = new BarcodeCommand();
        barcodeCommand.setSynchronousCommandResponder(barcodeCommand);
        return barcodeCommand;
    }

    public static BarcodeCommand synchronousCommand(int i) {
        BarcodeCommand barcodeCommand = new BarcodeCommand();
        barcodeCommand.setSynchronousCommandResponder(barcodeCommand);
        barcodeCommand.setScanTime(i);
        return barcodeCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        ResponseParameters.appendToCommandLine(this, sb);
        if (getScanTime() >= 1 && getScanTime() <= 9) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-t%d", new Object[]{Integer.valueOf(getScanTime())}));
        }
        if (getUseEscapeCharacter() != TriState.NOT_SPECIFIED) {
            sb.append(String.format(Constants.COMMAND_LOCALE, "-e%s", new Object[]{getUseEscapeCharacter().getArgument()}));
        }
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setData("");
    }

    public final IBarcodeReceivedDelegate getBarcodeReceivedDelegate() {
        return this.mBarcodeReceivedDelegate;
    }

    public final String getData() {
        return this.privateData;
    }

    public final TriState getIncludeDateTime() {
        return this.privateIncludeDateTime;
    }

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
    }

    public final int getScanTime() {
        return this.scanTime;
    }

    public final TriState getTakeNoAction() {
        return this.privateTakeNoAction;
    }

    public final TriState getUseAlert() {
        return this.privateUseAlert;
    }

    public final TriState getUseEscapeCharacter() {
        return this.privateUseEscapeCharacter;
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
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!getResponseStarted()) {
            return false;
        }
        if ("OK".equals(str2)) {
            informDelegateResponseDidComplete();
        } else if ("ER".equals(str2)) {
            informDelegateResponseDidComplete();
        } else if ("BR".equals(str2)) {
            setData(str3);
        } else if (!"BC".equals(str2)) {
            return false;
        } else {
            setData(convertEscapeSequences(str3));
        }
        appendToResponse(str);
        return true;
    }

    /* access modifiers changed from: protected */
    public void responseDidFinish(boolean z) {
        super.responseDidFinish(z);
        informDelegateResponseDidComplete();
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (ResponseParameters.parseParameterFor(this, str) || CommandParameters.parseParameterFor(this, str)) {
            return true;
        }
        if (b.a(str)) {
            return super.responseDidReceiveParameter(str);
        }
        switch (str.charAt(0)) {
            case 'e':
                setUseEscapeCharacter(TriState.Parse(str.substring(1)));
                return true;
            case 't':
                setScanTime(Integer.parseInt(String.format(Constants.COMMAND_LOCALE, str.substring(1).trim(), new Object[0])));
                return true;
            default:
                return true;
        }
    }

    public final void setBarcodeReceivedDelegate(IBarcodeReceivedDelegate iBarcodeReceivedDelegate) {
        this.mBarcodeReceivedDelegate = iBarcodeReceivedDelegate;
    }

    public final void setIncludeDateTime(TriState triState) {
        this.privateIncludeDateTime = triState;
    }

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
    }

    public final void setScanTime(int i) {
        if (i < 1 || i > 9) {
            throw new IllegalArgumentException(String.format(Locale.US, "Scan time (%d) is not in the range of 1 to 9 seconds", new Object[]{Integer.valueOf(i)}));
        }
        this.scanTime = i;
        setMaxSynchronousWaitTime((double) (getScanTime() + 2));
    }

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }

    public final void setUseAlert(TriState triState) {
        this.privateUseAlert = triState;
    }

    public final void setUseEscapeCharacter(TriState triState) {
        this.privateUseEscapeCharacter = triState;
    }
}
