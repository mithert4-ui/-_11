package com.example.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.paperdb.Paper;

public class SettingsFragment extends Fragment {
    SeekBar seekBar;
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        seekBar = view.findViewById(R.id.seekBar);
        radioGroup = view.findViewById(R.id.radioGroup);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String size = (progress == 0) ? "20.0" : (progress == 1) ? "25.0" : "30.0";
                Paper.book("setting").write("fontSize", size);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String color = (checkedId == R.id.radioRed) ? "#FF0000" :
                    (checkedId == R.id.radioWhite) ? "#FFFFFF" : "#000000";
            Paper.book("setting").write("fontColor", color);
        });
    }
}