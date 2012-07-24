package de.pribluda.android.stroker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *
 */
public class StrokeDetector implements SensorEventListener {

    private static StrokeDetector instance;

    // window size for fft
    public static final int WINDOW_SIZE = 128;
    private SensorManager sensorManager;


    private final double[] buffer = new double[WINDOW_SIZE];
    // array index
    private int index;

    private double lastSample;


    public StrokeDetector(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }


    public void start() {
        sensorManager.registerListener(this, sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_FASTEST);

    }


    public void stop() {
        sensorManager.unregisterListener(this);
    }

    /**
     * receive sensor event and place it into  buffer
     *
     * @param sensorEvent
     */
    public void onSensorChanged(SensorEvent sensorEvent) {
        // we are only interested in accelerometer events
        if (Sensor.TYPE_ACCELEROMETER == sensorEvent.sensor.getType()) {
            // compute modulo
            double modulo = Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1] + sensorEvent.values[2] * sensorEvent.values[2]);
            // store difference
            buffer[index] = lastSample - modulo;
            lastSample = modulo;
            // advance index
            index++;
            index %= WINDOW_SIZE;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * access to sample buffer
     *
     * @return
     */
    public double[] getBuffer() {
        return buffer;
    }
}
