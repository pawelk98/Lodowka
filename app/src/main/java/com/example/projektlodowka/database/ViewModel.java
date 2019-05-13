package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Produkt>> produkty;
    private LiveData<List<Przepis>> przepisy;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;

    public ViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        produkty = repository.getProdukty();
        przepisy = repository.getPrzepisy();
        produktyPrzepisy = repository.getProduktyPrzepisy();
    }

    public LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }

    public void setEditProdukt(Activity activity, int id) { repository.setEditProdukt(activity,id); }

    public void insertProdukt(Activity activity, Produkt produkt) { repository.insertProdukt(activity, produkt); }

    public void deleteProdukt(Produkt produkt) { repository.deleteProdukt(produkt); }

    public void deleteAllProdukt() { repository.deleteAllProduct(); }

    public void updateProdukt(Activity activity, Produkt produkt) { repository.updateProdukt(activity, produkt); }

    public LiveData<List<Przepis>> getPrzepisy() { return przepisy; }

    public void insertPrzepis(Activity activity, Przepis przepis) { repository.insertPrzepis(activity, przepis); }

    public void deletePrzepis(Przepis przepis) { repository.deletePrzepis(przepis); }

    public void updatePrzepis(Activity activity, Przepis przepis) { repository.updatePrzepis(activity, przepis); }

    public LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() { return produktyPrzepisy; }

    public void insertProduktPrzepis(ProduktPrzepis produktPrzepis) { repository.insertProduktPrzepis(produktPrzepis); }

    public void deleteProduktPrzepis(ProduktPrzepis produktPrzepis) { repository.deleteProduktPrzepis(produktPrzepis); }

    public void updateProduktPrzepis(ProduktPrzepis produktPrzepis) { repository.updateProduktPrzepis(produktPrzepis); }
}
