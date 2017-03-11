package com.example.noamm_000.talkwithcompviawifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    Button btnConnect, btnCloseSocket;
    EditText etIP;
    EditText etPort;
    RadioGroup rgAxis;
    RadioButton rbX;
    RadioButton rbY;
    RadioButton rbBoth;

    boolean isConnected = false;
    ConnectAndSendAsyncTask connectAndSendAsyncTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnCloseSocket = (Button) findViewById(R.id.btn_closeSocket);
        etIP = (EditText) findViewById(R.id.et_ip);
        etPort = (EditText) findViewById(R.id.et_port);
        rgAxis = (RadioGroup) findViewById(R.id.rg_axis);
        rbX = (RadioButton) findViewById(R.id.rb_x);
        rbY = (RadioButton) findViewById(R.id.rb_y);
        rbBoth = (RadioButton) findViewById(R.id.rb_both);
    }

    public void socketOperationRequest(View view) {
        if (!isConnected) {
            String mode = null;
            int selectedRbId = rgAxis.getCheckedRadioButtonId();
            if (selectedRbId == rbX.getId()) mode = "x";
            else if (selectedRbId == rbY.getId()) mode = "y";
            else mode = "both";
            connectAndSendAsyncTask = new ConnectAndSendAsyncTask(MainActivity.this, mode);
        }
        switch (view.getId()) {
            case R.id.btn_connect:
            {
                isConnected = true;
                String IP = etIP.getText().toString();
                String port = etPort.getText().toString();
                connectAndSendAsyncTask.execute(IP, port);
                btnConnect.setEnabled(false);
                btnCloseSocket.setEnabled(true);
                break;
            }
            case R.id.btn_closeSocket:
            {
                isConnected = false;
                connectAndSendAsyncTask.cancel(true);
                btnCloseSocket.setEnabled(false);
                btnConnect.setEnabled(true);
                break;
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //float leftClick = 1;
        //float rightClick = 2;
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            //connectAndSendAsyncTask.setKeyPressed(true, rightClick, false, true);
            connectAndSendAsyncTask.setKeyPressed(false, true);
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            //connectAndSendAsyncTask.setKeyPressed(true, leftClick, true, false);
            connectAndSendAsyncTask.setKeyPressed(true, false);
        }
        return super.onKeyDown(keyCode, event);
    }
}
