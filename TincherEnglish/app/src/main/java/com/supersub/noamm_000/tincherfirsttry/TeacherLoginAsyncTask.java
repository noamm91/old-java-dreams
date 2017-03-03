package com.supersub.noamm_000.tincherfirsttry;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
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
 * Created by noamm_000 on 26/08/2016.
 */
public class TeacherLoginAsyncTask extends AsyncTask<String, String, String[]> {

    Context context;
    Button btnLogin;

    TeacherLoginAsyncTask(Context ctx, Button btnLogin) {
        this.context = ctx;
        this.btnLogin = btnLogin;
    }
    @Override
    protected String[] doInBackground(String... strings) {
        String userName = strings[0];
        String password = strings[1];

        // For connection through Genymotion
        //String login_url = "http://192.168.56.1/supersub/teacherLogin.php";

        // For connection through real Android device on same Wi-Fi network
        // CMD -> IPCONFIG -> Wireless LAN adapter Wi - Fi -> IPv4 address
        //String login_url = "http://10.0.0.10/supersub/teacherLogin.php";
        // For goDaddy server:
        String login_url = "http://supersubstitute2.tk/teacherLogin.php";
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(login_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            //httpURLConnection.setConnectTimeout(1000);
            // setReadTimeout method that will effect GetInputStream method below
            httpURLConnection.setReadTimeout(4000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                    + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream is = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String[] loginTeacher = new String[15];
            String serverResponse ="NoRespone";
            boolean firstIteration = true;
            loginTeacher[0] = userName;
            loginTeacher[1] = password;
            String line;
            int i = 2;
            // While for fetching current user data from DB and validation result
            // in cell no. 10 (loginTeacher[10]) the number of teachers in DB is stored
            while (((line = bufferedReader.readLine()) != null) && (!line.equals("end"))) {
                if(firstIteration) {
                    serverResponse = line;
                    if(serverResponse.equals("login not success")){
                    }
                    else {
                        firstIteration = false;
                    }
                }
                else {
                    loginTeacher[i] = line;
                    i++;
                }
            }

            loginTeacher[14] = serverResponse;

            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();

            return loginTeacher;

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
    protected void onPreExecute() {
        // Lock loginBtn of LoginActivity
        btnLogin.setEnabled(false);
    }

    @Override
    protected void onPostExecute(String[] loginTeacher) {
        if(loginTeacher != null) {
            if (loginTeacher[14].equals("login success")) {
                Toast.makeText(context, "Login success, Welcome " + loginTeacher[2], Toast.LENGTH_LONG).show();
                boolean isEducational = (loginTeacher[8].equals("1")) ? true : false;
                int viewsNum = Integer.parseInt(loginTeacher[12]);
                Teacher currentTeacher = new Teacher(loginTeacher[2], loginTeacher[3], loginTeacher[0],
                        loginTeacher[1], Integer.parseInt(loginTeacher[4]),
                        MapsForEnums.stringToEducation.get(loginTeacher[5]), MapsForEnums.stringToAOI.get(loginTeacher[6]),
                        MapsForEnums.stringToSchoolType.get(loginTeacher[7]), isEducational, loginTeacher[9], loginTeacher[10],
                        loginTeacher[11], viewsNum);


            // Release loginBtn of LoginActivity
                btnLogin.setEnabled(true);

                String userType = "Teacher";
                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.TeacherPersonalArea");
                intent.putExtra("userType", userType);
                intent.putExtra("currentTeacher", currentTeacher);

                context.startActivity(intent);
            }
            else {
                Toast.makeText(context, "Login failed, username or password incorrect", Toast.LENGTH_LONG).show();
                // Release loginBtn of LoginActivity
                btnLogin.setEnabled(true);
            }}
        else {
            Toast.makeText(context, "Login failed, no connection to server", Toast.LENGTH_LONG).show();
            // Release loginBtn of LoginActivity
            btnLogin.setEnabled(true);
        }
    }

}
