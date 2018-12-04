package com.example.acer.home;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setTitle(getResources().getString(R.string.help));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorAccent)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


}
