package com.example.projektlodowka;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.support.v4.app.Fragment;


public class MainActivity extends AppCompatActivity  {

    Toolbar toolbar;


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private StartFragment startFragment;
    private ProductFragment productFragment;
    private RecipeFragment recipeFragment;

    private HistoryFragment historyFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_frame);

        startFragment = new StartFragment();
        productFragment = new ProductFragment();
        recipeFragment = new RecipeFragment();
        historyFragment = new HistoryFragment();

        mMainNav.setSelectedItemId(R.id.nav_start);
        setFragment(startFragment);
        toolbar.setTitle("eFridge");

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_start :
                        setFragment(startFragment);
                        toolbar.setTitle(R.string.app_name);
                        return true;


                    case R.id.nav_produkty :
                        setFragment(productFragment);
                        toolbar.setTitle(R.string.produkty);
                        return true;

                    case R.id.nav_przepisy :
                        setFragment(recipeFragment);
                        toolbar.setTitle(R.string.przepisy);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper_menu, menu);
        return true;
    }

    private void setFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        manager.popBackStack();
        manager.popBackStack();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent intent =  new Intent(MainActivity.this, settings.class);
                startActivity(intent);
                break;
            case R.id.history:
                setFragment(historyFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
