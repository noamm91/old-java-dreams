package com.example.noamm_000.talkwithcompviawifi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.noamm_000.talkwithcompviawifi.CommonServerAndClient.DataObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by noamm_000 on 25/02/2017.
 */
public class ConnectAndSendAsyncTask extends AsyncTask<String,String,String> implements SensorEventListener {

    //private DataObject dataObject;
    private Button btnConnect;
    private Button btnCancel;
    private ObjectOutputStream objectOutputStream;
    private boolean isUpCliked;
    private boolean isDownClicked;
    private String mode;
    private boolean isConnected;
    private float UoD;
    private Context context;
    private Socket socket;
    private OutputStream os;
    private final static String TAG = "GYRO ASYNCTASK LOGGER: ";

    public ConnectAndSendAsyncTask(Context ctx, String mode, Button btnConnect, Button btnCancel) {
        this.context = ctx;
        this.mode = mode;
        this.btnConnect = btnConnect;
        this.btnCancel = btnCancel;
    }

    public void setKeyPressed(boolean isUpCliked, boolean isDownClicked) {
        this.isUpCliked = isUpCliked;
        this.isDownClicked = isDownClicked;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        btnConnect.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    @Override
    protected String doInBackground(String... strings) {

        String IP = strings[0];
        String port = strings[1];
        try {
            InetAddress inetIP = InetAddress.getByName(IP);
            socket = new Socket();
            socket.connect(new InetSocketAddress(inetIP, Integer.parseInt(port)), 2000);
            publishProgress("Connected!");
            os = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(os);

            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);

            while (!this.isCancelled()) {
                Thread.sleep(2000);
            }
            sensorManager.unregisterListener(this, gyro);

        } catch (UnknownHostException e) {
            publishProgress("Unknown host exception occurred");
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            publishProgress("Socket timeout exception occurred");
            e.printStackTrace();
        } catch (ConnectException e) {
            publishProgress("Connect exception occurred");
            e.printStackTrace();
        } catch (IOException e) {
            publishProgress("IO exception occurred");
            e.printStackTrace();
        } catch (InterruptedException e) {
            publishProgress("Interrupted Exception occurred");
            e.printStackTrace();
        } catch (Exception e) {
            publishProgress("General Exception occurred");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String message = values[0];
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
        if(message.equals("Connected!")) {
            btnCancel.setEnabled(true);
            btnConnect.setEnabled(false);
            this.isConnected = true;
        }
        if(message.contains("exception")) {
            btnConnect.setEnabled(true);
            MainActivity.isConnected = false;
            this.isConnected = false;
        }
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        try {
            Log.d(TAG, "INSIDE ON CANCELED METHOD");
            os.flush();
            os.close();
            socket.close();
            MainActivity.isConnected = false;
            btnConnect.setEnabled(true);
            btnCancel.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if((!isCancelled()) && (this.isConnected)) {
            if(mode.equals("both")) {
                DataObject dataObject = new DataObject(sensorEvent.values[1]*16, sensorEvent.values[0]*16, this.isUpCliked, this.isDownClicked);
                this.isUpCliked = false;
                this.isDownClicked = false;
                try {
                    objectOutputStream.writeObject(dataObject);
                    Log.d(TAG, Float.toString(dataObject.getxDelta()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
