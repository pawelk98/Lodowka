package com.example.projektlodowka;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projektlodowka.database.BazaDanych;
import com.example.projektlodowka.database.Minutnik;
import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktPrzepis;
import com.example.projektlodowka.database.Przepis;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BazaDanych db = Room.databaseBuilder(getApplicationContext(),
                BazaDanych.class, "Bazzzuuugguiii").allowMainThreadQueries().build();
        //utworzenie bazy danych o nazwie "B1"

        Przepis p1 = new Przepis("placuszek",10,"trombalskis");
        db.przepisDao().insert(p1);

        Minutnik m1 = new Minutnik("tak",1,20);
        db.minutnikDao().insert(m1);

        Minutnik m2 = db.minutnikDao().loadNazwa("tak").get(0);

        TextView tv = findViewById(R.id.textView);
        tv.setText(m2.getNazwa());
    }
}
