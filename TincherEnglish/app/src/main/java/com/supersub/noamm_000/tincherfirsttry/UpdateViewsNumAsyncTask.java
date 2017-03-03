package com.supersub.noamm_000.tincherfirsttry;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownServiceException;

/**
 * Created by noamm_000 on 02/09/2016.
 */
public class UpdateViewsNumAsyncTask extends AsyncTask<Teacher, String, String> {

    private Context context;
    private AlertDialog builder;

    public UpdateViewsNumAsyncTask(Context ctx) {
        context = ctx;

    }

    @Override
    protected String doInBackground(Teacher... teachers) {
        Teacher cTeacher = teachers[0];
        String username = cTeacher.getUserName();
        String viewsNum = Integer.toString(cTeacher.getViewsNum());
        String updateUrl = "http://supersubstitute2.tk/updateTeacherViewsNum.php";

        try {
            URL url = new URL(updateUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //publishProgress("after setting url connection");
            httpURLConnection.setReadTimeout(4000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // Throws IOException if not valid URL
            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));


            String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&"
                    +URLEncoder.encode("viewsNum", "UTF-8") + "=" + URLEncoder.encode(viewsNum, "UTF-8");


            bufferedWriter.write(post_data);

            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream is = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            publishProgress("MalformedURLException been thrown");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            publishProgress("File not found exception");
            e.printStackTrace();
        } catch (UnknownServiceException e) {
            publishProgress("Unknown service exception exception");
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            publishProgress("Connecting process timeout");
            e.printStackTrace();
        } catch (IOException e) {
            publishProgress("Problem with internet connection");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        builder = new AlertDialog.Builder(context).create();
        builder.setTitle("הודעה");
        if(s.equals("Update successful")) {
            builder.setMessage("המורה יקבל התראה שמנהל ביקש את מספר הטלפון שלו ולכן יצפה לקבלת שיחה");
            builder.show();
        }
        else {
            builder.setMessage("אירעה תקלה. אנא נסה שוב");
            builder.show();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
