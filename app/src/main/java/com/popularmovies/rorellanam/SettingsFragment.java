package com.popularmovies.rorellanam;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by Rorellanam on 9/14/16.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_sort_movies);

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        Preference currentPref = findPreference(getString(R.string.pref_sort_key));
        currentPref.setSummary(sharedPreferences.getString(getString(R.string.pref_sort_key), ""));
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_key))) {

            Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, ""));

            getActivity().finish();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

}
