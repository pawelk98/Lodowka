package com.example.projektlodowka;


import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.projektlodowka.database.BazaDanych;
import com.example.projektlodowka.database.Produkt;

import java.util.List;


public class produkty_activity extends AppCompatActivity {

    BazaDanych baza;

    EditText produktNazwa;
    EditText produktIlosc;
    EditText produktTyp;
    Button produktDodaj;

    Toast produktDodano;

    ProduktyAdapter produktyAdapter;
    ListView produktyLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_produkty_activity);

        baza = Room.databaseBuilder(getApplicationContext(), BazaDanych.class,
                "newDatabase").allowMainThreadQueries().build();
        produktNazwa = findViewById(R.id.produktNazwaEditText);
        produktIlosc = findViewById(R.id.produktIloscEditText);
        produktTyp = findViewById(R.id.produktTypEditText);
        produktDodaj = findViewById(R.id.produktDodajButton);
        produktyLista = findViewById(R.id.produktyLista);
        setListView();

        produktDodano = Toast.makeText(getApplicationContext(), "Dodano produkt", Toast.LENGTH_SHORT);

        produktDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!produktNazwa.getText().toString().trim().isEmpty() && !produktIlosc.getText().toString().trim().isEmpty()
                        && !produktTyp.getText().toString().trim().isEmpty()) {

                    Produkt p = new Produkt(produktNazwa.getText().toString(),
                            Integer.parseInt(produktTyp.getText().toString()),
                            Integer.parseInt(produktIlosc.getText().toString()));
                    baza.produktDao().insert(p);
                    setListView();

                    produktNazwa.setText("");
                    produktTyp.setText("");
                    produktIlosc.setText("");
                    produktDodano.show();
                }
            }
        });
        produktyLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popup = new PopupMenu(produkty_activity.this, produktyLista);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Produkt p = produktyAdapter.getProdukt(position);
                        baza.produktDao().delete(p);
                        setListView();
                        Toast.makeText(produkty_activity.this, "Pomyślnie usunięto",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    void setListView() {
        List<Produkt> p = baza.produktDao().loadAllOrderNazwa();
        produktyAdapter = new ProduktyAdapter(getApplicationContext(), p);
        produktyLista.setAdapter(produktyAdapter);
    }
}
