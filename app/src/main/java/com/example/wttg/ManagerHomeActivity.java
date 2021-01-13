package com.example.wttg;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wttg.Fragment.ManagerHistoryFragment;
import com.example.wttg.Fragment.ManagerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagerHomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ManagerHistoryFragment()).commit();

        selectedFragment = new ManagerHistoryFragment();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_camera:
                            startActivity(new Intent(ManagerHomeActivity.this, CameraActivity.class));
                            break;
                        case R.id.nav_my:
                            selectedFragment = new ManagerProfileFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new ManagerHistoryFragment();
                            break;
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };



}
