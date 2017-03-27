package com.devyatochka.huaweiapp.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.devyatochka.huaweiapp.AssynkTask.ProfileTask;
import com.devyatochka.huaweiapp.Helper.Profile;
import com.devyatochka.huaweiapp.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexbelogurow on 26.03.17.
 */

public class MainMenuActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    public static Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        createNavigationDrawer();


        SharedPreferences sharedPreferences = getSharedPreferences("current_id", Context.MODE_PRIVATE);
        long id = sharedPreferences.getInt("id", -1);
        Log.i("id_mainMenu", id+"");
        ProfileTask task = new ProfileTask((int)(long)id, this);
        task.execute();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }

    private void createNavigationDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bmstu)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Huawei")
                                .withIcon(R.drawable.huawei)
                                .withDisabledTextColor(7)
                )
                /*.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                }) */
                .withSelectionListEnabledForSingleProfile(false)
                .build();


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_item_profile)
                .withIcon(R.drawable.ic_profile)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent getProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(getProfile);

                        return false;
                    }
                });


        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.drawer_item_news)
                .withIcon(R.drawable.ic_news);

        SecondaryDrawerItem item3 = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.drawer_item_support)
                .withIcon(R.drawable.ic_feedback)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent sendEmail = new Intent(getApplicationContext(), SendEmailActivity.class);
                        startActivity(sendEmail);


                        return false;
                    }
                });





        //create the drawer and remember the `Drawer` result object
        final Drawer drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolBar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .withSelectedItem(2)
                .build();
    }

    public void parseJson(String result) {
        String fullName,
                login,
                password,
                numberPhone,
                modelPhone;
        long id;

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject response = jsonObject.getJSONObject("response");
            Log.i("id_json", response.toString());

            fullName = response.getString("full_name");
            login = response.getString("login");
            password = response.getString("password");
            numberPhone = response.getString("number_phone");
            modelPhone = response.getString("model_phone");
            id = response.getLong("id");

            profile = new Profile(fullName, login, password, numberPhone, modelPhone, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
