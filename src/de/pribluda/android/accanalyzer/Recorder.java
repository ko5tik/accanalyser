package de.pribluda.android.accanalyzer;

import android.os.Environment;
import android.util.Log;
import de.pribluda.android.accmeter.Sampler;
import de.pribluda.android.accmeter.file.FileSink;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * performs recording of data.  realised as singleton
 *
 * @author Konstantin Pribluda
 */
public class Recorder {

    public static final String DIRECTORY = "acc_data";
    public static final String LOG_TAG = "strokeCounter.recorder";
    static private Recorder instance;
    private final DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");


    private boolean recording = false;
    private final File basedir;
    private FileSink sink;
    private final Sampler sampler;

    private Recorder( Sampler sampler) {

        this.sampler = sampler;
        basedir = new File(Environment.getExternalStorageDirectory(), DIRECTORY);
    }


    public static synchronized Recorder getInstance( Sampler sampler) {
        if (null == instance) {
            instance = new Recorder(sampler);
        }
        return instance;
    }


    /**
     * start recording samples  into automatically generated file
     *
     * @param sampler
     */
    public void start(Sampler sampler) {
        Log.d(LOG_TAG, "start recording");

        final File destFile = new File(basedir, format.format(new Date()));

        Log.d(LOG_TAG, "file name:" + destFile);


        // create file and directory
        try {
            //  create parent directories just in case
            destFile.getParentFile().mkdirs();

            sink = new FileSink(destFile);
            sampler.addSink(sink);

        } catch (IOException e) {

        }


        recording = true;
    }


    /**
     * stop recording
     */
    public void stop() {
        Log.d(LOG_TAG, "stop recording");
        if (recording) {
            if (sink != null) {
                sampler.removeSink(sink);
                try {
                    sink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        recording = false;
    }

    public boolean isRecording() {
        return recording;
    }

    public File[] listFiles() {
        return basedir.listFiles();
    }
}
