package com.devyatochka.huaweiapp.AssynkTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.devyatochka.huaweiapp.Activity.SignupActivity;
import com.devyatochka.huaweiapp.Helper.RegistrationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupTask extends AsyncTask<Void, Void, String> {

    private String server = "http://devyatochka.fvds.ru:8888/register";

    private RegistrationRequest request;
    private ProgressDialog progressDialog;
    private SignupActivity activity;

    public SignupTask(RegistrationRequest request, SignupActivity activity) {
        this.request = request;
        this.activity = activity;
        this.progressDialog = new ProgressDialog(this.activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage("Registrating new user...");
        //progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(server);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            JSONObject body = new JSONObject();
            body.put("full_name",request.getFull_name());
            body.put("login",request.getLogin());
            body.put("password",request.getPassword());
            body.put("number_phone",request.getNumber_phone());
            body.put("model_phone",request.getModel_phone());

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(body.toString());
            wr.flush();

            InputStream in = new BufferedInputStream(con.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        } catch (JSONException e) {
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
