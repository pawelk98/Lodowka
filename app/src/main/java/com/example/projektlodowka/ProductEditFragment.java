package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    int id;
    EditText nazwa;
    EditText ilosc;
    int type;
    Button edytuj;
    ViewModel viewModel;
    Produkt uProdukt;
    ImageButton imageButton;

    public ProductEditFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_edit, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Spinner typ = view.findViewById(R.id.spinner_prod_edit);
        ArrayAdapter<CharSequence> adapter_prod = ArrayAdapter.createFromResource(this.getActivity() ,R.array.types ,android.R.layout.simple_spinner_item);
        adapter_prod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typ.setAdapter(adapter_prod);
        typ.setOnItemSelectedListener(this);

        nazwa = view.findViewById(R.id.produktEditNazwaEditText);
        ilosc = view.findViewById(R.id.produktEditIloscEditText);
        edytuj = view.findViewById(R.id.produktEditButton);
        imageButton = view.findViewById(R.id.productImage);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setEditProdukt(getActivity(), id);

        edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nazwa.getText().toString().trim().length() != 0
                        && ilosc.getText().toString().trim().length() != 0){

                    switch (type) {
                        case 0:
                             uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.parseInt(ilosc.getText().toString())*1000);
                            break;
                        case 1:
                             uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.parseInt(ilosc.getText().toString())*1000);
                            break;
                        case 2:
                             uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.parseInt(ilosc.getText().toString())*1000);
                            break;
                        case 3:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type-3),
                                    Integer.parseInt(ilosc.getText().toString()));
                            break;
                        case 4:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type-3),
                                    Integer.parseInt(ilosc.getText().toString()));
                            break;
                    }

                    uProdukt.setId(id);
                    viewModel.updateProdukt(getActivity(), uProdukt);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                    fragmentTransaction.commit();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
