package com.supersub.noamm_000.tincherfirsttry;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

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
 * Created by noamm_000 on 27/08/2016.
 */
public class PrincipalRegistrationAsyncTask extends AsyncTask<Principal,String,String> {

    Context context;
    AlertDialog.Builder builder;
    String userName;
    String password;

    PrincipalRegistrationAsyncTask(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(Principal... principals) {
        String principalRegisterPath = "http://supersubstitute2.tk/principalRegister.php";
        try {
            //publishProgress("Inside try");
            // Setting URL obj for current task
            URL pRURL = new URL(principalRegisterPath);
            // Setting httpUrlConnection from this URL
            HttpURLConnection httpURLConnection =  (HttpURLConnection) pRURL.openConnection();
            // Initial some settings for httpUrl object
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(4000);
            //publishProgress("after setting httpurl...");
            // Building write objects connected to httpUrl object
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Getting registration data
            Principal principal = principals[0];
            String schoolCity = principal.getSchoolCity().name();
            String schoolName = principal.getSchoolName().name();
            userName = principal.getUserName();
            password = principal.getPassword();
            // Encoding registration data
            //publishProgress("before encoding...");
            String postData = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(principal.getFirstName(),"UTF-8") + "&"
                    +URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(principal.getLastName(), "UTF-8") + "&"
                    +URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                    +URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                    +URLEncoder.encode("schoolCity", "UTF-8") + "=" + URLEncoder.encode(schoolCity, "UTF-8") + "&"
                    +URLEncoder.encode("schoolName", "UTF-8")+"="+URLEncoder.encode(schoolName, "UTF-8");

            // Sending post data
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();

            // Reading response from server
            InputStream is = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String response = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();

            return response;

        } catch (MalformedURLException e) {
            publishProgress("MalformedURLException ");
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
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Response From Server");

    }
    @Override
    protected void onPostExecute(String result) {
        if(result != null) {
            if (result.equals("Insert successful you can now log in")) {
                builder.setCancelable(false);
                builder.setMessage("ההרשמה בוצעה בהצלחה!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("userName", userName);
                        intent.putExtra("password", password);
                        intent.putExtra("userType", "principal");
                        context.startActivity(intent);
                    }
                });
            }
            else {
                builder.setMessage(result);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.PrincipalRegistrationActivity");
                        //context.startActivity(intent);
                    }
                });
            }
            builder.show();
        }


    }
    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(this.context, values[0], Toast.LENGTH_LONG).show();
    }
}
