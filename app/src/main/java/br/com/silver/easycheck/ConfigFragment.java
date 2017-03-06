package br.com.silver.easycheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by silver on 26/04/16.
 */
public class ConfigFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    EditTextPreference mPrefServer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ec_preferences);

        mPrefServer = (EditTextPreference) findPreference(getString(R.string.pref_server_address));
        fillSummary(mPrefServer);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if(preference.equals(mPrefServer)){
            mPrefServer.setSummary(stringValue);
        }
        return true;
    }

    private void fillSummary(Preference preference){
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Object value = pref.getString(preference.getKey(),"");
        onPreferenceChange(preference, value);
    }
}
