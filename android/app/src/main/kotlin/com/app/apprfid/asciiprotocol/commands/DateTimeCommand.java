package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateTimeCommand extends AsciiSelfResponderCommandBase {
    private String mDateValue;
    private String mtimeValue;
    private ArrayList partialResponse;
    private Date privateValue;

    public DateTimeCommand() {
        super(".da");
    }

    public static DateTimeCommand synchronousCommand() {
        DateTimeCommand dateTimeCommand = new DateTimeCommand();
        dateTimeCommand.setSynchronousCommandResponder(dateTimeCommand);
        return dateTimeCommand;
    }

    public static DateTimeCommand synchronousCommand(Date date) {
        DateTimeCommand dateTimeCommand = new DateTimeCommand();
        dateTimeCommand.setSynchronousCommandResponder(dateTimeCommand);
        dateTimeCommand.setValue(date);
        return dateTimeCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        if (getValue() != null) {
            sb.append(new SimpleDateFormat("yyMMdd", Constants.COMMAND_LOCALE).format(getValue()));
        }
        sb.append("\r\n");
        setCommandName(".tm");
        super.buildCommandLine(sb);
        setCommandName(".da");
        if (getValue() != null) {
            sb.append(new SimpleDateFormat("HHmmss", Constants.COMMAND_LOCALE).format(getValue()));
        }
        sb.append("\r\n");
    }

    public final Date getValue() {
        return this.privateValue;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if ("DA".equals(str2)) {
            this.mDateValue = str3;
        }
        if (!"TM".equals(str2)) {
            return false;
        }
        this.mtimeValue = str3;
        appendToResponse(str);
        return true;
    }

    /* access modifiers changed from: protected */
    public void responseDidFinish(boolean z) {
        if (!".da".equals(getCommandName())) {
            setCommandName(".da");
            this.partialResponse.addAll(getResponse());
            setResponse(this.partialResponse);
            try {
                setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Constants.COMMAND_LOCALE).parse(this.mDateValue + " " + this.mtimeValue));
            } catch (ParseException e) {
                setValue(null);
                setIsSuccessful(false);
            }
            super.responseDidFinish(z);
        } else if (isSuccessful()) {
            this.partialResponse = new ArrayList();
            this.partialResponse.addAll(getResponse());
            setCommandName(".tm");
        } else {
            super.responseDidFinish(z);
        }
    }

    /* access modifiers changed from: protected */
    public void responseDidStart() {
        if (".da".equals(getCommandName())) {
            super.responseDidStart();
            this.mDateValue = null;
            this.mtimeValue = null;
        }
    }

    public final void setValue(Date date) {
        this.privateValue = date;
    }
}
