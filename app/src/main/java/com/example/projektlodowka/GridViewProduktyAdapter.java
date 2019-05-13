package com.example.projektlodowka;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projektlodowka.database.Produkt;

import java.util.ArrayList;
import java.util.List;

public class GridViewProduktyAdapter extends BaseAdapter {

    private List<Produkt> produkty;
    private Context context;
    private LayoutInflater inflater;

    GridViewProduktyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
    }

    @Override
    public int getCount() {
        if(produkty == null)
            return 0;
        else
            return produkty.size();
    }

    public List<Produkt> getProdukty(){
        List<Produkt> nowy = new ArrayList<>();
        for(int i=0;i<getCount();i++){
            nowy.add(getProdukt(i));
        }
        return nowy;
    }

    @Override
    public Object getItem(int position) {
        return produkty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Produkt getProdukt(int position) { return produkty.get(position); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.produkt_view, parent, false);
            TextView nazwa = convertView.findViewById(R.id.przepisNazwaTextView);
            TextView ilosc = convertView.findViewById(R.id.produktIloscTextView);

            nazwa.setText(produkty.get(position).getNazwa());

            int i = produkty.get(position).getIlosc();
            switch (produkty.get(position).getTyp()) {
                case 0:
                    if(i < 500)
                        ilosc.setText(String.valueOf(i)+"g");
                    else
                        if(i%1000 == 0)
                            ilosc.setText(String.valueOf(i/1000)+"kg");
                        else
                        ilosc.setText(String.valueOf((float)i/1000)+"kg");
                    break;

                case 1:
                    if(i < 500)
                        ilosc.setText(String.valueOf(i)+"ml");
                    else
                        if(i%1000 == 0)
                            ilosc.setText(String.valueOf(i/1000)+"l");
                        else
                        ilosc.setText(String.valueOf((float)i/1000)+"l");
                    break;

                case 2:
                    if(i%1000 == 0)
                        ilosc.setText(String.valueOf(i/1000)+"szt");
                    else
                        ilosc.setText(String.valueOf((float)i/1000)+"szt");
                    break;

                    default:
                        ilosc.setText(String.valueOf((float)i/1000));
                        break;
            }
        }

        return convertView;
    }
    public void setFilter(List<Produkt> noweProdukty){

       // produkty = new ArrayList<>();
       // produkty.addAll(noweProdukty);
        setProdukty(noweProdukty);
        notifyDataSetChanged();

    }
}
