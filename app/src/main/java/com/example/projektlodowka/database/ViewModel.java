package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private RepositoryProdukt repositoryProdukt;
    private RepositoryPrzepis repositoryPrzepis;
    private RepositoryProduktPrzepis repositoryProduktPrzepis;
    private RepositoryMinutnik repositoryMinutnik;
    private RepositoryPoraDnia repositoryPoraDnia;
    private RepositoryHistoria repositoryHistoria;

    private LiveData<List<Produkt>> produkty;
    private LiveData<List<Przepis>> przepisy;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;
    private LiveData<List<Minutnik>> minutniki;
    private LiveData<List<PoraDnia>> poraDnia;
    private LiveData<List<Historia>> historia;

    public ViewModel(Application application) {
        super(application);
        repositoryProdukt = new RepositoryProdukt(application);
        repositoryPrzepis = new RepositoryPrzepis(application);
        repositoryProduktPrzepis = new RepositoryProduktPrzepis(application);
        repositoryMinutnik = new RepositoryMinutnik(application);
        repositoryPoraDnia = new RepositoryPoraDnia(application);
        repositoryHistoria = new RepositoryHistoria(application);

        produkty = repositoryProdukt.getProdukty();
        przepisy = repositoryPrzepis.getPrzepisy();
        produktyPrzepisy = repositoryProduktPrzepis.getProduktyPrzepisy();
        minutniki = repositoryMinutnik.getMinutniki();
        poraDnia = repositoryPoraDnia.getPoryDnia();
        historia = repositoryHistoria.getHistoria();
    }

    public LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }

    public void setEditProdukt(Activity activity, int id) { repositoryProdukt.setEditProdukt(activity,id); }

    public void setShowcaseProdukt(Activity activity, int id) { repositoryProdukt.setShowcaseProdukt(activity, id); }

    public void insertProdukt(Activity activity, Produkt produkt) { repositoryProdukt.insertProdukt(activity, produkt); }

    public void deleteProdukt(Produkt produkt) { repositoryProdukt.deleteProdukt(produkt); }

    public void deleteAllProdukt() { repositoryProdukt.deleteAllProdukt(); }

    public void updateProdukt(Activity activity, Produkt produkt) { repositoryProdukt.updateProdukt(activity, produkt); }

    public LiveData<List<Przepis>> getPrzepisy() { return przepisy; }

    public void setShowPrzepis(Activity activity, int id) { repositoryPrzepis.setShowPrzepis(activity,id); }

    public void insertPrzepis(Activity activity, Przepis przepis, List<MyTaskParams> produkty) { repositoryPrzepis.insertPrzepis(activity, przepis, produkty); }

    public void deletePrzepis(Przepis przepis) { repositoryPrzepis.deletePrzepis(przepis); }

    public void deleteAllPrzepis() { repositoryPrzepis.deleteAllPrzepis(); }

    public void updatePrzepis(Activity activity, Przepis przepis) { repositoryPrzepis.updatePrzepis(activity, przepis); }

    public void setNoc(int godzina,int minuta){repositoryPrzepis.setNoc(godzina,minuta);}

    public void setKolacja(int godzina,int minuta){repositoryPrzepis.setKolacja(godzina, minuta);}

    public void setSniadanie(int godzina,int minuta){repositoryPrzepis.setSniadanie(godzina, minuta);}

    public void setObiad(int godzina,int minuta){repositoryPrzepis.setObiad(godzina, minuta);}

    public void setBasics(){repositoryPrzepis.setBasics();}

    public long getNoc(){return repositoryPrzepis.getNoc();}

    public long getKolacja(){ return repositoryPrzepis.getKolacja();}

    public long getSniadanie(){ return repositoryPrzepis.getSniadanie();}

    public long getObiad(){ return repositoryPrzepis.getObiad();}

    public String getStringNoc(){return repositoryPrzepis.getStringNoc();}

    public String getStringKolacja(){ return repositoryPrzepis.getStringKolacja();}

    public String getStringSniadanie(){ return repositoryPrzepis.getStringSniadanie();}

    public String getStringObiad(){ return repositoryPrzepis.getStringObiad();}

    public String readFromFile(Context context, String plik){return repositoryPrzepis.readFromFile(context,plik);}

    public LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() { return produktyPrzepisy; }

    public LiveData<List<ProduktInPrzepis>> getProduktyInPrzepis(int idPrzepisu) { return repositoryProduktPrzepis.getProduktyInPrzepis(idPrzepisu); }

    public LiveData<List<PrzepisInProdukt>> getPrzepisyInProdukt(int idProduktu) { return repositoryProduktPrzepis.getPrzepisyInProdukt(idProduktu); }

    public LiveData<List<ProduktPrzepis>> getProduktyPrzepisyIdPrzepis(int idPrzepisu) { return repositoryProduktPrzepis.getProduktyPrzepisyIdPrzepis(idPrzepisu); }

    public void insertProduktPrzepis(ProduktPrzepis produktPrzepis) { repositoryProduktPrzepis.insertProduktPrzepis(produktPrzepis); }

    public void insertProduktPrzepisByName(String przepisName, String produktName, int ilosc, boolean opcjonalny) {
        repositoryProduktPrzepis.insertProduktPrzepisByName(przepisName, produktName, ilosc, opcjonalny);
    }

    public void deleteProdukty(int idPrzepisu) { repositoryProduktPrzepis.deleteProdukty(idPrzepisu); }

    public void setEditPrzepis(Activity activity, int id) { repositoryPrzepis.setEditPrzepis(activity, id); }

    public void setStartPrzepis(Activity activity) { repositoryPrzepis.setStartPrzepis(activity); }

    public void deleteProduktPrzepis(ProduktPrzepis... produktPrzepis) { repositoryProduktPrzepis.deleteProduktPrzepis(produktPrzepis); }

    public void updateProduktPrzepis(ProduktPrzepis produktPrzepis) { repositoryProduktPrzepis.updateProduktPrzepis(produktPrzepis); }

    public LiveData<List<Minutnik>> getMinutniki() { return minutniki; }

    public LiveData<List<Minutnik>> getMinutnikiPrzepis(int id) { return repositoryMinutnik.getMinutnikiPrzepis(id); }

    public void insertMinutnik (Activity activity, Minutnik minutnik) { repositoryMinutnik.insertMinutnik(activity, minutnik); }

    public void deleteMinutnik (Minutnik minutnik) { repositoryMinutnik.deleteMinutnik(minutnik); }

    public LiveData<List<PoraDnia>> getPoraDnia() { return poraDnia; }

    public LiveData<List<PoraDnia>> getPoraDniaPrzepis(int id) { return repositoryPoraDnia.getPoryDniaPrzepis(id); }

    public void insertPoraDnia (Activity activity, PoraDnia poraDnia) { repositoryPoraDnia.insertPoraDnia(activity, poraDnia); }

    public void deletePoraDnia (PoraDnia poraDnia) { repositoryPoraDnia.deletePoraDnia(poraDnia); }

    public LiveData<List<Historia>> getHistoria() { return historia; }

    public void insertHistoria (Historia historia) { repositoryHistoria.insertHistoria(historia); }

    public void deleteHistoria (Historia historia) { repositoryHistoria.deleteHistoria(historia); }

    public void deleteAllHistoria () { repositoryHistoria.deleteAllHistoria(); }

    public void cook(Activity activity, String przepisNazwa, String data, int ilePorcji) { repositoryPrzepis.cook(activity, przepisNazwa, data, ilePorcji); }
}
