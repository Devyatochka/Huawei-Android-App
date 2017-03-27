package com.devyatochka.huaweiapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devyatochka.huaweiapp.AssynkTask.SignupTask;
import com.devyatochka.huaweiapp.Helper.RegistrationRequest;
import com.devyatochka.huaweiapp.R;

import org.json.JSONException;
import org.json.JSONObject;


public class SignupActivity extends AppCompatActivity {

    private EditText fullnameText;
    private EditText loginText;
    private EditText passwordText;
    private EditText phonenumberText;
    private Spinner modelphone;
    private String choose_user;
    private Button signupButton;
    private TextView loginLink;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullnameText = (EditText)findViewById(R.id.input_fullname);
        loginText = (EditText)findViewById(R.id.input_login);
        passwordText = (EditText) findViewById(R.id.input_password);
        phonenumberText = (EditText) findViewById(R.id.input_number_phone);
        modelphone = (Spinner) findViewById(R.id.model_phone);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.model_phone, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelphone.setAdapter(adapter);
        modelphone.setSelection(1);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthorizationActivity.class);
                startActivity(intent);
            }
        });

        modelphone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.model_phone);
                choose_user = choose[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed("Login failed");
            return;
        }

        signupButton.setEnabled(false);


        String fullname = fullnameText.getText().toString();
        String login    = loginText.getText().toString();
        String password = passwordText.getText().toString();
        String phonenumber = phonenumberText.getText().toString();
        String modelphone = choose_user;

        RegistrationRequest request = new RegistrationRequest(fullname,login,password,phonenumber,modelphone);
        SignupTask task = new SignupTask(request,this);
        task.execute();

    }

    public void parseJson(String result) {
        try {
            JSONObject jObj = new JSONObject(result);
            if (jObj.getString("state").equals("error")) {
                onSignupFailed(jObj.getString("response"));
            }
            else if (jObj.getString("state").equals("ok")) {
                JSONObject response = jObj.getJSONObject("response");


                /*SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id", response.getInt("id"));
                editor.commit();*/

                SharedPreferences mySharedPreferences = getSharedPreferences("current_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putInt("id", response.getInt("id"));
                editor.apply();

                onSignupSuccess();
            }
        } catch (JSONException e) {
            onSignupFailed("500 Internal Error. Please try again later");
        }
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
    }

    public void onSignupFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String fullname = fullnameText.getText().toString();
        String login    = loginText.getText().toString();
        String password = passwordText.getText().toString();
        String phonenumber = phonenumberText.getText().toString();


        if (fullname.isEmpty() || fullname.length() < 3) {
            fullnameText.setError("at least 3 characters");
            valid = false;
        } else {
            fullnameText.setError(null);
        }

        if (login.isEmpty() || password.length() < 3 || password.length() > 30) {
            loginText.setError("between 3 and 30 alphanumeric characters");
            valid = false;
        } else {
            loginText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 50) {
            passwordText.setError("between 3 and 50 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (phonenumber.length() != 11 || phonenumber.charAt(0) != '8') {
            phonenumberText.setError("format telephone must match 8**********");
            valid = false;
        } else {
            phonenumberText.setError(null);
        }

        return valid;
    }

}
