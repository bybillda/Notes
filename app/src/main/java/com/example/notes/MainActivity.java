package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        if (!isLandscape())
            initToolbarAndDrawer();
        else
            initToolBar();

        if (savedInstanceState == null) getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.notes_container, new NotesFragment())
                .commit();
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = initToolBar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_about:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("")
                                .add(R.id.notes_container, new AboutFragment())
                                .commit();
                        return true;
                    case R.id.action_exit:
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.notes_container, new AboutFragment())
                        .commit();
                return true;
            case R.id.action_exit:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}