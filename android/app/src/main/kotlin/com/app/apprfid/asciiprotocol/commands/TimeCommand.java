package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.Constants;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCommand extends AsciiSelfResponderCommandBase {
    private Date privateTime;

    public TimeCommand() {
        super(".tm");
    }

    public static TimeCommand synchronousCommand() {
        TimeCommand timeCommand = new TimeCommand();
        timeCommand.setSynchronousCommandResponder(timeCommand);
        return timeCommand;
    }

    public static TimeCommand synchronousCommand(Date date) {
        TimeCommand timeCommand = new TimeCommand();
        timeCommand.setSynchronousCommandResponder(timeCommand);
        timeCommand.setTime(date);
        return timeCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        if (getTime() != null) {
            sb.append("-s" + new SimpleDateFormat("HHmmss", Constants.COMMAND_LOCALE).format(getTime()));
        }
    }

    public final Date getTime() {
        return this.privateTime;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (super.processReceivedLine(str, str2, str3, z)) {
            return true;
        }
        if (!"TM".equals(str2)) {
            return false;
        }
        try {
            setTime(new SimpleDateFormat("HH:mm:ss", Constants.COMMAND_LOCALE).parse(str3.trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appendToResponse(str);
        return true;
    }

    public final void setTime(Date date) {
        this.privateTime = date;
    }
}
