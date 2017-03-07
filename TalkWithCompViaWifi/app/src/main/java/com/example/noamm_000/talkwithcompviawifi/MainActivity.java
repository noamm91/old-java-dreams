package com.example.noamm_000.talkwithcompviawifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Button btnConnect, btnCloseSocket;
    EditText etIP;
    EditText etPort;
    boolean isConnected = false;
    ConnectToServerAsyncTask connectToServerAsyncTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnCloseSocket = (Button) findViewById(R.id.btn_closeSocket);
    }

    public void socketOperationRequest(View view) {
        if (!isConnected) {
            connectToServerAsyncTask = new ConnectToServerAsyncTask(MainActivity.this);
        }
        switch (view.getId()) {
            case R.id.btn_connect:
            {
                isConnected = true;
                etIP = (EditText) findViewById(R.id.et_ip);
                etPort = (EditText) findViewById(R.id.et_port);
                String IP = etIP.getText().toString();
                String port = etPort.getText().toString();
                connectToServerAsyncTask.execute(IP, port);
                btnConnect.setEnabled(false);
                btnCloseSocket.setEnabled(true);
                break;
            }
            case R.id.btn_closeSocket:
            {
                isConnected = false;
                connectToServerAsyncTask.cancel(true);
                btnCloseSocket.setEnabled(false);
                btnConnect.setEnabled(true);
                break;
            }
        }

    }
}
