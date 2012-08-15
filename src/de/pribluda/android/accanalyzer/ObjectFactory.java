package de.pribluda.android.accanalyzer;

import android.content.Context;
import de.pribluda.android.accmeter.Sampler;

/**
 * poor man DI container providing objects
 *
 * @author Konstantin Pribluda
 */
public class ObjectFactory {


    public static Sampler getSampler(Context context) {
        return Sampler.getInstance(context);
    }


    public static Recorder getRecorder(Context context) {
        return Recorder.getInstance(context, getSampler(context));
    }
}
