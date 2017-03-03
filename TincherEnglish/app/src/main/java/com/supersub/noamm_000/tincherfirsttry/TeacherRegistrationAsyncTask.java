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
 * Created by noamm_000 on 13/08/2016.
 */
public class TeacherRegistrationAsyncTask extends AsyncTask<Teacher, String, String> {


    Context context;
    AlertDialog.Builder builder;
    String userName;
    String password;

    TeacherRegistrationAsyncTask(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(Teacher... params) {
        Teacher currentTeacher = params[0];
        userName = currentTeacher.getUserName();
        password = currentTeacher.getPassword();

        // For connection through Genymotion
        //String login_url = "http://192.168.56.1/supersub/teacherRegister.php";

        // For connection through real Android device on same Wi-Fi network
        // CMD -> IPCONFIG -> Wireless LAN adapter Wi - Fi -> IPv4 address
        String register_url = "http://supersubstitute2.tk/teacherRegister.php";

        try {
            URL url = new URL(register_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //publishProgress("after setting url connection");
            httpURLConnection.setReadTimeout(4000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // Throws IOException if not valid URL
            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String ageAsString = Integer.toString(currentTeacher.getAge());
            //String educationAsString = currentTeacher.getEducation().name();
            String educationAsString = MapsForEnums.educationToString.get(currentTeacher.getEducation());
            //String aoiAsString = currentTeacher.getAoi().name();
            String aoiAsString = MapsForEnums.aoiToString.get(currentTeacher.getAoi());
            //String schoolTypeAsString = currentTeacher.getSchoolType().name();
            String schoolTypeAsString = MapsForEnums.schoolTypeToString.get(currentTeacher.getSchoolType());
            String isEducationalDegreeAsNumString = currentTeacher.isEducationalDegree() ? "1" : "0";

            String post_data = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(currentTeacher.getFirstName(),"UTF-8") + "&"
                                +URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(currentTeacher.getLastName(), "UTF-8") + "&"
                                +URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                                +URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                                +URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(ageAsString, "UTF-8") + "&"
                                +URLEncoder.encode("education", "UTF-8")+"="+URLEncoder.encode(educationAsString, "UTF-8")+"&"
                                +URLEncoder.encode("aoi", "UTF-8")+"="+URLEncoder.encode(aoiAsString, "UTF-8")+"&"
                                +URLEncoder.encode("schoolType", "UTF-8") + "=" + URLEncoder.encode(schoolTypeAsString, "UTF-8")+"&"
                                +URLEncoder.encode("isEducationalDegree", "UTF-8") + "=" + URLEncoder.encode(isEducationalDegreeAsNumString, "UTF-8")+"&"
                                +URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(currentTeacher.getAddress(), "UTF-8")+"&"
                                +URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(currentTeacher.getMail(), "UTF-8")+"&"
                                +URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(currentTeacher.getPhone(), "UTF-8");

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
                        intent.putExtra("userType", "teacher");
                        context.startActivity(intent);
                    }
                });
            }
            else {
                builder.setMessage(result);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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