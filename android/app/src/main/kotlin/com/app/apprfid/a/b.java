package com.app.apprfid.a;

import android.media.AudioTrack;
import android.util.Log;

class b extends Thread {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        this.a.t = true;
        setPriority(10);
        int minBufferSize = AudioTrack.getMinBufferSize(this.a.a(), 4, 2);
        AudioTrack audioTrack = new AudioTrack(3, this.a.a(), 4, 2, minBufferSize, 1);
        short[] sArr = new short[minBufferSize];
        int min = Math.min(480, minBufferSize);
        this.a.a(sArr, min);
        int write = audioTrack.write(sArr, 0, min);
        audioTrack.play();
        while (this.a.t) {
            if (write != min) {
                Log.v("TGen", String.format("Not all samples written (%d/%d)", new Object[]{Integer.valueOf(write), Integer.valueOf(min)}));
                write = audioTrack.write(sArr, write, min - write);
            }
            this.a.a(sArr, min);
            if (this.a.t) {
                write = audioTrack.write(sArr, 0, min);
            }
        }
        audioTrack.stop();
        audioTrack.release();
    }
}
