package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Produkt>> produkty;

    public ViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        produkty = repository.getProdukty();
    }

    public LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }

    public void setEditProdukt(Activity activity, int id) { repository.setEditProdukt(activity,id); }

    public void insertProdukt(Activity activity, Produkt produkt) {
        repository.insertProdukt(activity, produkt);
    }

    public void deleteProdukt(Produkt produkt) { repository.deleteProdukt(produkt); }

    public void deleteAllProdukt() { repository.deleteAllProduct(); }

    public void updateProdukt(Activity activity, Produkt produkt) { repository.updateProdukt(activity, produkt); }
}
