package com.example.acer.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {
    CurrentFragmentEnum currentFragment;

    private SensorManager sensorManager;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setItemIconTintList(null);
            navigationView.setNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                // Set the grocery fragment as default screen
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GroceriesFragment()).commit();
                currentFragment=CurrentFragmentEnum.Groceries;
                //select the grocery navigation menu by default
                //navigationView.setCheckedItem(R.id.nav_view);
            }
        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //setTitle(currentFragment);
        if (Objects.equals(currentFragment,CurrentFragmentEnum.Groceries))
        {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
        }
        else {
            //Reference URL: https://stackoverflow.com/questions/10692755/how-do-i-hide-a-menu-item-in-the-actionbar
           for (int i=0; i < menu.size();i++)
           {
               menu.getItem(i).setVisible(false);
           }

            //return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnAddGrocery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AddGrocery()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            Intent i;

            switch(item.getItemId())
            {
                case  R.id.help :
                    i = new Intent(this, HelpActivity.class);
                    startActivity(i);
                    break;

                case R.id.call:
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + "9027896475"));
                    startActivity(dialIntent);
                    break;

                case R.id.logout:
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Logout Activity")
                            .setMessage("Are you sure you want to Logout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();

                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                    break;

                case  R.id.Settings:
                    i = new Intent(this, SettingsActivity.class);
                    startActivity(i);
                    break;

                case  R.id.myaccount:
                    i = new Intent(this, MyAccountActivity.class);
                    startActivity(i);
                    break;

                case R.id.recipes:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new RecipesFragment()).commit();
                    invalidateOptionsMenu();// now onCreateOptionsMenu(...) is called again
                    currentFragment = CurrentFragmentEnum.Recipes;
                    break;

                case R.id.groceries :
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new GroceriesFragment()).commit();
                    invalidateOptionsMenu();// now onCreateOptionsMenu(...) is called again
                    currentFragment = CurrentFragmentEnum.Groceries;
                    break;

                case R.id.Activity :
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new FitnessFragment()).commit();
                    invalidateOptionsMenu(); // now onCreateOptionsMenu(...) is called again
                    currentFragment = CurrentFragmentEnum.Activity;
                    break;

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }
        catch (Exception ex)
        {

        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        editor.putInt(getString(R.string.savedSteps), (int) event.values[0]);
        editor.commit();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        return;
    }

    public enum CurrentFragmentEnum
    {
        Groceries,
        Recipes,
        Activity
    }
}