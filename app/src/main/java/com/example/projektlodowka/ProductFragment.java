package com.example.projektlodowka;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v4.app.FragmentTransaction;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private ViewModel viewModel;
    GridView produktyGridView;
    GridViewProduktyAdapter gridViewProduktyAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produktyGridView = view.findViewById(R.id.gridViewProdukty);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        gridViewProduktyAdapter = new GridViewProduktyAdapter(getActivity());
        produktyGridView.setAdapter(gridViewProduktyAdapter);

        viewModel.getProdukty().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(@Nullable final List<Produkt> produkt) {
                gridViewProduktyAdapter.setProdukty(produkt);
                produktyGridView.setAdapter(gridViewProduktyAdapter);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.addProductFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProductAddFragment());
                fragmentTransaction.commit();
            }
        });

        produktyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", gridViewProduktyAdapter.getProdukt(position).getId());
                ProductEditFragment fragment = new ProductEditFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();
            }
        });


    }
}
