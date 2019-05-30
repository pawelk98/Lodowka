package com.example.projektlodowka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projektlodowka.database.Produkt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProduktDodawaniePrzepisuAdatper extends RecyclerView.Adapter<ProduktDodawaniePrzepisuAdatper.viewHolder>{
    private List<Produkt> produkty;
    private Context context;
    private LayoutInflater inflater;

    public ProduktDodawaniePrzepisuAdatper(){}

    public ProduktDodawaniePrzepisuAdatper(Context context, List<Produkt> produkty) {
        this.produkty = produkty;
        this.context = context;
    }


    void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
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

      /*  if (produkty.get(position).getImage() == null) {
            File file = new File("drawable\\def_pic.png");
            if (file.exists()) {
                Glide.with(context).load(file).into(viewHolder.obrazek);
            }
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(produkty.get(position).getImage(), 0, produkty.get(position).getImage().length);
            Glide.with(context).load(bitmap).into(viewHolder.obrazek);
        }*/
        viewHolder.nazwa.setText(produkty.get(position).getNazwa());

        int i = produkty.get(position).getIlosc();
        switch (produkty.get(position).getTyp()) {
            case 0:
                if (i < 500)
                    viewHolder.typ.setText("g");
                else if (i % 1000 == 0)
                    viewHolder.typ.setText("kg");
                else
                    viewHolder.typ.setText("kg");
                break;

            case 1:
                if (i < 500)
                    viewHolder.typ.setText("ml");
                else if (i % 1000 == 0)
                    viewHolder.typ.setText("l");
                else
                    viewHolder.typ.setText("l");
                break;

            case 2:
                if (i % 1000 == 0)
                    viewHolder.typ.setText("szt");
                else
                    viewHolder.typ.setText("szt");
                break;

            default:
                viewHolder.typ.setText(String.valueOf((float) i / 1000));
                break;
        }
        if(viewHolder.nazwa.isChecked()==true) {
            Toast.makeText(context,produkty.get(position).getNazwa()+" jest dodana",
                    Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context,produkty.get(position).getNazwa()+" chuj",
                    Toast.LENGTH_SHORT).show();

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
        TextView typ;

        ConstraintLayout parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            //obrazek = itemView.findViewById(R.id.produktImageView);
            nazwa = itemView.findViewById(R.id.nazwaCheckBox);
            typ = itemView.findViewById(R.id.typ_dodawanie_do_przepisow);
            //parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }

    public Object getItem(int position) {
        return produkty.get(position);
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



    public void setFilter(List<Produkt> noweProdukty){

        produkty = new ArrayList<>(noweProdukty);
        notifyDataSetChanged();
    }

}
