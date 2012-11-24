package de.pribluda.android.accanalyzer;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

/**
 * preference screen setting integer values.
 *
 * @author Konstantin Pribluda
 */
public class IntegerListPreference extends ListPreference {

    Integer currentValue;

    public IntegerListPreference(Context context) {
        super(context);
    }

    public IntegerListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        Log.e("ACCMETER ", "default value: " + defaultValue);
        if (restoreValue) {
            currentValue = this.getPersistedInt(0);
        } else {
            currentValue = Integer.parseInt(defaultValue.toString());
            persistInt(currentValue);
        }
    }

    @Override
    public String getValue() {
        currentValue = getPersistedInt(0);
        return currentValue.toString();
    }

    @Override
    public void setValue(String value) {
        currentValue = Integer.valueOf(value);
        persistInt(currentValue);
    }
}
