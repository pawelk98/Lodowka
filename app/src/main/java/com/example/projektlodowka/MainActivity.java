package com.example.projektlodowka;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




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
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
