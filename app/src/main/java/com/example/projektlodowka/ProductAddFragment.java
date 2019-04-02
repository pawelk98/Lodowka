package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductAddFragment extends Fragment {
    EditText nazwa;
    EditText ilosc;
    EditText typ;
    Button dodaj;
    Produkt p;
    ViewModel viewModel;

    public ProductAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nazwa = view.findViewById(R.id.produktDodajNazwaEditText);
        ilosc = view.findViewById(R.id.produktDodajIloscEditText);
        typ = view.findViewById(R.id.produktDodajTypEditText);
        dodaj = view.findViewById(R.id.produktDodajButton);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nazwa.getText().toString().trim().length() != 0
                        && ilosc.getText().toString().trim().length() != 0
                        && typ.getText().toString().trim().length() != 0) {
                    p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(typ.getText().toString()),
                            Integer.valueOf(ilosc.getText().toString()));
                    viewModel.insertProdukt(getActivity(),p);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                    fragmentTransaction.commit();
                }
            }
        });


    }

}
