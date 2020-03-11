package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCommand extends AsciiSelfResponderCommandBase {
    private Date privateDate;

    public DateCommand() {
        super(".da");
    }

    public static DateCommand synchronousCommand() {
        DateCommand dateCommand = new DateCommand();
        dateCommand.setSynchronousCommandResponder(dateCommand);
        return dateCommand;
    }

    public static DateCommand synchronousCommand(Date date) {
        DateCommand dateCommand = new DateCommand();
        dateCommand.setSynchronousCommandResponder(dateCommand);
        dateCommand.setDate(date);
        return dateCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        if (getDate() != null) {
            sb.append("-s" + new SimpleDateFormat("yyMMdd", Constants.COMMAND_LOCALE).format(getDate()));
        }
    }

    public final Date getDate() {
        return this.privateDate;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!"DA".equals(str2)) {
            return false;
        }
        try {
            setDate(new SimpleDateFormat("yyyy-MM-dd", Constants.COMMAND_LOCALE).parse(str3.trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appendToResponse(str);
        return true;
    }

    public final void setDate(Date date) {
        this.privateDate = date;
    }
}
