package com.app.apprfid.util;

import android.widget.Toast;

final class j implements Runnable {
    final /* synthetic */ Toast a;

    j(Toast toast) {
        this.a = toast;
    }

    public void run() {
        this.a.cancel();
    }
}
