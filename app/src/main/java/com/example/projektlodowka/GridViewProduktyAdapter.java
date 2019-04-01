package com.example.projektlodowka;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.projektlodowka.database.Produkt;

import java.util.List;

public class GridViewProduktyAdapter extends BaseAdapter {

    List<Produkt> produkty;
    Context context;

    GridViewProduktyAdapter(Context context) {
        this.context = context;
    }

    void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(produkty == null)
            return 0;
        else
            return produkty.size();
    }

    @Override
    public Object getItem(int position) {
        return produkty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button button;

            if(convertView == null) {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(dpToPx(110), dpToPx(110)));
                button.setText(produkty.get(position).getNazwa());
            }
            else
                button = (Button) convertView;

        return button;
    }
}
