package com.example.projektlodowka;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements SearchView.OnQueryTextListener {

    List<Przepis> przepisy = new ArrayList<>();
    private ViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerPrzepisyAdapter adapter_przepisy;
    SearchView searchView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        searchView.setQuery("",true);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.przepisy_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.przepisy_recycler);
        adapter_przepisy = new RecyclerPrzepisyAdapter(getActivity(), przepisy);
        recyclerView.setAdapter(adapter_przepisy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getPrzepisy().observe(this, new Observer<List<Przepis>>() {
            @Override
            public void onChanged(@Nullable final List<Przepis> przepis) {
                przepisy = przepis;
                adapter_przepisy.setPrzepisy(przepis);
                recyclerView.setAdapter(adapter_przepisy);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.przepis_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new PrzepisAddFragment());
                fragmentTransaction.addToBackStack("przepisyRecycler").commit();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerPrzepisyAdapter.RecyclerTouchListener(getActivity(), recyclerView, new RecyclerPrzepisyAdapter.ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", adapter_przepisy.getPrzepis(position).getId());
                bundle.putString("przepisName", adapter_przepisy.getPrzepis(position).getNazwa());
                RecipeShowFragment fragment = new RecipeShowFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.addToBackStack("przepisyRecycler").commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", adapter_przepisy.getPrzepis(position).getId());
                bundle.putString("przepisName", adapter_przepisy.getPrzepis(position).getNazwa());
                RecipeEditFragment fragment = new RecipeEditFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.addToBackStack("przepisyRecycler").commit();
            }

        }));
        searchView = (SearchView) view.findViewById(R.id.search_view_przepisy);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String name = newText.toLowerCase();
        List<Przepis> nowePrzepisy = new ArrayList<>();

        recyclerView.setAdapter(adapter_przepisy);
        for (Przepis przepis : przepisy) {
            if (przepis.getNazwa().toLowerCase().contains(name)) {
                nowePrzepisy.add(przepis);
            }
        }
        adapter_przepisy.setFilter(nowePrzepisy);
        return true;
    }
}
