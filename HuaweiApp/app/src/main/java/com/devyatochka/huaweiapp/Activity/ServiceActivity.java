package com.devyatochka.huaweiapp.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.devyatochka.huaweiapp.R;

public class ServiceActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mToolBar = (Toolbar) findViewById(R.id.toolbarService);
        setSupportActionBar(mToolBar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
