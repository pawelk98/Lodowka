package com.example.projektlodowka;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.support.v4.app.Fragment;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {

    Toolbar toolbar;
    private boolean visible_setter=false;
    private DrawerLayout drawerLayout;


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private StartFragment startFragment;
    private HistoryFragment historyFragment;
    private ProductFragment productFragment;
    private RecipeFragment recipeFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);

        startFragment = new StartFragment();
        historyFragment = new HistoryFragment();
        productFragment = new ProductFragment();
        recipeFragment = new RecipeFragment();

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_start :
                        setFragment(startFragment);
                        visible_setter=false;
                        return true;

                    case R.id.nav_historia :
                        setFragment(historyFragment);
                        visible_setter=false;
                        return true;

                    case R.id.nav_produkty :
                        setFragment(productFragment);
                        visible_setter=true;
                        return true;

                    case R.id.nav_przepisy :
                        setFragment(recipeFragment);
                        visible_setter=true;
                        return true;

                    default:
                        return false;

                }







            }
        });



        /*
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(


                new NavigationView.OnNavigationItemSelectedListener() {
                    private MenuItem menuItem;


                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        this.menuItem = menuItem;
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        drawerLayout.closeDrawers();
                        switch(id){
                            case R.id.nav_produkty:
                                Intent intent = new Intent(MainActivity.this, produkty_activity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_przepisy:
                                Intent intent1 = new Intent (MainActivity.this, przepisy_activity.class);
                                startActivity(intent1);
                                break;
                            case R.id.nav_historia:
                                Intent intent2 = new Intent (MainActivity.this, historia_activity.class);
                                startActivity(intent2);
                                break;
                            case R.id.nav_ustawienia:
                                Intent intent3 = new Intent (MainActivity.this, ustawienia_activity.class);
                                startActivity(intent3);
                                break;
                        }
                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

         */


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper_menu, menu);
        invalidateOptionsMenu();  //To jest problem i plusik bo jeżeli to tutaj jest, wted ikonka szukania znika na niechcianych fragmentach, ale to resetuje ustawienia menu, więc search się nie rozwija
        //Jezeli chcemy aby sie rozwijało(co jest konieczne) to zakomentujcie 165 linijke i w linijce 30 zmiencie na "true" lub zakomentujcie ifa
        if(visible_setter==false){
            menu.getItem(0).setVisible(false);
        }
        else {
            menu.getItem(0).setVisible(true);


        }

        return true;
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "Wybrano szukanie ";
        switch (item.getItemId())
        {
            case R.id.search:
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.settings:
                Intent intent =  new Intent(MainActivity.this, settings.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
