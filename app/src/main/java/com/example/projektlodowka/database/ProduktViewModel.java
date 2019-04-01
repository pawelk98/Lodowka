package com.example.projektlodowka.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ProduktViewModel extends AndroidViewModel {
    private ProduktRepository produktRepository;
    private LiveData<List<Produkt>> produkty;

    public ProduktViewModel(Application application) {
        super(application);
        produktRepository = new ProduktRepository(application);
        produkty = produktRepository.getProdukty();
    }

    public LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }

    public void insert(Produkt produkt) {
        produktRepository.insertProdukt(produkt);
    }
}
