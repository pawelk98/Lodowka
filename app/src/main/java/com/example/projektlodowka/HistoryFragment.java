package com.example.projektlodowka;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.projektlodowka.database.Historia;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {


    List<Historia> historias = new ArrayList<>();
    List<Przepis> przepisy = new ArrayList<>();
    RecyclerView recyclerView;
    private ViewModel viewModel;
    HistoriaAdapter historiaAdapter;

    public HistoryFragment() {
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
        return inflater.inflate(R.layout.activity_history, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.historia_recycler);
        historiaAdapter = new HistoriaAdapter(getContext(), historias,przepisy);
        historiaAdapter.setHistoria(historias);
        recyclerView.setAdapter(historiaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getHistoria().observe(this, new Observer<List<Historia>>() {
            @Override
            public void onChanged(@Nullable List<Historia> historia) {
                historias = historia;
                historiaAdapter.setHistoria(historia);
                recyclerView.setAdapter(historiaAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        });
        viewModel.getPrzepisy().observe(this, new Observer<List<Przepis>>() {
            @Override
            public void onChanged(@Nullable List<Przepis> przepis) {
                przepisy = przepis;
                historiaAdapter.setPrzepisy(przepis);
                recyclerView.setAdapter(historiaAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        });


    }

}
