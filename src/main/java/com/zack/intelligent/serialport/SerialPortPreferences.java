package com.zack.intelligent.serialport;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.serialport.SerialPortFinder;
import android.util.Log;


public class SerialPortPreferences extends PreferenceActivity {

    private App mApplication;
    private SerialPortFinder mSerialPortFinder;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.mApplication = ((App) getApplication());
        this.mSerialPortFinder = this.mApplication.mSerialPortFinder;
        addPreferencesFromResource(R.xml.serial_port_preferences);
        ListPreference listPreference = (ListPreference) findPreference("DEVICE");
        String[] arrayOfString1 = this.mSerialPortFinder.getAllDevices();
        String[] arrayOfString2 = this.mSerialPortFinder.getAllDevicesPath();
        listPreference.setEntries(arrayOfString1);
        listPreference.setEntryValues(arrayOfString2);
        listPreference.setSummary(listPreference.getValue());
        Log.i("serialport", "onCreate value: "+listPreference.getValue());
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.i("serialport", "onCreate key: "+preference.getKey()+"; newValue: "+newValue.toString());
                preference.setSummary((String) newValue);
                return true;
            }
        });
        ListPreference listPreference2 = (ListPreference) findPreference("BAUDRATE");
        listPreference2.setSummary(listPreference2.getValue());
        Log.i("serialport", "onCreate2 value: "+listPreference2.getValue());
        listPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.i("serialport", "onCreate2 key: "+preference.getKey()+"; newValue: "+newValue.toString());
                preference.setSummary((String) newValue);
                return true;
            }
        });
    }
}
