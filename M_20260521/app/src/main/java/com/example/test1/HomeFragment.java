package com.example.test1;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.paperdb.Paper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    EditText memo_content;
    DatePicker datePicker;
    Button btn_save, btn_cancel;
    String filename;
    boolean modified = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        memo_content = view.findViewById(R.id.memo_content);
        datePicker = view.findViewById(R.id.datePicker);
        btn_save = view.findViewById(R.id.btn_save);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        readPaper(); // 설정 적용 [8]
        try {
            initDatePicker(); // 날짜 초기화 [11]
        } catch (IOException e) { e.printStackTrace(); }

        memo_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                btn_save.setEnabled(true);
                btn_cancel.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btn_save.setOnClickListener(v -> saveMemo());
    }

    private void readPaper() {
        String fontSize = Paper.book("setting").read("fontSize", "20.0");
        String fontColor = Paper.book("setting").read("fontColor", "#000000");
        memo_content.setTextSize(Float.parseFloat(fontSize));
        memo_content.setTextColor(Color.parseColor(fontColor));
    }

    private void initDatePicker() throws IOException {
        Calendar c = Calendar.getInstance();
        filename = c.get(Calendar.YEAR) + "_" + (c.get(Calendar.MONTH) + 1) + "_" + c.get(Calendar.DAY_OF_MONTH) + ".txt";
        memo_content.setText(readFile(filename));
    }

    private void saveMemo() {
        try (FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(memo_content.getText().toString().getBytes("UTF-8"));
            modified = false;
            btn_save.setEnabled(false);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private String readFile(String name) throws IOException {
        File file = new File(requireContext().getFilesDir(), name);
        if (!file.exists()) return "";
        FileInputStream fis = requireContext().openFileInput(name);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line).append("\n");
        fis.close();
        return sb.toString();
    }
}