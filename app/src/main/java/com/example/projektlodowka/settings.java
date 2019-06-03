package com.example.projektlodowka;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.projektlodowka.database.BazaDanych;
import com.example.projektlodowka.database.ViewModel;

public class settings extends AppCompatActivity {
    Toolbar toolbar;
    ViewModel viewModel;
    Button resetBtn;
    BazaDanych db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar= findViewById(R.id.toolbar);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        resetBtn = findViewById(R.id.resetButton);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllProdukt();
                viewModel.deleteAllPrzepis();
                db = BazaDanych.getBazaDanych(getApplication());
                new BazaDanych.PopulateDbAsync(db).execute();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
