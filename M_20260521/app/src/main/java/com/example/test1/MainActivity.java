package com.example.test1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import io.paperdb.Paper;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        Paper.init(this); // PaperDB 초기화 [2]
        setActionbar();
        setBottomNavigation();

        // 초기 화면 설정 [2]
        replaceFragment(new HomeFragment());
    }

    private void setActionbar() {
        toolbar.setTitle("메모장 앱");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
    }

    private void setBottomNavigation() {
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.fragment_home) replaceFragment(new HomeFragment());
                else if (id == R.id.fragment_settings) replaceFragment(new SettingsFragment());
                else if (id == R.id.fragment_list) replaceFragment(new ListFragment());
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}