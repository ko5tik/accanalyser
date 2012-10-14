package de.pribluda.android.accanalyzer;

import android.hardware.SensorManager;
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
     * shall set proper value for backing store upon setting
     */
    @Test
    public void testSampleRateSettings(@Mocked(methods = {"updateSampleRate", "<clinit>"}, inverse = true) final Settings settings,
                                       @Mocked final Configuration configuration) {



        Deencapsulation.setField(settings,"configuration", configuration);

        new Expectations() {
            {
                configuration.setSampleRate(SensorManager.SENSOR_DELAY_NORMAL);
                configuration.setSampleRate(SensorManager.SENSOR_DELAY_UI);
                configuration.setSampleRate(SensorManager.SENSOR_DELAY_GAME);
                configuration.setSampleRate(SensorManager.SENSOR_DELAY_FASTEST);

            }
        };

        Deencapsulation.invoke(settings, "updateSampleRate", 0);
        Deencapsulation.invoke(settings, "updateSampleRate", 1);
        Deencapsulation.invoke(settings, "updateSampleRate", 2);
        Deencapsulation.invoke(settings, "updateSampleRate", 3);
    }
}
