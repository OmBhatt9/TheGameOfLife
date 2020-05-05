package com.example.thegameoflife.ui.settings;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.thegameoflife.MenuActivity;
import com.example.thegameoflife.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private SettingsViewModel notificationsViewModel;
    public Switch musicSwitch;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        // music settings
        musicSwitch = root.findViewById(R.id.switch2);
        if (!MenuActivity.playing) {
            final SharedPreferences sharedPrefs = getActivity().getSharedPreferences("com.example.xyle", MODE_PRIVATE);
            musicSwitch.setChecked(sharedPrefs.getBoolean("musicSwitch", false));
        } else {
            final SharedPreferences sharedPrefs = getActivity().getSharedPreferences("com.example.xyle", MODE_PRIVATE);
            musicSwitch.setChecked(sharedPrefs.getBoolean("musicSwitch", true));
        }
        // Set a checked change listener for switch button
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    playMusic(root);
                    MenuActivity.playing = true;
                }
                else {
                    pauseMusic(root);
                    MenuActivity.playing = false;
                }
            }
        });

        return root;
    }

    public void playMusic(View v) {
        if (MenuActivity.player == null) {
            MenuActivity.player = MediaPlayer.create(getContext(), R.raw.finalappmusic);
            MenuActivity.player.setLooping(true);
        }
        MenuActivity.player.start();
    }

    public void pauseMusic(View v) {
        if (MenuActivity.player != null) {
            MenuActivity.player.pause();
        }
    }
}
