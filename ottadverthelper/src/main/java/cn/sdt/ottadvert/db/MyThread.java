package cn.sdt.ottadvert.db;

import android.os.Looper;
import android.os.Process;

/**
 * Created by SDT13411 on 2018/3/22.
 */

public class MyThread extends Thread {

    Looper mLooper;

    public MyThread() {
    }

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }
}
