package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.projektlodowka.database.ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeEditFragment extends Fragment {

    Button zmienZdjecie;
    Button usunZdjecie;
    ImageView obrazek;
    EditText nazwa;
    EditText czas;
    EditText opis;

    int idPrzepisu;
    String nazwaPrzepisu;

    ViewModel viewModel;


    public RecipeEditFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPrzepisu = getArguments().getInt("id");
            nazwaPrzepisu = getArguments().getString("przepisName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zmienZdjecie = view.findViewById(R.id.zmienFoto);
        usunZdjecie = view.findViewById(R.id.usunFoto);
        obrazek = view.findViewById(R.id.recipkaFoto);
        nazwa = view.findViewById(R.id.zmienName);
        czas = view.findViewById(R.id.zmienTajm);
        opis = view.findViewById(R.id.zmienOpis);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.setEditPrzepis(getActivity(), idPrzepisu);

    }
}
