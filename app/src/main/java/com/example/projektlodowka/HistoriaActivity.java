package com.example.projektlodowka;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.projektlodowka.database.Historia;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoriaActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Historia> historias = new ArrayList<>();
    List<Przepis> przepisy = new ArrayList<>();
    RecyclerView recyclerView;
    private ViewModel viewModel;
    HistoriaAdapter historiaAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    recyclerView = findViewById(R.id.historia_recycler);
    historiaAdapter = new HistoriaAdapter(this, historias,przepisy);

    viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    context = this;
    viewModel.getHistoria().observe(this, new Observer<List<Historia>>() {
        @Override
        public void onChanged(@Nullable List<Historia> historia) {
            historias = historia;
            historiaAdapter.setHistoria(historia);
            recyclerView.setAdapter(historiaAdapter);
        }
    });
        viewModel.getPrzepisy().observe(this, new Observer<List<Przepis>>() {
            @Override
            public void onChanged(@Nullable List<Przepis> przepis) {
                przepisy = przepis;
                historiaAdapter.setPrzepisy(przepis);
                recyclerView.setAdapter(historiaAdapter);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
