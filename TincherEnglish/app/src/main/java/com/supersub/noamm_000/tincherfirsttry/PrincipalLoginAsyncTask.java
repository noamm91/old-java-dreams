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
 * Created by noamm_000 on 13/08/2016.
 */
public class PrincipalLoginAsyncTask extends AsyncTask<String, String, PrincipalAndTeachersPacket> {

    Context context;
    Button btnLogin;

    PrincipalLoginAsyncTask(Context ctx, Button btnLogin) {
        this.context = ctx;
        this.btnLogin = btnLogin;
    }
    @Override
    protected PrincipalAndTeachersPacket doInBackground(String... params) {

        String userName = params[0];
        String password = params[1];

        // For connection through Genymotion
        //String login_url = "http://192.168.56.1/supersub/principalLogin.php";

        // For connection through real Android device on same Wi-Fi network
        // CMD -> IPCONFIG -> Wireless LAN adapter Wi - Fi -> IPv4 address
        // For Wamp server:
        //String login_url = "http://10.0.0.10/supersub/principalLogin.php";
        // For goDaddy server:
        String login_url = "http://supersubstitute2.tk//principalLogin.php";
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


                // Throws IOException if not valid URL
                OutputStream os = httpURLConnection.getOutputStream();

                //publishProgress("URL is Valid");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream is = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String[] loginPrincipal = new String[8];
                String serverResponse ="NoRespone";
                boolean firstIteration = true;
                loginPrincipal[0] = userName;
                loginPrincipal[1] = password;
                String line;
                int i = 2;
                // While for fetching current user data from DB and validation result
                // in cell no. 10 (loginPrincipal[10]) the number of teachers in DB is stored
                while (((line = bufferedReader.readLine()) != null) && (!line.equals("end"))) {
                    if(firstIteration) {
                        serverResponse = line;
                        if(serverResponse.equals("login not success")){
                            loginPrincipal[7] = serverResponse;
                            return new PrincipalAndTeachersPacket(loginPrincipal, null);
                        }
                        else {
                            firstIteration = false;
                        }
                    }
                    else {
                        loginPrincipal[i] = line;
                        i++;
                    }
                }
                // Initial teachers array in the size of number of teachers in DB (stored in cell no. 6)
                Teacher[] teachers = new Teacher[Integer.parseInt(loginPrincipal[6])];
                // While for fetching all teachers data from teachers DB

                // TeacherDetails will store all 4 details that being echo from server a (first, last, age ,aoi)
                int k = 0;
                String[] teacherDetails = new String[13];

                    for(int j = 0; j < Integer.parseInt(loginPrincipal[6]); j++) {
                        while ((k < 13) && ((line = bufferedReader.readLine()) != null)) {
                            teacherDetails[k] = line;
                            k++;
                        }
                        k = 0;
                        // Building a teacher using all 13 fields that came from server and stored in teacherDetails
                        int cAge = Integer.parseInt(teacherDetails[4]);
                        Teacher.Education cEducation = MapsForEnums.stringToEducation.get(teacherDetails[5]);
                        Teacher.AOI cAoi = MapsForEnums.stringToAOI.get(teacherDetails[6]);
                        Teacher.SchoolType cSchoolType = MapsForEnums.stringToSchoolType.get(teacherDetails[7]);
                        boolean cIsEducationalDegree = (teacherDetails[8].equals("1") ? true : false);
                        int viewsNum = Integer.parseInt(teacherDetails[12]);
                        teachers[j] = new Teacher(teacherDetails[0], teacherDetails[1],teacherDetails[2],
                                                    teacherDetails[3], cAge, cEducation, cAoi, cSchoolType,
                                                    cIsEducationalDegree, teacherDetails[9], teacherDetails[10],
                                                    teacherDetails[11], viewsNum);
                }

                loginPrincipal[7] = serverResponse;

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();

                PrincipalAndTeachersPacket cPrincipalAndTeachers = new PrincipalAndTeachersPacket(loginPrincipal, teachers);

                return cPrincipalAndTeachers;

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
    protected void onPostExecute(PrincipalAndTeachersPacket cPrincipalAndTeachers) {
        if (cPrincipalAndTeachers != null) {

            String[] loginPrincipal = cPrincipalAndTeachers.getCPrincipal();
            if (loginPrincipal[7].equals("login success")) {
                Toast.makeText(context, "Login success, Welcome " + loginPrincipal[2], Toast.LENGTH_LONG).show();
                Principal currentPrincipal = new Principal(loginPrincipal[2], loginPrincipal[3], loginPrincipal[0],
                        loginPrincipal[1],
                        Principal.SchoolCity.valueOf(loginPrincipal[4]),
                        Principal.SchoolName.valueOf(loginPrincipal[5]));

                // Release loginBtn of LoginActivity
                btnLogin.setEnabled(true);

                Intent intent = new Intent("com.example.noamm_000.tincherfirsttry.PrincipalMainActivity");
                intent.putExtra("currentPrincipal", currentPrincipal);
                intent.putExtra("allTeachers", cPrincipalAndTeachers.getAllTheRest());
                intent.putExtra("numOfTeachers", loginPrincipal[6]);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Login failed, username or password incorrect", Toast.LENGTH_LONG).show();
                // Release loginBtn of LoginActivity
                btnLogin.setEnabled(true);
            }
        }
        else {
            Toast.makeText(context, "Login failed, no connection to server", Toast.LENGTH_LONG).show();
            // Release loginBtn of LoginActivity
            btnLogin.setEnabled(true);
        }
    }


/*
    @Override
    protected void onProgressUpdate(String... string) {
        String s = string[0];
        Toast.makeText(context, string[0], Toast.LENGTH_LONG).show();
    }
*/
}
