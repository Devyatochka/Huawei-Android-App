package com.devyatochka.huaweiapp.AssynkTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.devyatochka.huaweiapp.Activity.MainMenuActivity;
import com.devyatochka.huaweiapp.Activity.SendEmailActivity;
import com.devyatochka.huaweiapp.Helper.Mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * Created by alexbelogurow on 26.03.17.
 */

public class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
    private Mail mail;
    private SendEmailActivity activity;
    private ProgressDialog progressDialog;


    public interface SendEmailResponse {
        void processFinish(boolean result);
    }

    public SendEmailResponse delegate = null;

    public SendEmailTask(SendEmailResponse delegate, Mail mail, SendEmailActivity activity){
        this.delegate = delegate;
        this.mail = mail;
        this.activity = activity;
        this.progressDialog = new ProgressDialog(this.activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage("Sending email");
        //progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            if (mail.send()) {
                activity.displayMessage("Email sent.");
            } else {
                activity.displayMessage("Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailTask.class.getName(), "Bad account details");
            e.printStackTrace();
            activity.displayMessage("Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailTask.class.getName(), "Email failed");
            e.printStackTrace();
            activity.displayMessage("Email failed to send.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            activity.displayMessage("Unexpected error occured.");
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        progressDialog.dismiss();

        new CountDownTimer(500, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                delegate.processFinish(true);
            }

        }.start();
    }
}