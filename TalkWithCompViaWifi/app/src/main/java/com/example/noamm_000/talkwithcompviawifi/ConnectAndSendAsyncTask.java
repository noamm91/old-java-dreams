package com.example.noamm_000.talkwithcompviawifi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.noamm_000.talkwithcompviawifi.CommonServerAndClient.DataObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by noamm_000 on 25/02/2017.
 */
public class ConnectAndSendAsyncTask extends AsyncTask<String,String,String> implements SensorEventListener {

    //private DataObject dataObject;
    private ObjectOutputStream objectOutputStream;
    private boolean isUpCliked;
    private boolean isDownClicked;
    private String mode;
    //private boolean keyPressed;
    private float UoD;
    private Context context;
    private Socket socket;
    private OutputStream os;
    //private byte[] floatValX = new byte[4];
    //private byte[] floatValY = new byte[4];
    //private byte[] floatValPressed = new byte[4];
    private final static String TAG = "ASYNCTASK LOGGER: ";

    public ConnectAndSendAsyncTask(Context ctx, String mode) {
        this.context = ctx;
        this.mode = mode;
        //keyPressed = false;
    }

    /*
    public void setKeyPressed(boolean keyPressed, float UorD, boolean isUpCliked, boolean isDownClicked) {
        //this.keyPressed = keyPressed;
        this.UoD = UorD;
        this.isUpCliked = isUpCliked;
        this.isDownClicked = isDownClicked;
    }
    */

    public void setKeyPressed(boolean isUpCliked, boolean isDownClicked) {
        this.isUpCliked = isUpCliked;
        this.isDownClicked = isDownClicked;
    }

    @Override
    protected String doInBackground(String... strings) {

        String IP = strings[0];
        String port = strings[1];
        //String mode = strings[2];
        //this.mode = mode;
        try {
            InetAddress inetIP = InetAddress.getByName(IP);
            socket = new Socket(inetIP, Integer.parseInt(port));
            os = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(os);
            publishProgress("Send packet");

            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);

            while (!this.isCancelled()) {
                Thread.sleep(500);
            }
            sensorManager.unregisterListener(this, gyro);

        } catch (UnknownHostException e) {
            publishProgress("Exception");
            e.printStackTrace();
        } catch (IOException e) {
            publishProgress("Exception2");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String message = values[0];
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        try {
            os.flush();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (!this.isCancelled()) {
            /*
            if(mode.equals("y")) {
                float data = sensorEvent.values[0];
                data *= 16;
                floatValY = ByteBuffer.allocate(4).putFloat(data).array();
                floatValPressed = ByteBuffer.allocate(4).putFloat(UoD).array();
                Log.d(TAG , Float.toString(data));
                try {
                    if(!keyPressed) {
                        os.write(floatValY);
                    }
                    else {
                        keyPressed = false;
                        os.write(floatValPressed);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(mode.equals("x")) {
                float data = sensorEvent.values[1];
                data *= 16;
                floatValX = ByteBuffer.allocate(4).putFloat(data).array();
                floatValPressed = ByteBuffer.allocate(4).putFloat(UoD).array();
                Log.d(TAG, Float.toString(data));
                try {
                    if (!keyPressed) {
                        os.write(floatValX);
                    } else {
                        keyPressed = false;
                        os.write(floatValPressed);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            if(mode.equals("both")) {
                DataObject dataObject = new DataObject(sensorEvent.values[1]*16, sensorEvent.values[0]*16, this.isUpCliked, this.isDownClicked);
                this.isUpCliked = false;
                this.isDownClicked = false;
                try {
                    objectOutputStream.writeObject(dataObject);
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
