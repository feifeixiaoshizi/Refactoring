package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {
    private String TAG = "RemoteService";
    @Override
    public IBinder onBind(Intent intent) {
        AidlInterface.Stub stub = new AidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                                   double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void test(String content) throws RemoteException {
                for(int i=0;i<100;i++){
                    try {
                        Thread.currentThread().sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG,"threadName:"+Thread.currentThread().getName()+"content:"+content+"i:"+i);
                }

            }
        };
        return stub;
    }
}
