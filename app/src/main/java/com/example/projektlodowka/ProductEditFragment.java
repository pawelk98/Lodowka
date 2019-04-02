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
import android.widget.Button;
import android.widget.EditText;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductEditFragment extends Fragment {


    int id;
    EditText nazwa;
    EditText ilosc;
    EditText typ;
    Button zatwierdz;
    Button usun;
    Produkt p;
    ViewModel viewModel;

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
        nazwa = view.findViewById(R.id.produktEditNazwaEditText);
        ilosc = view.findViewById(R.id.produktEditIloscEditText);
        typ = view.findViewById(R.id.produktEditTypEditText);
        zatwierdz = view.findViewById(R.id.produktEditButton);
        usun = view.findViewById(R.id.produktUsunButton);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setEditProdukt(getActivity(), id);

        zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nazwa.getText().toString().trim().length() != 0
                        && ilosc.getText().toString().trim().length() != 0
                        && typ.getText().toString().trim().length() != 0){
                    Produkt uProdukt = new Produkt(nazwa.getText().toString(), Integer.parseInt(typ.getText().toString()),
                            Integer.parseInt(ilosc.getText().toString()));
                    uProdukt.setId(id);
                    viewModel.updateProdukt(uProdukt);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                    fragmentTransaction.commit();
                }
            }
        });

        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produkt uProdukt = new Produkt(nazwa.getText().toString(), Integer.parseInt(typ.getText().toString()),
                        Integer.parseInt(ilosc.getText().toString()));
                uProdukt.setId(id);
                viewModel.deleteProdukt(uProdukt);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                fragmentTransaction.commit();
            }
        });
    }
}
