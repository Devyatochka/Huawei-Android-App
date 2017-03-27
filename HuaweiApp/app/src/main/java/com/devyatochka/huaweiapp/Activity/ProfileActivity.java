package com.devyatochka.huaweiapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devyatochka.huaweiapp.Helper.Profile;
import com.devyatochka.huaweiapp.R;

/**
 * Created by alexbelogurow on 28.03.17.
 */

public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView mTextViewLogin;
    private TextView mTextViewFullName;
    private TextView mTextViewPassword;
    private TextView mTextViewNumberPhone;
    private TextView mTextViewModelPhone;
    private Button mButtonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolBar = (Toolbar) findViewById(R.id.toolbarProfile);
        mTextViewLogin = (TextView) findViewById(R.id.textViewProfileLogin);
        mTextViewFullName = (TextView) findViewById(R.id.textViewProfileFullName);
        mTextViewPassword = (TextView) findViewById(R.id.textViewProfilePassword);
        mTextViewNumberPhone = (TextView) findViewById(R.id.textViewProfileNumberPhone);
        mTextViewModelPhone = (TextView) findViewById(R.id.textViewPorfileModelPhone);
        mButtonExit = (Button) findViewById(R.id.buttonProfileExit);

        setSupportActionBar(mToolBar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Profile profile = MainMenuActivity.profile;
        Log.i("profile", profile.toString());

        mTextViewLogin.setText(profile.getLogin());
        mTextViewFullName.setText(profile.getFullName());
        mTextViewPassword.setText(profile.getPassword());
        mTextViewNumberPhone.setText(profile.getNumberPhone());
        mTextViewModelPhone.setText(profile.getModelPhone());

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("current_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putInt("id", -1);
                editor.apply();

                Intent exit = new Intent(getApplicationContext(), AuthorizationActivity.class);
                startActivity(exit);
            }
        });
    }
}
