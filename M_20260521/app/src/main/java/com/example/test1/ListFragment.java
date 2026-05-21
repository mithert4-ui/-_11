package com.example.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> fileList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        readFileList();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new FileListAdapter(fileList));
    }

    private void readFileList() {
        File dir = requireContext().getFilesDir();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (Character.isDigit(f.getName().charAt(0))) fileList.add(f.getName());
            }
        }
    }
}
