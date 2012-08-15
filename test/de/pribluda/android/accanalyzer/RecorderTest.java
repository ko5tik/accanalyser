package de.pribluda.android.accanalyzer;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertSame;

/**
 * test recorder functionality
 *
 * @author Konstantin Pribluda
 */
public class RecorderTest {


    /**
     * recorder shall deliver ;ist of files
     */
    @Test
    public void testFileListDelivery(@Mocked final File basedir,
                                     @Mocked(methods = {"listFiles"}, inverse = true) final Recorder recorder) {

        final File[] files = new File[5];
        Deencapsulation.setField(recorder,"basedir",basedir);

        new Expectations() {
            {

                basedir.listFiles();  returns(files);
            }
        };

        assertSame(files, recorder.listFiles());
    }
}
