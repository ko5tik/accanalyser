package de.pribluda.android.accanalyzer;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends PreferenceActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(Configuration.class.getCanonicalName());
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}