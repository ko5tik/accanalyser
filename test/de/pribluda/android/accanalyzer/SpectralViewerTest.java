package de.pribluda.android.accanalyzer;

import android.app.Activity;
import de.pribluda.android.accmeter.Sampler;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

/**
 * test  proper function of spectral viewer
 *
 * @author Konstantin Pribluda
 */
public class SpectralViewerTest {


    /**
     * shall start sampler automatically on resume in any case ( it is immune to double start )
     */
    @Test
    public void testSamplerIsStartedOnResume(@Mocked final Sampler sampler,
                                             @Mocked Activity activity,
                                             @Mocked(methods = {"onResume"}, inverse = true) final SpectralViewer spectralViewer) {

        Deencapsulation.setField(spectralViewer, "sampler", sampler);

        new Expectations() {
            {
                sampler.start();
            }
        };
        spectralViewer.onResume();
    }


    /**
     * shall stop sampler if not recording
     */
    @Test
    public void testThatSamplerIsStoppedIfNotRecording(@Mocked final Sampler sampler,
                                                       @Mocked final Recorder recorder,
                                                       @Mocked Activity activity,
                                                       @Mocked(methods = {"onPause"}, inverse = true) final SpectralViewer spectralViewer) {

        Deencapsulation.setField(spectralViewer, "sampler", sampler);
        Deencapsulation.setField(spectralViewer, "recorder", recorder);

        new Expectations() {
            {

                recorder.isRecording();
                returns(false);

            }
        };

        spectralViewer.onPause();
    }


    /**
     * shall stop sampler if not recording
     */
    @Test
    public void testThatSamplerIsNotStoppedIfRecording(@Mocked final Sampler sampler,
                                                       @Mocked final Recorder recorder,
                                                       @Mocked Activity activity,
                                                       @Mocked(methods = {"onPause"}, inverse = true) final SpectralViewer spectralViewer) {

        Deencapsulation.setField(spectralViewer, "sampler", sampler);
        Deencapsulation.setField(spectralViewer, "recorder", recorder);

        new Expectations() {
            {

                recorder.isRecording();
                returns(false);
            }
        };

        spectralViewer.onPause();
    }
}
