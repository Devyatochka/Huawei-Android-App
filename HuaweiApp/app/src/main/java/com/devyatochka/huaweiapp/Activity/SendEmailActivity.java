package com.devyatochka.huaweiapp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.devyatochka.huaweiapp.AssynkTask.SendEmailTask;
import com.devyatochka.huaweiapp.Helper.Mail;

import com.devyatochka.huaweiapp.R;

/**
 * Created by alexbelogurow on 26.03.17.
 */

public class SendEmailActivity extends AppCompatActivity implements SendEmailTask.SendEmailResponse {

    private static final String SENDER = "admin@devyatochka.fvds.ru";
            //"devyatochkabmstu@gmail.com";
    private static final String PASSWORD = "E6f9H0a2";
    // TODO add "huawei.task@best-bmstu.ru" to getter
    private static final String[] GETTER = { "alexbelogur@yandex.ru" };

    private EditText subject;
    private EditText body;
    private EditText recipient;
    private Toolbar toolbar;
    private FloatingActionButton floatButton;

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


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        SendEmailTask emailTask = new SendEmailTask(new SendEmailTask.SendEmailResponse() {
            @Override
            public void processFinish(boolean result) {
                finish();
            }},
                new Mail(SENDER,
                        PASSWORD,
                        GETTER,
                        subject.getText().toString(),
                        body.getText().toString()),
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
