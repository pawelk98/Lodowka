package com.example.projektlodowka;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projektlodowka.database.MyTaskParams;
import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktPrzepis;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class PrzepisAddFragment extends Fragment {

    Button dodaj;
    EditText nazwa;
    EditText czas;
    EditText opis;
    ViewModel viewModel;
    RecyclerView recyclerView;
    ProduktDodawaniePrzepisuAdatper adapter;
    Przepis przepis;
    RadioGroup radioGroup;
    RadioButton inne,sniadanie,obiad,kolacja,kolacjaSniadanie;
    List<Produkt> produkty = new ArrayList<>();
    List<MyTaskParams> produktyDoDodania = new ArrayList<>();


    public PrzepisAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_przepis_add, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_dodaj_produkt_do_przepisu);
        adapter = new ProduktDodawaniePrzepisuAdatper(getActivity(), produkty);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        radioGroup = view.findViewById(R.id.radioGroupAdd);
        inne = view.findViewById(R.id.radioInneAdd);
        sniadanie = view.findViewById(R.id.radioSniadanieAdd);
        obiad = view.findViewById(R.id.radioObiadAdd);
        kolacja = view.findViewById(R.id.radioKolacjaAdd);
        kolacjaSniadanie = view.findViewById(R.id.radioSniadanieKolacjaAdd);

        viewModel.getProdukty().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(@Nullable final List<Produkt> produkt) {
                produkty = produkt;
                adapter.setProdukty(produkt);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        nazwa = view.findViewById(R.id.przepis_dodaj_nazwe);
        czas = view.findViewById(R.id.przepis_dodaj_czas);
        opis = view.findViewById(R.id.przepis_dodaj_opis);
        dodaj = view.findViewById(R.id.dodaj_przepis);


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (adapter.checked(i)) {
                        String nazwa = adapter.nazwaProduktu(i);
                        int ilosc = adapter.iloscProduktu(i);
                        boolean opcjonalny = adapter.opcjonalnyProdukt(i);

                        produktyDoDodania.add(new MyTaskParams(nazwa, ilosc, opcjonalny));
                    }
                }
                if (nazwa.getText().toString().trim().length() != 0
                        && czas.getText().toString().trim().length() != 0) {
                    Przepis uPrzepis = new Przepis();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    int pora=0;
                    if(selectedId == sniadanie.getId()) {
                        pora=1;
                    }
                    else if(selectedId == obiad.getId()) {
                        pora=2;
                    }
                    else if(selectedId == kolacja.getId()) {
                        pora=3;
                    }
                    else if(selectedId == kolacjaSniadanie.getId()) {
                        pora=4;
                    }
                    else {
                        pora=0;
                    }

                    przepis = new Przepis(nazwa.getText().toString().toLowerCase(),
                            Integer.valueOf(czas.getText().toString()),
                            opis.getText().toString().toLowerCase(), pora);
                    viewModel.insertPrzepis(getActivity(), przepis, produktyDoDodania);
                    produktyDoDodania.clear();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new RecipeFragment());
                    fragmentTransaction.commit();
                }
            }
        });
    }
}
