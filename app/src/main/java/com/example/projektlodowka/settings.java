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

                int h = Integer.parseInt(sniadanie.getText().toString().substring(0,2));
                int m = Integer.parseInt(sniadanie.getText().toString().substring(3,5));

                int time1 = m + h * 100;
                int time2 = minuta + godzina * 100;

                if(time2 < time1) {
                    viewModel.setNoc(godzina, minuta);
                    noc.setText(viewModel.getStringNoc());
                }
            }
        });
        sniadanieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();

                int h = Integer.parseInt(noc.getText().toString().substring(0,2));
                int m = Integer.parseInt(noc.getText().toString().substring(3,5));

                int time1 = m + h * 100;
                int time2 = minuta + godzina * 100;

                h = Integer.parseInt(obiad.getText().toString().substring(0,2));
                m = Integer.parseInt(obiad.getText().toString().substring(3,5));

                int time3 = m + h*100;

                if(time1 < time2 && time2 < time3) {
                    viewModel.setSniadanie(godzina, minuta);
                    sniadanie.setText(viewModel.getStringSniadanie());
                }
            }
        });

        obiadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();

                int h = Integer.parseInt(sniadanie.getText().toString().substring(0,2));
                int m = Integer.parseInt(sniadanie.getText().toString().substring(3,5));

                int time1 = m + h*100;
                int time2 = minuta + godzina * 100;

                h = Integer.parseInt(kolacja.getText().toString().substring(0,2));
                m = Integer.parseInt(kolacja.getText().toString().substring(3,5));

                int time3 = m + h*100;

                if(time1 < time2 && time2 < time3) {
                    viewModel.setObiad(godzina, minuta);
                    obiad.setText(viewModel.getStringObiad());
                }
            }
        });

        kolacjaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int godzina = timePicker.getCurrentHour();
                int minuta = timePicker.getCurrentMinute();

                int h = Integer.parseInt(obiad.getText().toString().substring(0,2));
                int m = Integer.parseInt(obiad.getText().toString().substring(3,5));

                int time1 = m + h*100;
                int time2 = minuta + godzina * 100;

                if(time1 < time2) {
                    viewModel.setKolacja(godzina, minuta);
                    //kolacja.setText(String.valueOf(viewModel.getKolacja()));
                    kolacja.setText(viewModel.getStringKolacja());
                }
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
