package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends Activity {

    @InjectView(id = R.id.sampleRate)
    private SeekBar sampleRate;

    @InjectView(id = R.id.windowSize)
    private SeekBar windowSize;

    @InjectView(id = R.id.updateRate)
    private SeekBar updateRate;

    private static final Integer[] windowSizes = {16, 32 , 64 , 128, 256, 512, 1024};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //  wire views
        ViewInjector.startActivity(this);


    }
}