package com.app.apprfid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextBackEvent extends EditText {
    private e a;

    public EditTextBackEvent(Context context) {
        super(context);
    }

    public EditTextBackEvent(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditTextBackEvent(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1 && this.a != null) {
            this.a.onImeBack(this, getText().toString());
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    public void setOnEditTextImeBackListener(e eVar) {
        this.a = eVar;
    }
}
