package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.R;

import java.util.ArrayList;
import java.util.List;

public class RepositoryPrzepis {

    private LiveData<List<Przepis>> przepisy;
    private PrzepisDao przepisDao;
    private ProduktPrzepisDao produktPrzepisDao;
    private ProduktDao produktDao;
    private HistoriaDao historiaDao;

    RepositoryPrzepis(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        przepisDao = db.przepisDao();
        produktDao = db.produktDao();
        historiaDao = db.historiaDao();
        produktPrzepisDao = db.produktPrzepisDao();
        przepisy = przepisDao.loadAllOrderNazwa();
    }




    LiveData<List<Przepis>> getPrzepisy() { return przepisy; }


    public void insertPrzepis(Activity activity, Przepis przepis, List<MyTaskParams> produkty) { new insertPrzepisAsyncTask(activity, przepisDao, produktDao, produktPrzepisDao, produkty, przepis.getNazwa()).execute(przepis); }

    private static class insertPrzepisAsyncTask extends AsyncTask<Przepis, Void, Boolean> {
        private PrzepisDao mAsyncTaskDao;
        private ProduktPrzepisDao produktPrzepisDao;
        private ProduktDao produktDao;
        private String przepisNazwa;
        private Activity mActivity;
        List<MyTaskParams> produkty;
        insertPrzepisAsyncTask(Activity activity, PrzepisDao dao, ProduktDao produktDao, ProduktPrzepisDao produktPrzepisDao, List<MyTaskParams> produkty, String przepisNazwa) {
            this.produktDao = produktDao;
            this.produktPrzepisDao = produktPrzepisDao;
            this.produkty = new ArrayList<>(produkty);
            this.przepisNazwa = przepisNazwa;
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final Przepis... params) {
            Przepis p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if(p == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            }
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean) {
                Toast.makeText(mActivity, "Dodano przepis", Toast.LENGTH_SHORT).show();
                new insertProduktyAsyncTask(produktPrzepisDao, produktDao, mAsyncTaskDao, przepisNazwa).execute(produkty);
            }
            else
                Toast.makeText(mActivity,"Przepis o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    private static class insertProduktyAsyncTask extends AsyncTask<List<MyTaskParams>, Void, Void> {
        private ProduktPrzepisDao produktPrzepisDao;
        private PrzepisDao przepisDao;
        private ProduktDao produktDao;
        private String przepisNazwa;

        insertProduktyAsyncTask(ProduktPrzepisDao produktPrzepisDao, ProduktDao produktDao, PrzepisDao przepisDao, String przepisNazwa) {
            this.produktPrzepisDao = produktPrzepisDao;
            this.przepisDao = przepisDao;
            this.produktDao = produktDao;
            this.przepisNazwa = przepisNazwa;
        }

        @Override
        protected Void doInBackground(final List<MyTaskParams>... params) {
            for(int i = 0; i < params[0].size(); i++) {
                int produktId = produktDao.loadNazwa(params[0].get(i).produktName).getId();
                int przepisId = przepisDao.loadNazwa(przepisNazwa).getId();
                produktPrzepisDao.insert(new ProduktPrzepis(przepisId, produktId, params[0].get(i).ilosc, params[0].get(i).opcjonalny));
            }
            return null;
        }
    }


    public void deletePrzepis(Przepis przepis) { new deletePrzepisAsyncTask(przepisDao).execute(przepis); }

    private static class deletePrzepisAsyncTask extends AsyncTask<Przepis, Void, Void> {
        private PrzepisDao mAsyncTaskDao;
        deletePrzepisAsyncTask(PrzepisDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Przepis... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void updatePrzepis(Activity activity, Przepis przepis) { new updatePrzepisAsyncTask(activity, przepisDao).execute(przepis); }

    private static class updatePrzepisAsyncTask extends AsyncTask<Przepis, Void, Boolean> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;
        updatePrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(Przepis... params) {
            Przepis p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if(p == null || params[0].getId() == p.getId()) {
                mAsyncTaskDao.update(params[0]);
                return true;
            }
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean)
                Toast.makeText(mActivity,"Edytowano przepis",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity,"Przepis o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllPrzepis() {
        new RepositoryPrzepis.deleteAllPrzepisAsyncTask(przepisDao).execute();
    }

    private static class deleteAllPrzepisAsyncTask extends AsyncTask<Przepis, Void, Void> {
        private PrzepisDao mAsyncTaskDao;

        deleteAllPrzepisAsyncTask(PrzepisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Przepis... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void setShowPrzepis(Activity activity, int id) {
        new setShowPrzepisAsyncTask(activity, przepisDao).execute(id);
    }

    private static class setShowPrzepisAsyncTask extends AsyncTask<Integer, Void, Przepis> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;

        setShowPrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Przepis doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadId(integers[0]);
        }

        @Override
        protected void onPostExecute(Przepis przepis) {
            TextView nazwa = mActivity.findViewById(R.id.przepisShowNazwaTextView);
            TextView czas = mActivity.findViewById(R.id.przepisShowCzasTextView);
            TextView opis = mActivity.findViewById(R.id.przepisShowOpisTextView);

            nazwa.setText(przepis.getNazwa());
            czas.setText(String.valueOf(przepis.getCzas()));
            opis.setText(przepis.getOpis());
        }
    }

    public void cook(Activity activity, String przepisNazwa, String data, int ilePorcji) {
        CookData cookData = new CookData(przepisNazwa, data, ilePorcji);
        new cookAsyncTask(activity, przepisDao, produktDao, produktPrzepisDao, historiaDao).execute(cookData);
    }

    private static class cookAsyncTask extends AsyncTask<CookData, Void, Void> {
        Activity activity;
        PrzepisDao przepisDao;
        ProduktPrzepisDao produktPrzepisDao;
        ProduktDao produktDao;
        HistoriaDao historiaDao;

        cookAsyncTask(Activity activity, PrzepisDao przepisDao, ProduktDao produktDao, ProduktPrzepisDao produktPrzepisDao, HistoriaDao historiaDao){
            this.activity = activity;
            this.produktPrzepisDao = produktPrzepisDao;
            this.przepisDao = przepisDao;
            this.produktDao = produktDao;
            this.historiaDao = historiaDao;
        }

        @Override
        protected Void doInBackground(CookData... cookData) {
            Przepis przepis = przepisDao.loadNazwa(cookData[0].getPrzepis());
            int id = przepis.getId();
            List<ProduktPrzepis> produkty = new ArrayList<>(produktPrzepisDao.loadPrzepisList(id));

            for(int i = 0; i < produkty.size(); i++) {
                Produkt produkt = produktDao.loadId(produkty.get(i).getIdProduktu());
                int ilosc = produkt.getIlosc() - (produkty.get(i).getIloscProduktu() * cookData[0].getPorcje());
                if(ilosc < 0) ilosc = 0;

                produkt.setIlosc(ilosc);
                produktDao.update(produkt);
            }

            Historia historia = new Historia(przepis.getId(), cookData[0].getData(), cookData[0].getPorcje());
            historiaDao.insert(historia);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(activity, "Smacznego!", Toast.LENGTH_SHORT).show();
        }
    }
}
