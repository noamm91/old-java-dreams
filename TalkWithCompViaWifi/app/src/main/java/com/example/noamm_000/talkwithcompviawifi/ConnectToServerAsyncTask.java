package com.example.noamm_000.talkwithcompviawifi;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by noamm_000 on 25/02/2017.
 */
public class ConnectToServerAsyncTask extends AsyncTask<String,String,String> implements SensorEventListener {

    int times = 0;
    Context context;
    Socket socket;
    OutputStream os;
    byte[] floatValX = new byte[4];
    byte[] floatValY = new byte[4];
    byte[] floatXY = new byte[8];
    final static String TAG = "ASYNCTASK LOGGER: ";

    public ConnectToServerAsyncTask(Context ctx) {
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... strings) {

        String IP = strings[0];
        String port = strings[1];
        try {
            InetAddress inetIP = InetAddress.getByName(IP);
            socket = new Socket(inetIP, Integer.parseInt(port));
            os = socket.getOutputStream();
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
            float data = sensorEvent.values[0];
            //float data2 = sensorEvent.values[1];
            data *= 16;
            //data2 *= 16;
            floatValY = ByteBuffer.allocate(4).putFloat(data).array();
            //floatValX = ByteBuffer.allocate(4).putFloat(data2).array();
            //System.arraycopy(floatValY,0,floatXY,0,4);
            //System.arraycopy(floatValY,0,floatXY,4,4);
            Log.d(TAG , Float.toString(data));
            try {
                os.write(floatValY);
                //os.write(floatValX);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
