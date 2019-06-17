package com.example.projektlodowka;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.projektlodowka.database.BazaDanych;
import com.example.projektlodowka.database.ViewModel;

public class settings extends AppCompatActivity {
    Toolbar toolbar;
    ViewModel viewModel;
    Button resetBtn,nocBtn,sniadanieBtn,obiadBtn,kolacjaBtn;
    TextView noc,sniadanie,obiad,kolacja;
    BazaDanych db;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        nocBtn = findViewById(R.id.buttonNoc);
        sniadanieBtn = findViewById(R.id.buttonSniadanie);
        obiadBtn = findViewById(R.id.buttonObiad);
        kolacjaBtn = findViewById(R.id.buttonKolacja);
        toolbar= findViewById(R.id.toolbar);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        resetBtn = findViewById(R.id.resetButton);
        noc=findViewById(R.id.czasNoc);
        sniadanie=findViewById(R.id.czasSniadanie);
        obiad=findViewById(R.id.czasObiad);
        kolacja=findViewById(R.id.czasKolacja);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
        kolacja.setText(viewModel.getStringKolacja());
        obiad.setText(viewModel.getStringObiad());
        noc.setText(viewModel.getStringNoc());
        sniadanie.setText(viewModel.getStringSniadanie());

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllProdukt();
                viewModel.deleteAllPrzepis();
                db = BazaDanych.getBazaDanych(getApplication());
                new BazaDanych.PopulateDbAsync(db).execute();
                viewModel.setBasics();
                noc.setText(viewModel.getStringNoc());
                sniadanie.setText(viewModel.getStringSniadanie());
                obiad.setText(viewModel.getStringObiad());
                kolacja.setText(viewModel.getStringKolacja());

            }
        });
        nocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int godzina = timePicker.getCurrentHour();
            int minuta = timePicker.getCurrentMinute();
                Toast.makeText(getBaseContext(),String.valueOf(godzina),
                        Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(),String.valueOf(minuta),
                        Toast.LENGTH_SHORT).show();
            viewModel.setNoc(godzina,minuta);
            noc.setText(viewModel.getStringNoc());
            }
        });
        sniadanieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();
                viewModel.setSniadanie(godzina,minuta);
                sniadanie.setText(viewModel.getStringSniadanie());

            }
        });
        obiadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();
                viewModel.setObiad(godzina,minuta);
                obiad.setText(viewModel.getStringObiad());

            }
        });
        kolacjaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();
                viewModel.setKolacja(godzina,minuta);
                //kolacja.setText(String.valueOf(viewModel.getKolacja()));
                kolacja.setText(viewModel.getStringKolacja());

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
