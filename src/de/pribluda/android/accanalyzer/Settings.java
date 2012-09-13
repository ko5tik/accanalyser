package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends Activity {

    @InjectView(id = R.id.sampleRateSpinner)
    private Spinner sampleRate;

    @InjectView(id = R.id.windowSizeSpinner)
    private Spinner windowSize;

    @InjectView(id = R.id.updateRateSpinner)
    private Spinner updateRate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ViewInjector.startActivity(this);


    }
}