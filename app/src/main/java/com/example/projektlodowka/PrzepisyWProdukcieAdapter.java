package com.example.projektlodowka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.PrzepisInProdukt;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrzepisyWProdukcieAdapter extends RecyclerView.Adapter<PrzepisyWProdukcieAdapter.viewHolder> {
    private List<PrzepisInProdukt> przepisy;
    private Context context;

    public  PrzepisyWProdukcieAdapter(){}
    public PrzepisyWProdukcieAdapter(Context context, List<PrzepisInProdukt> przepisy) {
        this.przepisy = przepisy;
        this.context = context;
    }
    void setPrzepisy(List<PrzepisInProdukt> przepisy) { this.przepisy=przepisy;}

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.przepisy_zawierajace_produkt, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {

        if(przepisy.get(position).getObrazek()==null) {
            Glide.with(context).load(R.drawable.custom_dish_02).into(viewHolder.obrazek);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(przepisy.get(position).getObrazek(),0,przepisy.get(position).getObrazek().length);
            Glide.with(context).load(bitmap).into(viewHolder.obrazek);
        }
        viewHolder.nazwa.setText(przepisy.get(position).getNazwa());

    }

    @Override
    public int getItemCount() {
        if(przepisy == null)
             return 0;
        else
            return przepisy.size();
    }

    @Override
    public long getItemId(int position){return position;}
    public PrzepisInProdukt
    getPrzepis(int position){return przepisy.get(position);}

    public class viewHolder extends RecyclerView.ViewHolder{

        CircleImageView obrazek;
        TextView nazwa;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            obrazek = itemView.findViewById(R.id.obrazekPrzepisuWProdukcie);
            nazwa = itemView.findViewById(R.id.nazwaPrzepisuWProdukcie);
        }
    }


    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private RecyclerProduktyAdapter.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final RecyclerProduktyAdapter.ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
