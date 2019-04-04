package com.example.projektlodowka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projektlodowka.database.Przepis;

import java.util.List;

public class ListViewPrzepisyAdapter extends BaseAdapter {
    private List<Przepis> przepisy;
    private Context context;
    private LayoutInflater inflater;

    public ListViewPrzepisyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    void setPrzepisy(List<Przepis> przepisy) { this.przepisy = przepisy; };

    @Override
    public int getCount() {
        if(przepisy == null)
        return 0;
        else
        return przepisy.size();
    }

    @Override
    public Object getItem(int position) {
        return przepisy.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Przepis getPrzepis(int position) { return przepisy.get(position); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_przepis_layout, parent, false);
            TextView nazwa = convertView.findViewById(R.id.przepisNazwaTextView);
            TextView czas = convertView.findViewById(R.id.przepisCzasTextView);

            nazwa.setText(przepisy.get(position).getNazwa());
            czas.setText(String.valueOf(przepisy.get(position).getCzas()));
        }

        return convertView;
    }
}
