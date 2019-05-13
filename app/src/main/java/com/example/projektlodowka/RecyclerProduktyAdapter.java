package com.example.projektlodowka;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projektlodowka.database.Produkt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerProduktyAdapter extends RecyclerView.Adapter<RecyclerProduktyAdapter.viewHolder> {

    private List<Produkt> produkty;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerProduktyAdapter(Context context, List<Produkt> produktyParam) {
        produkty = produktyParam;
        this.context = context;
    }

    void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produkt_view, viewGroup, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {

        if (produkty.get(position).getImage() == null) {
            File file = new File("drawable\\def_pic.png");
            if (file.exists()) {
                Glide.with(context).load(file).into(viewHolder.obrazek);
            }
        } else {

            byte[] ImageArray = Base64.decode(produkty.get(position).getImage(), Base64.DEFAULT);

            Glide.with(context).asBitmap().load(ImageArray).into(viewHolder.obrazek);//<<<<<-------------
        }
        viewHolder.nazwa.setText(produkty.get(position).getNazwa());

        int i = produkty.get(position).getIlosc();
        switch (produkty.get(position).getTyp()) {
            case 0:
                if (i < 500)
                    viewHolder.ilosc.setText(i + "g");
                else if (i % 1000 == 0)
                    viewHolder.ilosc.setText(i / 1000 + "kg");
                else
                    viewHolder.ilosc.setText((float) i / 1000 + "kg");
                break;

            case 1:
                if (i < 500)
                    viewHolder.ilosc.setText(i + "ml");
                else if (i % 1000 == 0)
                    viewHolder.ilosc.setText(i / 1000 + "l");
                else
                    viewHolder.ilosc.setText((float) i / 1000 + "l");
                break;

            case 2:
                if (i % 1000 == 0)
                    viewHolder.ilosc.setText(i / 1000 + "szt");
                else
                    viewHolder.ilosc.setText((float) i / 1000 + "szt");
                break;

            default:
                viewHolder.ilosc.setText(String.valueOf((float) i / 1000));
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

        CircleImageView obrazek;
        TextView nazwa;
        TextView ilosc;
        ConstraintLayout parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            obrazek = itemView.findViewById(R.id.produktImageView);
            nazwa = itemView.findViewById(R.id.produktNazwaTextView);
            ilosc = itemView.findViewById(R.id.produktIloscTextView);
            parentLayout = itemView.findViewById(R.id.parent_layout);

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

    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

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
    public void setFilter(List<Produkt> noweProdukty){

         produkty = new ArrayList<>(noweProdukty);
         notifyDataSetChanged();
    }
}
