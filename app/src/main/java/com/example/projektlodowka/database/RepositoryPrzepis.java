package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class RepositoryPrzepis {

    private LiveData<List<Przepis>> przepisy;
    private PrzepisDao przepisDao;
    private ProduktPrzepisDao produktPrzepisDao;
    private ProduktDao produktDao;
    private HistoriaDao historiaDao;
    private String noc;
    private String kolacja;
    private String sniadanie;
    private String obiad;
    Application application;

    RepositoryPrzepis(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        przepisDao = db.przepisDao();
        produktDao = db.produktDao();
        historiaDao = db.historiaDao();
        produktPrzepisDao = db.produktPrzepisDao();
        przepisy = przepisDao.loadAllOrderNazwa();

        this.application = application;

        File fNoc = new File(application.getFilesDir(),"plikNoc");
        File fObiad = new File(application.getFilesDir(),"plikObiad");
        File fKolacja = new File(application.getFilesDir(),"plikKolacja");
        File fSniadanie = new File(application.getFilesDir(),"plikSniadanie");

        if(!fNoc.exists()&&!fObiad.exists()&&!fKolacja.exists()&&!fSniadanie.exists()){
            setBasics();
        }
        else {

            if (fNoc.exists())
                this.noc = readFromFile(application, "plikNoc");
            else
                setNoc(3, 0);

            if (fSniadanie.exists())
                this.sniadanie = readFromFile(application, "plikSniadanie");
            else
                setSniadanie(11,0);

            if (fNoc.exists())
                this.obiad = readFromFile(application, "plikObiad");
            else
                setObiad(16,0);

            if (fNoc.exists())
                this.kolacja = readFromFile(application, "plikKolacja");
            else
                setKolacja(21,0);

        }
    }
    public void setBasics(){
       setNoc(3,0);
       setKolacja(21,0);
       setSniadanie(11,0);
       setObiad(16,0);
    }
    public void setNoc(int godzina,int minuta){
        String h = "00",m="00";

        if(godzina <10)
            h="0"+godzina;
        else
            h=String.valueOf(godzina);
        if(minuta<10)
            m="0"+minuta;
        else
            m=String.valueOf(minuta);

        noc = h+":" +m+":00";
        writeToFile(noc,application,"plikNoc");
    }
    public void setKolacja(int godzina,int minuta){
        String h = "00",m="00";

        if(godzina <10)
            h="0"+godzina;
        else
            h=String.valueOf(godzina);
        if(minuta<10)
            m="0"+minuta;
        else
            m=String.valueOf(minuta);
        kolacja = h +":" +m+":00";
        writeToFile(kolacja,application,"plikKolacja");
    }
    public void setSniadanie(int godzina,int minuta){
        String h = "00",m="00";

        if(godzina <10)
            h="0"+godzina;
        else
            h=String.valueOf(godzina);
        if(minuta<10)
            m="0"+minuta;
        else
            m=String.valueOf(minuta);

        sniadanie = h +":" +m+":00";
        writeToFile(sniadanie,application,"plikSniadanie");
    }
    public void setObiad(int godzina,int minuta){
        String h = "00",m="00";

        if(godzina <10)
            h="0"+godzina;
        else
            h=String.valueOf(godzina);
        if(minuta<10)
            m="0"+minuta;
        else
            m=String.valueOf(minuta);

        obiad = h +":" +m+":00";
        writeToFile(obiad,application,"plikObiad");
    }

    public long getNoc(){
        if(noc==null) {
            File fNoc = new File(application.getFilesDir(),"plikNoc");
            if(fNoc.exists()){
                noc=readFromFile(application,"plikNoc");
            }
            else {
                writeToFile("03:00:00",application,"plikNoc");
                noc=readFromFile(application,"plikNoc");
            }
        }
            return Time.valueOf(noc).getTime();
    }


    public long getKolacja(){
        if(kolacja==null){
            File fkolacja = new File(application.getFilesDir(),"plikKolacja");
            if(fkolacja.exists()){
                kolacja=readFromFile(application,"plikKolacja");
            }
            else {
            writeToFile("21:00:00",application,"plikKolacja");
            kolacja=readFromFile(application,"plikKolacja");
            }
        }
            return Time.valueOf(kolacja).getTime();
    }

    public long getSniadanie() {
        if(sniadanie==null){
            File fSniadanie = new File(application.getFilesDir(),"plikSniadanie");
            if(fSniadanie.exists()){
                sniadanie=readFromFile(application,"plikSniadanie");
            }
            else {
                writeToFile("11:00:00",application,"plikSniadanie");
                sniadanie=readFromFile(application,"plikSniadanie");
            }
        }
            return Time.valueOf(sniadanie).getTime();
    }

    public long getObiad() {
        if(obiad==null){
            File fObiad = new File(application.getFilesDir(),"plikObiad");
            if(fObiad.exists()){
                obiad=readFromFile(application,"plikObiad");
            }
            else {
                writeToFile("16:00:00",application,"plikObiad");
                obiad=readFromFile(application,"plikObiad");
            }
        }
            return Time.valueOf(obiad).getTime();
    }

    public String  getStringNoc(){
        if(noc==null)
            return "brak:";
        else
            return noc;
    }

    public String getStringKolacja(){
        if(kolacja==null)
            return null;
        else
            return kolacja;
    }

    public String getStringSniadanie() {
        if(sniadanie==null)
            return null;
        else
            return sniadanie;
    }

    public String getStringObiad() {
        if(obiad==null)
            return null;
        else
            return obiad;
    }

    public void writeToFile(String data,Context context,String plik) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(plik, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



            public String readFromFile(Context context, String plik) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(plik);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    LiveData<List<Przepis>> getPrzepisy() {
        return przepisy;
    }

    public void insertPrzepis(Activity activity, Przepis przepis, List<MyTaskParams> produkty) {
        new insertPrzepisAsyncTask(activity, przepisDao, produktDao, produktPrzepisDao, produkty, przepis.getNazwa()).execute(przepis);
    }

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
            if (p == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(mActivity, "Dodano przepis", Toast.LENGTH_SHORT).show();
                new insertProduktyAsyncTask(produktPrzepisDao, produktDao, mAsyncTaskDao, przepisNazwa).execute(produkty);
            } else
                Toast.makeText(mActivity, "Przepis o podanej nazwie już istnieje", Toast.LENGTH_SHORT).show();
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
            for (int i = 0; i < params[0].size(); i++) {
                int produktId = produktDao.loadNazwa(params[0].get(i).produktName).getId();
                int przepisId = przepisDao.loadNazwa(przepisNazwa).getId();
                produktPrzepisDao.insert(new ProduktPrzepis(przepisId, produktId, params[0].get(i).ilosc, params[0].get(i).opcjonalny));
            }
            return null;
        }
    }


    public void deletePrzepis(Przepis przepis) {
        new deletePrzepisAsyncTask(przepisDao).execute(przepis);
    }

    private static class deletePrzepisAsyncTask extends AsyncTask<Przepis, Void, Void> {
        private PrzepisDao mAsyncTaskDao;

        deletePrzepisAsyncTask(PrzepisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Przepis... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void updatePrzepis(Activity activity, Przepis przepis) {
        new updatePrzepisAsyncTask(activity, przepisDao).execute(przepis);
    }

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
            if (p == null || params[0].getId() == p.getId()) {
                mAsyncTaskDao.update(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(mActivity, "Edytowano przepis", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Przepis o podanej nazwie już istnieje", Toast.LENGTH_SHORT).show();
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
            CircleImageView obrazek = mActivity.findViewById(R.id.dishImage);
            TextView poraDnia = mActivity.findViewById(R.id.poraDnia);
            byte[] array = przepis.getImage();

            if (array != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                obrazek.setImageBitmap(bitmap);
            }

            nazwa.setText(przepis.getNazwa());
            czas.setText(String.valueOf(przepis.getCzas()));
            opis.setText(przepis.getOpis());

            int pora=przepis.getPoraDnia();
            switch(pora){
                case 0:
                    poraDnia.setText("Inne");
                    break;
                case 1:
                    poraDnia.setText("Śniadanie");
                    break;
                case 2:
                    poraDnia.setText("Obiad");
                    break;
                case 3:
                    poraDnia.setText("Kolacja");
                    break;
                case 4:
                    poraDnia.setText("Śniadanie/Kolacja");
                    break;
                    default:
                        poraDnia.setText("Nie wiadomo");
                        break;
            }

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

        cookAsyncTask(Activity activity, PrzepisDao przepisDao, ProduktDao produktDao, ProduktPrzepisDao produktPrzepisDao, HistoriaDao historiaDao) {
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

            for (int i = 0; i < produkty.size(); i++) {
                Produkt produkt = produktDao.loadId(produkty.get(i).getIdProduktu());
                int ilosc = produkt.getIlosc() - (produkty.get(i).getIloscProduktu() * cookData[0].getPorcje());
                if (ilosc < 0) ilosc = 0;

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

    public void setEditPrzepis(Activity activity, int id) {
        new setEditPrzepisAsyncTask(activity, przepisDao).execute(id);
    }

    private static class setEditPrzepisAsyncTask extends AsyncTask<Integer, Void, Przepis> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;

        setEditPrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Przepis doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadId(integers[0]);
        }

        @Override
        protected void onPostExecute(Przepis przepis) {
            ImageView obrazek = mActivity.findViewById(R.id.recipkaFoto);
            EditText nazwa = mActivity.findViewById(R.id.zmienName);
            EditText czas = mActivity.findViewById(R.id.zmienTajm);
            EditText opis = mActivity.findViewById(R.id.zmienOpis);
            RadioGroup radioGroup = mActivity.findViewById(R.id.radioGroupAdd);
            RadioButton inne = mActivity.findViewById(R.id.radioInne);
            RadioButton sniadanie = mActivity.findViewById(R.id.radioSniadanie);
            RadioButton obiad = mActivity.findViewById(R.id.radioObiad);
            RadioButton kolacja = mActivity.findViewById(R.id.radioKolacja);
            RadioButton kolacjaSniadanie = mActivity.findViewById(R.id.radioSniadanieKolacja);

            byte[] array = przepis.getImage();

            if (array != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                obrazek.setImageBitmap(bitmap);
            }
            else {
                obrazek.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.custom_dish_03));
            }

            nazwa.setText(przepis.getNazwa());
            czas.setText(String.valueOf(przepis.getCzas()));
            opis.setText(przepis.getOpis());
            int pora=przepis.getPoraDnia();
            switch(pora){
                case 0:
                    inne.setChecked(true);
                    break;
                case 1:
                    sniadanie.setChecked(true);
                    break;
                case 2:
                    obiad.setChecked(true);
                    break;
                case 3:
                    kolacja.setChecked(true);
                    break;
                case 4:
                    kolacjaSniadanie.setChecked(true);
                    break;
            }

        }
    }

    public void setStartPrzepis(Activity activity) {
        new setStartPrzepisAsyncTask(activity, przepisDao, getNoc(), getSniadanie(), getObiad(), getKolacja()).execute();
    }

    private static class setStartPrzepisAsyncTask extends AsyncTask<Void, Void, Przepis> {
        PrzepisDao mAsyncTaskDao;
        Activity activity;
        long noc;
        long sniadanie;
        long obiad;
        long kolacja;

        setStartPrzepisAsyncTask(Activity activity, PrzepisDao przepisDao, long noc, long sniadanie, long obiad, long kolacja){
            mAsyncTaskDao = przepisDao;
            this.activity = activity;
            this.noc=noc;
            this.sniadanie=sniadanie;
            this.obiad=obiad;
            this.kolacja=kolacja;
        }

        @Override
        protected void onPostExecute(Przepis przepis) {
            super.onPostExecute(przepis);

            if(przepis != null) {
                ImageView obrazek = activity.findViewById(R.id.image_in_start);
                TextView nazwa = activity.findViewById(R.id.recipe_name_in_start);
                TextView id = activity.findViewById(R.id.doNotDelete);

                if(nazwa != null) {
                    byte[] array = przepis.getImage();

                    nazwa.setText(przepis.getNazwa());
                    id.setText(String.valueOf(przepis.getId()));

                    if (przepis.getImage() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                        obrazek.setImageBitmap(bitmap);
                    }
                }
            }
        }

        @Override
        protected Przepis doInBackground(Void... voids) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            long currentTime = Time.valueOf(currentDateandTime).getTime();

            List<Przepis> przepisy = new ArrayList<>();
            if(currentTime > noc && currentTime < sniadanie) {
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(1));
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(4));
            }
            else if(currentTime > sniadanie && currentTime < obiad)
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(2));
            else if(currentTime > obiad && currentTime < kolacja) {
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(3));
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(4));
            }
            else
                przepisy.addAll(mAsyncTaskDao.loadPoraDnia(0));

            if(przepisy.size() > 0) {
                return przepisy.get(new Random().nextInt(przepisy.size()));
            }
            else
            {
                przepisy.addAll(mAsyncTaskDao.loadAllList());
                if(przepisy.size() > 0)
                    return przepisy.get(new Random().nextInt(przepisy.size()));
                else
                    return null;
            }
        }
    }
}