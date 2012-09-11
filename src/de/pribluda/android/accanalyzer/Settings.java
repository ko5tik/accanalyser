package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.os.Bundle;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
}