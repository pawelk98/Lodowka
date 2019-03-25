package com.example.projektlodowka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projektlodowka.database.Produkt;

import java.util.List;

public class ProduktyAdapter extends BaseAdapter {
    Context context;
    List<Produkt> produkt;
    LayoutInflater inflater;

    public ProduktyAdapter(Context applicationContext, List<Produkt> produkt) {
        this.context = applicationContext;
        this.produkt = produkt;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() { return produkt.size(); }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_produkty_list_adapter, null);
        TextView nazwa = view.findViewById(R.id.produktNazwaLista);
        TextView ilosc = view.findViewById(R.id.produktIloscLista);
        TextView typ = view.findViewById(R.id.produktTypLista);

        nazwa.setText(produkt.get(i).getNazwa());
        ilosc.setText(String.valueOf(produkt.get(i).getIlosc()));
        typ.setText(String.valueOf(produkt.get(i).getTyp()));
        return view;
    }

}
