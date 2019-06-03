package com.example.projektlodowka;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener {

    List<Produkt> produkty = new ArrayList<>();
    private ViewModel viewModel;
    RecyclerView recyclerView;
    private boolean changedFragment = false;
    RecyclerProduktyAdapter adapter;
    SearchView searchView;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recycler_view_produkty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_produkty);
        adapter = new RecyclerProduktyAdapter(getActivity(), produkty);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getProdukty().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(@Nullable final List<Produkt> produkt) {
                produkty = produkt;
                adapter.setProdukty(produkt);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.addProductFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProductAddFragment());
                fragmentTransaction.addToBackStack("produktyRecycler").commit();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerProduktyAdapter.RecyclerTouchListener(getActivity(), recyclerView, new RecyclerProduktyAdapter.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", adapter.getProdukt(position).getId());
                ProductShowcaseFragment fragment = new ProductShowcaseFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.addToBackStack("produktyRecycler").commit();

            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        changedFragment = false;
        String name = newText.toLowerCase();
        List<Produkt> noweProdukty = new ArrayList<>();

        recyclerView.setAdapter(adapter);
        for (Produkt produkt : produkty) {
            if (produkt.getNazwa().toLowerCase().contains(name)) {
                noweProdukty.add(produkt);
            }
        }
        adapter.setFilter(noweProdukty);
        changedFragment = true;
        return true;
    }

}




