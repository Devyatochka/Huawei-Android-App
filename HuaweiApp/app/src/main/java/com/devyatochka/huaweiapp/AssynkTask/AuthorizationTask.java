package com.devyatochka.huaweiapp.AssynkTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.devyatochka.huaweiapp.Activity.AuthorizationActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AuthorizationTask extends AsyncTask<Void, Void, String> {

    private String server = "http://devyatochka.fvds.ru:8888/auth?";

    private ProgressDialog progressDialog;
    private AuthorizationActivity activity;

    public AuthorizationTask(String login, String password, AuthorizationActivity activity) {
        server += ("login=" + login);
        server += ("&password=" + password);
        this.activity = activity;
        this.progressDialog = new ProgressDialog(this.activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage("Authenticating...");
        //progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(server);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();

        this.activity.parseJson(result);
    }
}
