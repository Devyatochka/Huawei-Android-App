package com.devyatochka.huaweiapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devyatochka.huaweiapp.Activity.MainMenuActivity;
import com.devyatochka.huaweiapp.R;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);


        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
    }
}
