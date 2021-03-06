package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.hardware.SensorManager;
import android.widget.SeekBar;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

/**
 * test methods of settings activity
 *
 * @author Konstantin Pribluda
 */
public class SettingsTest {




    /**
     * on resume shall retrieve values from configuration and set proper slider values
     */
    @Test
    public void testResuming(@Mocked(methods = {"onResume", "<clinit>"}, inverse= true) final Settings settings,
                             // mock away underlying activity
                             @Mocked Activity activity,
                             @Mocked final Configuration configuration,
                             @Mocked final SeekBar sensorDelayBar,
                             @Mocked final SeekBar windowSizeBar,
                             @Mocked final SeekBar updateRateBar) {

        Deencapsulation.setField(settings, "configuration", configuration);
        Deencapsulation.setField(settings, "sensorDelayBar", sensorDelayBar);
        Deencapsulation.setField(settings, "windowSizeBar", windowSizeBar);
        Deencapsulation.setField(settings, "updateRateBar", updateRateBar);

        new Expectations() {
            {

                configuration.getSensorDelay();
                returns(SensorManager.SENSOR_DELAY_GAME);

                sensorDelayBar.setProgress(2);


                configuration.getWindowSize();
                returns(128);
                windowSizeBar.setProgress(3);


                configuration.getUpdateRate();
                returns(4000);
                updateRateBar.setProgress(3);

            }
        };

        Deencapsulation.invoke(settings, "onResume");

    }


    /**
     * shall retrieve and save proper values into configuration upon pausing.
     * configuuration shall be persisted
     *
     * @param settings
     * @param configuration
     * @param sensorDelayBar
     * @param windowSizeBar
     * @param updateRateBar
     */
    @Test
    public void testSavingOnPause(@Mocked(methods = {"onPause", "<clinit>"}, inverse = true) final Settings settings,
                                  // mock away underlying activity
                                  @Mocked Activity activity,
                                  @Mocked final Configuration configuration,
                                  @Mocked final SeekBar sensorDelayBar,
                                  @Mocked final SeekBar windowSizeBar,
                                  @Mocked final SeekBar updateRateBar) {

        Deencapsulation.setField(settings, "configuration", configuration);
        Deencapsulation.setField(settings, "sensorDelayBar", sensorDelayBar);
        Deencapsulation.setField(settings, "windowSizeBar", windowSizeBar);
        Deencapsulation.setField(settings, "updateRateBar", updateRateBar);

        new Expectations() {
            {

                sensorDelayBar.getProgress(); returns(3);
                configuration.setSensorDelay(SensorManager.SENSOR_DELAY_FASTEST);


                windowSizeBar.getProgress(); returns(4);
                configuration.setWindowSize(256);


                updateRateBar.getProgress(); returns(4);
                configuration.setUpdateRate(5000);

                configuration.save(settings);
            }
        };

        Deencapsulation.invoke(settings, "onPause");
    }
}
