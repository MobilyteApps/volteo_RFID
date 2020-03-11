package com.app.apprfid.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class h {
    private static final h a = new h();
    private final ExecutorService b = Executors.newFixedThreadPool(1);

    private h() {
    }

    public static h a() {
        return a;
    }

    public void a(Runnable runnable) {
        this.b.submit(runnable);
    }
}
