package com.devyatochka.huaweiapp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.devyatochka.huaweiapp.AssynkTask.SendEmailTask;
import com.devyatochka.huaweiapp.Helper.Mail;

import com.devyatochka.huaweiapp.Helper.Profile;
import com.devyatochka.huaweiapp.R;

/**
 * Created by alexbelogurow on 26.03.17.
 */

public class SendEmailActivity extends AppCompatActivity implements SendEmailTask.SendEmailResponse {

    private static final String SENDER = "devyatochkabmstu@gmail.com";
    private static final String PASSWORD = "E6f9H0a2";
    // TODO add "huawei.task@best-bmstu.ru" to getter
    private static final String[] GETTER = { "alexbelogur@yandex.ru" };

    private EditText subject;
    private EditText body;
    private Toolbar toolbar;
    private FloatingActionButton floatButton;
    private String bodyWithContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        toolbar = (Toolbar) findViewById(R.id.toolbarEmail);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        floatButton = (FloatingActionButton) findViewById(R.id.fab);
        subject = (EditText) findViewById(R.id.editTextSubject);
        body = (EditText) findViewById(R.id.editTextBody);

        final Profile profile = MainMenuActivity.profile;
        bodyWithContext = body.getText().toString();// + profile.toString();

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("email", body.getText().toString() + profile.toString());
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        final Profile profile = MainMenuActivity.profile;

        SendEmailTask emailTask = new SendEmailTask(new SendEmailTask.SendEmailResponse() {
            @Override
            public void processFinish(boolean result) {
                finish();
            }},
                new Mail(SENDER,
                        PASSWORD,
                        GETTER,
                        subject.getText().toString(),
                        body.getText().toString() + "\n\n" + profile.toString()),
                this);

        emailTask.execute();
    }

    public void displayMessage(String message) {
        Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void processFinish(boolean result) {
        finish();
    }


}
