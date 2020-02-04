package com.example.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView3;
    private TextView textView;
    private TextView textView2;

    private AidlInterface remote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                bindServie();
                break;
            case R.id.textView2:
               test2();
                break;
            case R.id.textView3:
                test3();
                break;
        }
    }

    public void test2(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    remote.test("2");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void test3(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    remote.test("3");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void bindServie(){
        Intent intent = new Intent(this,RemoteService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                remote = AidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },BIND_AUTO_CREATE);
    }
}
