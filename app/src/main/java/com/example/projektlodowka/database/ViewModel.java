package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private RepositoryProdukt repositoryProdukt;
    private RepositoryPrzepis repositoryPrzepis;
    private RepositoryProduktPrzepis repositoryProduktPrzepis;
    private LiveData<List<Produkt>> produkty;
    private LiveData<List<Przepis>> przepisy;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;

    public ViewModel(Application application) {
        super(application);
        repositoryProdukt = new RepositoryProdukt(application);
        repositoryPrzepis = new RepositoryPrzepis(application);
        repositoryProduktPrzepis = new RepositoryProduktPrzepis(application);
        produkty = repositoryProdukt.getProdukty();
        przepisy = repositoryPrzepis.getPrzepisy();
        produktyPrzepisy = repositoryProduktPrzepis.getProduktyPrzepisy();
    }

    public LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }

    public void setEditProdukt(Activity activity, int id) { repositoryProdukt.setEditProdukt(activity,id); }

    public void insertProdukt(Activity activity, Produkt produkt) { repositoryProdukt.insertProdukt(activity, produkt); }

    public void deleteProdukt(Produkt produkt) { repositoryProdukt.deleteProdukt(produkt); }

    public void deleteAllProdukt() { repositoryProdukt.deleteAllProduct(); }

    public void updateProdukt(Activity activity, Produkt produkt) { repositoryProdukt.updateProdukt(activity, produkt); }

    public LiveData<List<Przepis>> getPrzepisy() { return przepisy; }

    public void setShowPrzepis(Activity activity, int id) { repositoryPrzepis.setShowPrzepis(activity,id); }

    public void insertPrzepis(Activity activity, Przepis przepis) { repositoryPrzepis.insertPrzepis(activity, przepis); }

    public void deletePrzepis(Przepis przepis) { repositoryPrzepis.deletePrzepis(przepis); }

    public void updatePrzepis(Activity activity, Przepis przepis) { repositoryPrzepis.updatePrzepis(activity, przepis); }

    public LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() { return produktyPrzepisy; }

    public void insertProduktPrzepis(ProduktPrzepis produktPrzepis) { repositoryProduktPrzepis.insertProduktPrzepis(produktPrzepis); }

    public void deleteProduktPrzepis(ProduktPrzepis produktPrzepis) { repositoryProduktPrzepis.deleteProduktPrzepis(produktPrzepis); }

    public void updateProduktPrzepis(ProduktPrzepis produktPrzepis) { repositoryProduktPrzepis.updateProduktPrzepis(produktPrzepis); }
}
