package com.example.test1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;
import java.util.ArrayList;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    ArrayList<String> filelist;

    // 생성자: 파일 목록을 받아옴 [1], [4]
    public FileListAdapter(ArrayList<String> filelist) {
        this.filelist = filelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 이전에 만든 item_layout.xml을 연결 [4]
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String filename = filelist.get(position);
        holder.filename.setText(filename);

        // 항목 클릭 시 해당 파일명을 저장하고 홈으로 이동 [2], [3]
        holder.itemView.setOnClickListener(v -> {
            Paper.book("selectedFilename").write("filename", filename);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return filelist.size();
    }

    // 뷰홀더 클래스 정의 [5], [6]
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView filename;
        ImageView fileicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileicon = itemView.findViewById(R.id.fileicon);
            filename = itemView.findViewById(R.id.filename);
        }
    }
}