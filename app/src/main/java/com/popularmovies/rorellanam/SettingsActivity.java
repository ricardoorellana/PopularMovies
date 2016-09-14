package com.popularmovies.rorellanam;

import android.app.Activity;


import android.os.Bundle;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Display fragment as main content
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

}
