package com.devyatochka.huaweiapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devyatochka.huaweiapp.AssynkTask.AuthorizationTask;
import com.devyatochka.huaweiapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthorizationActivity extends AppCompatActivity {

    private static final String TAG = "AuthorizationActivityActivity";

    private EditText loginText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        loginText = (EditText)findViewById(R.id.input_login);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        /*SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.id);
        long highScore = sharedPref.getInt("id", defaultValue);*/
        SharedPreferences sharedPreferences = getSharedPreferences("current_id", Context.MODE_PRIVATE);
        long id = sharedPreferences.getInt("id", -1);


        if (id != -1) {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void parseJson(String result) {
        try {
            JSONObject jObj = new JSONObject(result);
            if (jObj.getString("state").equals("error")) {
                onLoginFailed(jObj.getString("response"));
            }
            else if (jObj.getString("state").equals("ok")) {
                JSONObject response = jObj.getJSONObject("response");

                SharedPreferences sharedPreferences = getSharedPreferences("current_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putInt("id", response.getInt("id"));
                editor.apply();

                onLoginSuccess();
            }
        } catch (JSONException e) {
            onLoginFailed("500 Internal Error. Please try again later");
        }
    }

    public boolean validate() {
        boolean valid = true;

        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        if (login.isEmpty() || login.length() < 3 || login.length() > 30) {
            loginText.setError("between 3 and 30 alphanumeric characters");
            valid = false;
        } else {
            loginText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 50) {
            passwordText.setError("between 8 and 50 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void login() {
        if (!validate()) {
            onLoginFailed("Login or password aren't validate");
            return;
        }

        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        AuthorizationTask authTask = new AuthorizationTask(login,password,this);
        authTask.execute();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
    }

    public void onLoginFailed(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }
}