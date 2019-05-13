package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.R;

import org.w3c.dom.Text;

import java.util.List;

public class RepositoryProdukt {
    private ProduktDao produktDao;
    private LiveData<List<Produkt>> produkty;

    RepositoryProdukt(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktDao = db.produktDao();
        produkty = produktDao.loadAllOrderNazwa();
    }


    LiveData<List<Produkt>> getProdukty() {
        return produkty;
    }


    public void insertProdukt(Activity activity, Produkt produkt) {
        new insertProduktAsyncTask(activity, produktDao).execute(produkt);
    }

    private static class insertProduktAsyncTask extends AsyncTask<Produkt, Void, Boolean> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;

        insertProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final Produkt... params) {
            Produkt p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if (p == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(mActivity, "Dodano produkt", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Produkt o podanej nazwie już istnieje", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteProdukt(Produkt produkt) {
        new deleteProduktAsyncTask(produktDao).execute(produkt);
    }

    private static class deleteProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;

        deleteProduktAsyncTask(ProduktDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Produkt... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void deleteAllProdukt() {
        new deleteAllProduktAsyncTask(produktDao).execute();
    }

    private static class deleteAllProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;

        deleteAllProduktAsyncTask(ProduktDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Produkt... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void updateProdukt(Activity activity, Produkt produkt) {
        new updateProduktAsyncTask(activity, produktDao).execute(produkt);
    }


    private static class updateProduktAsyncTask extends AsyncTask<Produkt, Void, Boolean> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;

        updateProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(Produkt... params) {
            Produkt p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if (p == null || params[0].getId() == p.getId()) {
                mAsyncTaskDao.update(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(mActivity, "Edytowano produkt", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Produkt o podanej nazwie już istnieje", Toast.LENGTH_SHORT).show();
        }
    }


    public void setEditProdukt(Activity activity, int id) {
        new setEditProduktAsyncTask(activity, produktDao).execute(id);
    }

    private static class setEditProduktAsyncTask extends AsyncTask<Integer, Void, Produkt> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;

        setEditProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Produkt doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadId(integers[0]);
        }

        @Override
        protected void onPostExecute(Produkt produkt) {
            EditText nazwa = mActivity.findViewById(R.id.produktEditNazwaEditText);
            EditText ilosc = mActivity.findViewById(R.id.produktEditIloscEditText);
            Spinner typ = mActivity.findViewById(R.id.spinner_prod_edit);

            nazwa.setText(produkt.getNazwa());

            if (produkt.getIlosc() < 500 && produkt.getTyp() != 2) {
                ilosc.setText(String.valueOf(produkt.getIlosc()));
                typ.setSelection(produkt.getTyp() + 3);
            } else if (produkt.getIlosc() >= 500 && produkt.getTyp() != 2) {
                ilosc.setText(String.valueOf((float)produkt.getIlosc() / 1000));
                typ.setSelection(produkt.getTyp());
            } else {
                ilosc.setText(String.valueOf((float)produkt.getIlosc() / 1000));
                typ.setSelection(produkt.getTyp());
            }
        }
    }


    public void setShowcaseProdukt(Activity activity, int id) { new setShowcaseProduktAsyncTask(activity, produktDao).execute(id); }

    private static class setShowcaseProduktAsyncTask extends AsyncTask<Integer, Void, Produkt> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;

        setShowcaseProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Produkt doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadId(integers[0]);
        }

        @Override
        protected void onPostExecute(Produkt produkt) {
            TextView nazwa = mActivity.findViewById(R.id.productNameFromDatabase);
            TextView ilosc = mActivity.findViewById(R.id.productIloscFromDatabase);
            TextView typ = mActivity.findViewById(R.id.productTypFromDatabase);
            int typInt = produkt.getTyp();

            nazwa.setText(produkt.getNazwa());

            if (produkt.getIlosc() < 500 && produkt.getTyp() != 2) {
                ilosc.setText(String.valueOf(produkt.getIlosc()));
                switch (typInt){
                    case 0:
                        typ.setText("g");
                        break;

                    case 1:
                        typ.setText("ml");
                        break;
                }
            } else {
                ilosc.setText(String.valueOf((float)produkt.getIlosc() / 1000));
                switch (typInt){
                    case 0:
                        typ.setText("kg");
                        break;

                    case 1:
                        typ.setText("l");
                        break;

                    case 2:
                        typ.setText("szt");
                        break;
                }
            }
        }
    }
}