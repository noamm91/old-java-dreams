package com.example.noamm_000.talkwithcompviawifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainRl;
    Button btnConnect, btnCloseSocket;
    EditText etIP;
    EditText etPort;
    RadioGroup rgAxis;
    RadioButton rbBoth;
    RadioButton rbTp;

    public static boolean isConnected = false;
    String mode = null;
    ConnectAndSendAsyncTask connectAndSendAsyncTask = null;
    TrackingPadAsyncTask trackingPadAsyncTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnCloseSocket = (Button) findViewById(R.id.btn_closeSocket);
        if (btnCloseSocket != null) {
            btnCloseSocket.setEnabled(false);
        }
        etIP = (EditText) findViewById(R.id.et_ip);
        etPort = (EditText) findViewById(R.id.et_port);
        rgAxis = (RadioGroup) findViewById(R.id.rg_axis);
        rbBoth = (RadioButton) findViewById(R.id.rb_both);
        rbTp = (RadioButton) findViewById(R.id.rb_tp);
        mainRl = (RelativeLayout) findViewById(R.id.main_rl);
    }

    /**
     * This function is basically a listener to the 2 buttons that are responsible of triggering network operations.
     * @param view - one of the two buttons: connect & close socket.
     */
    public void socketOperationRequest(View view) {
        if (!isConnected) {
            // Extracting the requested mode out of the RadioGroup, an asyncTask will be chosen based on this mode
            int selectedRbId = rgAxis.getCheckedRadioButtonId();
            if(selectedRbId == rbTp.getId()) {
                mode = "tp"; // tp stands for touch pad
                trackingPadAsyncTask = new TrackingPadAsyncTask(MainActivity.this, mainRl, btnConnect, btnCloseSocket);
            }
            else {
                if (selectedRbId == rbBoth.getId()) mode = "both";
                connectAndSendAsyncTask = new ConnectAndSendAsyncTask(MainActivity.this, mode, btnConnect, btnCloseSocket);
            }
        }
        switch (view.getId()) {
            case R.id.btn_connect:
            {
                isConnected = true;
                String IP = etIP.getText().toString();
                String port = etPort.getText().toString();
                if(mode.equals("tp"))trackingPadAsyncTask.execute(IP,port);
                else connectAndSendAsyncTask.execute(IP, port);
                break;
            }
            case R.id.btn_closeSocket:
            {
                isConnected = false;
                if(mode.equals("tp")) trackingPadAsyncTask.cancel(true);
                else connectAndSendAsyncTask.cancel(true);
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if(mode.equals("tp")) {
                trackingPadAsyncTask.setKeysState(false, true);
                MotionEvent motionEvent = MotionEvent.obtain(1,1,-10,0,0,0);
                mainRl.dispatchTouchEvent(motionEvent);
            }
            else connectAndSendAsyncTask.setKeyPressed(false, true);
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if(mode.equals("tp")){
                trackingPadAsyncTask.setKeysState(true, false);
                MotionEvent motionEvent = MotionEvent.obtain(1,1,-10,0,0,0);
                mainRl.dispatchTouchEvent(motionEvent);
            }
            else connectAndSendAsyncTask.setKeyPressed(true, false);
        }
        return super.onKeyDown(keyCode, event);
    }
}
