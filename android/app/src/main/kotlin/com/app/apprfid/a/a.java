package com.app.apprfid.a;

public class a {
    private double a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private volatile boolean g;
    private double h;
    private double i;
    private double j;
    private long k;
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;
    private double q;
    private double r;
    private Thread s;
    /* access modifiers changed from: private */
    public volatile boolean t;

    public a() {
        b();
    }

    public int a() {
        return this.c;
    }

    public void a(double d2) {
        this.a = d2;
    }

    public void a(int i2) {
        this.c = i2;
    }

    public synchronized void a(boolean z) {
        if (this.g != z) {
            if (z) {
                this.k = 0;
                this.l = 1.0d;
            } else {
                this.r = 1.0d / (this.q * ((double) this.c));
            }
            this.g = z;
        }
    }

    /* access modifiers changed from: protected */
    public void a(short[] sArr, int i2) {
        double d2;
        boolean z = this.g;
        int i3 = (int) (((double) this.c) / this.e);
        int i4 = (int) (((double) i3) * this.f);
        double d3 = this.r;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        double d4 = 0.0d;
        double d5 = 0.0d;
        double d6 = 0.0d;
        if (z) {
            i5 = (int) (((double) i4) * this.m);
            i6 = (int) (((double) i4) * (this.m + this.n));
            i7 = (int) (((double) i4) * (this.m + this.n + this.o));
            d4 = 1.0d / ((double) i5);
            d5 = (this.p - 1.0d) / ((double) (i6 - i5));
            d6 = this.p / ((double) (i7 - i4));
        }
        double d7 = this.a * this.l;
        double d8 = (6.283185307179586d * this.i) / ((double) this.c);
        int i8 = 0;
        while (true) {
            double d9 = d8;
            if (i8 < i2 && this.t) {
                if (z) {
                    if (this.k > ((long) i4)) {
                        this.l = 0.0d;
                    } else {
                        if (this.k < ((long) i5)) {
                            this.l += d4;
                        } else if (this.k < ((long) i6)) {
                            this.l += d5;
                        } else if (this.k >= ((long) i7)) {
                            this.l += d6;
                        }
                        if (this.l < 0.0d) {
                            this.l = 0.0d;
                        }
                        if (this.l > 1.0d) {
                            this.l = 1.0d;
                        }
                    }
                    d2 = this.a * this.l;
                    this.k++;
                    if (this.k >= ((long) i3)) {
                        this.k -= (long) i3;
                    }
                } else if (Math.abs(1.0d - this.l) > d3) {
                    this.l += d3;
                    d2 = this.a * this.l;
                } else {
                    d2 = this.a;
                }
                sArr[i8] = (short) ((int) (d2 * Math.sin(this.h) * 32767.0d));
                this.h += d9;
                if (this.h > 6.283185307179586d) {
                    this.h -= 6.283185307179586d;
                }
                if (this.j != 0.0d) {
                    if (this.j < 0.0d) {
                        if (this.i <= this.b) {
                            this.j = -this.j;
                        }
                    } else if (this.i >= this.b) {
                        this.j = -this.j;
                    }
                }
                if (this.d > 0.0d) {
                    this.i += this.j;
                    if (Math.abs(this.i - this.b) < this.j) {
                        this.i = this.b;
                        this.j = 0.0d;
                    }
                } else {
                    this.i = this.b;
                }
                d8 = (6.283185307179586d * this.i) / ((double) this.c);
                i8++;
            } else {
                return;
            }
        }
    }

    public synchronized void b() {
        a(22050);
        c(0.05d);
        b(220.0d);
        a(0.5d);
        this.h = 0.0d;
        this.i = 110.0d;
        this.l = 1.0d;
        this.e = 10.0d;
        this.f = 0.94d;
        this.m = 0.1d;
        this.n = 0.1d;
        this.o = 0.6d;
        this.p = 0.9d;
        this.q = 0.1d;
        this.r = 1.0d / (this.q * ((double) this.c));
        a(false);
    }

    public void b(double d2) {
        this.b = d2;
        this.j = (this.b - this.i) / (((double) this.c) * this.d);
    }

    public synchronized void c() {
        d();
        this.s = new b(this);
        this.s.start();
    }

    public void c(double d2) {
        this.d = d2;
    }

    public synchronized void d() {
        this.t = false;
        if (this.s != null) {
            try {
                this.s.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.s = null;
        }
        return;
    }
}
