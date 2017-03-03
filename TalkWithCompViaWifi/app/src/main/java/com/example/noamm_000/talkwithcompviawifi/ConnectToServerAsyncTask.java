package com.example.noamm_000.talkwithcompviawifi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by noamm_000 on 25/02/2017.
 */
public class ConnectToServerAsyncTask extends AsyncTask<String,String,String> {

    Context context;
    Socket socket;
    OutputStream os;
    boolean running = true;

    public ConnectToServerAsyncTask(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String IP = strings[0];
        String port = strings[1];
        InputStream is = null;
        int x = 500;
        int y = 500;
        try {
            InetAddress inetIP = InetAddress.getByName(IP);
            socket = new Socket(inetIP, Integer.parseInt(port));
            os = socket.getOutputStream();
            publishProgress("Send packet");
            while(!this.isCancelled()){
                os.write(x);
                os.write(y);
                x++;
                y++;
                if (x == 1000) {
                    x = 500;
                    y = 500;
                }
                Thread.sleep(2);
            }

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
}
