package de.pribluda.android.accanalyzer;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

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
         if(restoreValue) {
             currentValue = this.getPersistedInt(0);
         }   else {
             currentValue = (Integer) defaultValue;
             persistInt(currentValue);
         }
    }
}
