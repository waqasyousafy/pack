package com.code4rox.calculatorfox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;
import androidx.work.WorkManager;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingsframework, new SettingsFragment())
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topcornermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public  static class SettingsFragment extends PreferenceFragmentCompat  {

        SwitchPreferenceCompat syncSwitch;
        SwitchPreferenceCompat light_on_swtich;
        EditTextPreference editTextPreference;
        Camera cam ;
      Vibrator vibrator;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            syncSwitch = (SwitchPreferenceCompat) findPreference("Mode");
            light_on_swtich=(SwitchPreferenceCompat) findPreference("light_on_swtich");
            editTextPreference=(EditTextPreference) findPreference("signature");
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
            syncSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (preference.getKey().equals("Mode")) {
                        if (syncSwitch.isChecked()) {
                            syncSwitch.setChecked(false);
                            // action perform switch off
                            Toast.makeText(getContext(),"Normal mode ",Toast.LENGTH_SHORT).show();
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                        } else {
                            syncSwitch.setChecked(true);
                            // action perform switch on
                            Toast.makeText(getContext(),"Silent mode on ",Toast.LENGTH_SHORT).show();
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                    }
                    return false;
                }
            });

            light_on_swtich.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(preference.getKey().equals("light_on_swtich"))
                    {
                        if(light_on_swtich.isChecked())
                        {
                            light_on_swtich.setChecked(false);
                            cam.stopPreview();
                            cam.release();
                        }
                        else{
                            light_on_swtich.setChecked(true);
                            cam = Camera.open();
                            Camera.Parameters p = cam.getParameters();
                            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            cam.setParameters(p);
                            cam.startPreview();

                        }
                    }
                    return false;
                }
            });
//            editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    if(preference.getKey().equals("signature"))
//                    {
//                        String data=preference.getKey().toString();
//                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//                        String value = preferences.getString(data, " ");
//                        workermanager.btnSend.setText(value);
//                    }
//                    return false;
//                }
//            });

        }



    }
}