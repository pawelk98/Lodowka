package com.example.projektlodowka;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    private ViewModel viewModel;
    ListView przepisyListView;
    ListViewPrzepisyAdapter listViewPrzepisyAdapter;

    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        przepisyListView = getActivity().findViewById(R.id.przepisyListView);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        listViewPrzepisyAdapter = new ListViewPrzepisyAdapter(getActivity());
        przepisyListView.setAdapter(listViewPrzepisyAdapter);

        viewModel.getPrzepisy().observe(this, new Observer<List<Przepis>>() {
            @Override
            public void onChanged(@Nullable List<Przepis> przepis) {
                listViewPrzepisyAdapter.setPrzepisy(przepis);
                przepisyListView.setAdapter(listViewPrzepisyAdapter);
            }
        });
    }
}
