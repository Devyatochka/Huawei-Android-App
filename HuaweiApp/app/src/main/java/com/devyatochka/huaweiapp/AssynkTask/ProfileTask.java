package com.devyatochka.huaweiapp.AssynkTask;

import android.os.AsyncTask;

import com.devyatochka.huaweiapp.Activity.MainMenuActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 27.03.2017.
 */

public class ProfileTask extends AsyncTask<Void,Void,String> {
    private String server = "http://devyatochka.fvds.ru:8888/user?";

    private MainMenuActivity activity;

    public ProfileTask(Integer id, MainMenuActivity activity) {
        server += ("id=" + id);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        // To do in parse JSON //
        //this.activity.parseJson(result);
    }
}
