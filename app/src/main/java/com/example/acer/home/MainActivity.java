package com.example.acer.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SensorEventListener {

    SensorManager sensorManager;
    static int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerStepSensor(sensorManager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton grocery = (FloatingActionButton) findViewById(R.id.grocery);
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_button) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.help) {
            Intent i = new Intent(this, HelpActivity.class);
            startActivity(i);
        } else if (id == R.id.call) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" +"9027896475"));
            startActivity(dialIntent);
        } else if (id == R.id.logout) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logout Activity")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();

                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }
        else if (id == R.id.Settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.myaccount) {
            Intent i = new Intent(this, MyAccountActivity.class);
            startActivity(i);
        } else if (id == R.id.recipes) {
            Intent i = new Intent(this, RecepiesActivity.class);
            startActivity(i);
        }
        else if (id == R.id.groceries) {
            Intent i = new Intent(this, GroceriesActivity.class);
            startActivity(i);

        }
        else if (id == R.id.Activity) {
            Intent i = new Intent(this, FitnessActivity.class);
            this.startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void registerStepSensor(SensorManager sensorManager){
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        this.stepCount = (int) event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}