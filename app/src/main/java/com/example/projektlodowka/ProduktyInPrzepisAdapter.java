package com.example.projektlodowka;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.projektlodowka.database.ProduktInPrzepis;

import java.util.ArrayList;
import java.util.List;

public class ProduktyInPrzepisAdapter extends BaseAdapter {

    private List<ProduktInPrzepis> produktInPrzepis = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public ProduktyInPrzepisAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return produktInPrzepis.size();
    }

    @Override
    public Object getItem(int position) {
        return produktInPrzepis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.produkt_w_przepisie,parent,false);
            TextView nazwa = convertView.findViewById(R.id.przepisy_nazwa_produktu);
            TextView ilosc_posiadana = convertView.findViewById(R.id.przepisy_ilosc_posiadana_produktu);
            TextView ilosc_potrzebna = convertView.findViewById(R.id.przepisy_ilosc_postrzebna_produktu);
            CheckBox checkBox = convertView.findViewById(R.id.checkBoxOpcjonalny);


            nazwa.setText(produktInPrzepis.get(position).getNazwa());

            int i = produktInPrzepis.get(position).getIlosc();
            int j = produktInPrzepis.get(position).getIloscProduktu();



            //Ustawienie ilosci posiadanej

            switch (produktInPrzepis.get(position).getTyp()) {
                case 0:
                    if (i < 500)
                        ilosc_posiadana.setText(i + "g");
                    else if (i % 1000 == 0)
                        ilosc_posiadana.setText(i / 1000 + "kg");
                    else
                        ilosc_posiadana.setText((float) i / 1000 + "kg");
                    break;

                case 1:
                    if (i < 500)
                        ilosc_posiadana.setText(i + "ml");
                    else if (i % 1000 == 0)
                        ilosc_posiadana.setText(i / 1000 + "l");
                    else
                        ilosc_posiadana.setText((float) i / 1000 + "l");
                    break;

                case 2:
                    if (i % 1000 == 0)
                        ilosc_posiadana.setText(i / 1000 + "szt");
                    else
                        ilosc_posiadana.setText((float) i / 1000 + "szt");
                    break;

                default:
                         ilosc_posiadana.setText(String.valueOf((float) i / 1000));
                    break;
            }

            //Ustawienie ilosci potrzebnej

            switch (produktInPrzepis.get(position).getTyp()) {
                case 0:
                    if (j < 500)
                        ilosc_potrzebna.setText(j + "g");
                    else if (j % 1000 == 0)
                        ilosc_potrzebna.setText(j / 1000 + "kg");
                    else
                        ilosc_potrzebna.setText((float) j / 1000 + "kg");
                    break;

                case 1:
                    if (j < 500)
                        ilosc_potrzebna.setText(j + "ml");
                    else if (j % 1000 == 0)
                        ilosc_potrzebna.setText(j / 1000 + "l");
                    else
                        ilosc_potrzebna.setText((float) j / 1000 + "l");
                    break;

                case 2:
                    if (j % 1000 == 0)
                        ilosc_potrzebna.setText(j / 1000 + "szt");
                    else
                        ilosc_potrzebna.setText((float) j / 1000 + "szt");
                    break;

                default:
                    ilosc_potrzebna.setText(String.valueOf((float) j / 1000));
                    break;
            }

            if(j>i) {
                nazwa.setTextColor(Color.RED);
                ilosc_posiadana.setTextColor(Color.RED);
                ilosc_potrzebna.setTextColor(Color.RED);
            }
            if(produktInPrzepis.get(position).isOpcjonalny()==true)
                checkBox.setChecked(true);

        }

        return convertView;
    }

    public void setProduktInPzepis(List<ProduktInPrzepis> produktInPrzepis) {
        this.produktInPrzepis = produktInPrzepis;
    }
}
