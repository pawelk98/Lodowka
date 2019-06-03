package com.example.projektlodowka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.database.Historia;
import com.example.projektlodowka.database.Przepis;

import java.util.ArrayList;
import java.util.List;

public class HistoriaAdapter extends RecyclerView.Adapter<HistoriaAdapter.viewHolder>{

    private  List<Historia> historia;
    private List<Przepis>  przepisy ;
    private Context context;
;


    public HistoriaAdapter(Context context, List<Historia> historia, List<Przepis> przepis) {
        //this.historia = historia;
        this.historia = historia;
        this.context = context;
        //this.przepis = przepis;
        this.przepisy = przepis;
    }

    void setHistoria(List<Historia> historia) {
        this.historia = historia;
    }
    void setPrzepisy(List<Przepis> przepisy) {
        this.przepisy = przepisy ;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate((R.layout.historia_pojedynczy_item),viewGroup,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {

        for(Przepis przepis: przepisy) {
            if(przepis.getId()==historia.get(position).getIdPrzepisu()) {
                viewHolder.nazwa.setText(przepis.getNazwa());
                break;
            }
        }

        //viewHolder.nazwa.setText(przepis.get(poz).getNazwa());
        viewHolder.data.setText(historia.get(position).getData());
        int porcja = historia.get(position).getIloscPorcji();
        viewHolder.porcje.setText(String.valueOf(porcja));
    }

    @Override
    public int getItemCount() {
        return historia.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView nazwa;
        TextView data;
        TextView porcje;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nazwa = itemView.findViewById(R.id.historia_nazwa_przepisu);
            data = itemView.findViewById(R.id.historia_data);
            porcje = itemView.findViewById(R.id.historia_ilosc_porcji);
        }
    }
}
