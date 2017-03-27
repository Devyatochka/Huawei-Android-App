package com.devyatochka.huaweiapp.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    }
}
