package com.devyatochka.huaweiapp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class SendEmailActivity extends AppCompatActivity {

    private static final String SENDER = "devyatochkabmstu@gmail.com";
    private static final String PASSWORD = "E6f9H0a2";
    // TODO add "huawei.task@best-bmstu.ru" to getter
    private static final String[] GETTER = { "alexbelogur@yandex.ru" };

    private EditText subject;
    private EditText body;
    private EditText recipient;
    private FloatingActionButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatButton = (FloatingActionButton) findViewById(R.id.fab);
        subject = (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);
        recipient = (EditText) findViewById(R.id.recipient);


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        SendEmailTask email = new SendEmailTask(
                new Mail(SENDER,
                        PASSWORD,
                        GETTER,
                        subject.getText().toString(),
                        body.getText().toString()),
                this);

        email.execute();
    }

    public void displayMessage(String message) {
        Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}