package ca.smartgate.it.automatedbarriergate;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String VIBRATION_PREF = "vibration_pref";
    private static final String NIGHT_MODE_PREF = "night_mode_pref";
    private Vibrator vibrator;
    private boolean isNightModeEnabled;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        Switch switchLockPortrait = rootView.findViewById(R.id.switch1);
        Button button = rootView.findViewById(R.id.signout);
        Switch switchVibration = rootView.findViewById(R.id.switch2);
        Switch switchNightMode = rootView.findViewById(R.id.switch3);

        boolean isVibrationEnabled = sharedPreferences.getBoolean(VIBRATION_PREF, true);
        switchVibration.setChecked(isVibrationEnabled);

        isNightModeEnabled = sharedPreferences.getBoolean(NIGHT_MODE_PREF, false);
        switchNightMode.setChecked(isNightModeEnabled);

        switchLockPortrait.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            }
        });

        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(VIBRATION_PREF, isChecked).apply();
                if (isChecked) {
                    vibrate();
                }
            }
        });

        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(NIGHT_MODE_PREF, isChecked).apply();
                isNightModeEnabled = isChecked;
                applyNightMode();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        applyNightMode();

        return rootView;
    }

    private void vibrate() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void applyNightMode() {
        if (isNightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}