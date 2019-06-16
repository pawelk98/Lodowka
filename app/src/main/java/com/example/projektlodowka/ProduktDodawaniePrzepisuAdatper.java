package com.example.projektlodowka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktInPrzepis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProduktDodawaniePrzepisuAdatper extends RecyclerView.Adapter<ProduktDodawaniePrzepisuAdatper.viewHolder> {
    private List<Produkt> produkty;
    private Context context;
    private boolean[] checkBoxes;
    private boolean[] opcjonalny;
    private float[] ilosci;

    public ProduktDodawaniePrzepisuAdatper(Context context, List<Produkt> produkty) {
        this.produkty = produkty;
        this.context = context;
    }


    void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
        checkBoxes = new boolean[produkty.size()];
        opcjonalny = new boolean[produkty.size()];
        ilosci = new float[produkty.size()];
    }

    @NonNull
    @Override
    public ProduktDodawaniePrzepisuAdatper.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dodawany_produkt_do_przepisu, viewGroup, false);
        ProduktDodawaniePrzepisuAdatper.viewHolder holder = new ProduktDodawaniePrzepisuAdatper.viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProduktDodawaniePrzepisuAdatper.viewHolder viewHolder, int position) {

        viewHolder.nazwa.setText(produkty.get(position).getNazwa());
        viewHolder.bind(position);
        switch (produkty.get(position).getTyp()) {
            case 0:
                viewHolder.typ.setText("kg");
                break;

            case 1:
                viewHolder.typ.setText("l");
                break;

            case 2:
                viewHolder.typ.setText("szt");
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (produkty == null)
            return 0;
        else
            return produkty.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox nazwa;
        CheckBox opcjonalnyCB;
        TextView typ;
        EditText ilosc;

        ConstraintLayout parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nazwa = itemView.findViewById(R.id.nazwaCheckBox);
            opcjonalnyCB = itemView.findViewById(R.id.checkBox2);
            typ = itemView.findViewById(R.id.typ_dodawanie_do_przepisow);
            ilosc = itemView.findViewById(R.id.ilosc_produktu_w_przepisie);


            ilosc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = ilosc.getText().toString();

                    if (text.length() > 0)
                        ilosci[getAdapterPosition()] = Float.parseFloat(text);
                }
            });

            nazwa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxes[getAdapterPosition()] = isChecked;
                }
            });

            opcjonalnyCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    opcjonalny[getAdapterPosition()] = isChecked;
                }
            });
        }

        void bind(int position) {
            nazwa.setChecked(checkBoxes[position]);
            opcjonalnyCB.setChecked(opcjonalny[position]);
            ilosc.setText(String.valueOf(ilosci[position]));
        }
    }

    public Object getItem(int position) {
        return produkty.get(position);
    }

    public boolean checked(int position) {
        return checkBoxes[position];
    }

    public String nazwaProduktu(int position) {
        return produkty.get(position).getNazwa();
    }

    public boolean opcjonalnyProdukt(int position) {
        return opcjonalny[position];
    }

    public int iloscProduktu(int position) {
        return (int)(ilosci[position] * 1000);
    }

    public String getItemName(int position) {
        return produkty.get(position).getNazwa();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Produkt getProdukt(int position) {
        return produkty.get(position);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream.toByteArray();
    }


    public void setFilter(List<Produkt> noweProdukty) {

        produkty = new ArrayList<>(noweProdukty);
        notifyDataSetChanged();
    }

    public void setSkladniki(List<ProduktInPrzepis> skladniki) {
        for (int i = 0; i < produkty.size(); i++) {
            for(int j = 0; j < skladniki.size(); j++) {
                if(produkty.get(i).getNazwa().equals(skladniki.get(j).getNazwa())) {
                    checkBoxes[i] = true;
                    opcjonalny[i] = skladniki.get(j).isOpcjonalny();
                    ilosci[i] = (float)(skladniki.get(j).getIloscProduktu())/1000;
                }
            }
        }
    }

}
