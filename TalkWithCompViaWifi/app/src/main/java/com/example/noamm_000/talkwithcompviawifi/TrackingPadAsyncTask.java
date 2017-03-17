package com.example.noamm_000.talkwithcompviawifi;

import android.content.Context;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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

/**
 * This class handles the sending of touch events locations (X and Y) to the server. It does so by wrapping
 * the locations inside DataObject which is a shared class between the client & the server.
 */
public class TrackingPadAsyncTask extends AsyncTask<String,String,String> implements View.OnTouchListener {

    Context context;            // In order to make it possible to have Toasts
    RelativeLayout mainRl;      // A touch listener will be attached to the relative layout so we can get touch events
    Button btnConnect;          // Both buttons are transfered to this class so it can handle their setEnable state
    Button btnCancel;
    boolean isConnected = false;
    float prevX;                // In order to calculate dx and dy
    float prevY;
    Socket socket;
    ObjectOutputStream oos;     // As mentioned in class description, we are sending an object not a primitive type
    boolean isUpKeyClicked;     // Flags for saving the volume buttons clicks (which will be translated into mouse click at server)
    boolean isDownKeyClicked;
    //private final static String TAG = "ME ASYNCTASK LOGGER: "; // For logging data while debugging

    // Constructor
    public TrackingPadAsyncTask(Context context, RelativeLayout mainRl, Button btnConnect, Button btnCancel){
        this.context = context;
        this.mainRl = mainRl;
        this.mainRl.setOnTouchListener(this);
        this.btnConnect = btnConnect;
        this.btnCancel = btnCancel;

    }

    /**
     * The UI thread handles the volume buttons clicks, whenever there is a click - the function updates flag state
     * @param isUpKeyClicked
     * @param isDownKeyClicked
     */
    public void setKeysState(boolean isUpKeyClicked, boolean isDownKeyClicked) {
        this.isUpKeyClicked = isUpKeyClicked;
        this.isDownKeyClicked = isDownKeyClicked;
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
            InetAddress inetAddress = InetAddress.getByName(IP);
            socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, Integer.parseInt(port)), 2000);
            publishProgress("Connected!");
            OutputStream os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            while(!isCancelled()) {
                Thread.sleep(2000);
            }
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
            publishProgress("Interrupted exception occurred");
            e.printStackTrace();
        } catch (Exception e) {
            publishProgress("General exception occurred");
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
            oos.flush();
            oos.close();
            socket.close();
            MainActivity.isConnected = false;
            btnConnect.setEnabled(true);
            btnCancel.setEnabled(false);
            mainRl.setOnClickListener(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if((!isCancelled()) && (this.isConnected)) {
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    float dx = prevX - motionEvent.getX();
                    prevX = motionEvent.getX();
                    float dy = prevY - motionEvent.getY();
                    prevY = motionEvent.getY();
                    DataObject dataObject = new DataObject(dx,dy,this.isUpKeyClicked, this.isDownKeyClicked);
                    try {
                        oos.writeObject(dataObject);
                    } catch (IOException e) {
                        publishProgress("IO exception occurred");
                        e.printStackTrace();
                    }
                   break;
                }
                case MotionEvent.ACTION_DOWN:
                {
                    prevX = motionEvent.getX();
                    prevY = motionEvent.getY();
                    break;
                }
                // -10 is a just the number I chose (was available) to represent a "fake" event
                // which is event triggered by the UI thread in order to announce that there was a
                // click on one of the volume buttons and so in needs to be send to server asap
                case -10:
                {
                    DataObject dataObject = new DataObject(0,0,this.isUpKeyClicked, this.isDownKeyClicked);
                    this.isUpKeyClicked = false;
                    this.isDownKeyClicked = false;
                    try {
                        oos.writeObject(dataObject);
                    } catch (IOException e) {
                        publishProgress("IO exception occurred");
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return true;
    }

}
