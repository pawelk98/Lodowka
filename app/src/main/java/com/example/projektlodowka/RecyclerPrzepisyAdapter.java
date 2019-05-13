package com.example.projektlodowka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
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
import com.example.projektlodowka.database.Przepis;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerPrzepisyAdapter extends  RecyclerView.Adapter<RecyclerPrzepisyAdapter.viewHolder> {

    private List<Przepis> przepisy;
    private Context context;

    public RecyclerPrzepisyAdapter( Context context,List<Przepis> przepisyParam) {
        przepisy = przepisyParam;
        this.context = context;
    }
    void setPrzepisy(List<Przepis> przepisy){
        this.przepisy=przepisy;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.konkretny_przepis, viewGroup, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {

        if (przepisy.get(position).getImage() == null) {
            File file = new File("drawable\\custom_dish_03.png");
            if (file.exists()) {
                Glide.with(context).load(file).into(viewHolder.obrazek);
            }
        } else {

            byte[] ImageArray = Base64.decode(przepisy.get(position).getImage(), Base64.DEFAULT);

            Glide.with(context).asBitmap().load(ImageArray).into(viewHolder.obrazek);//<<<<<-------------
        }
        viewHolder.nazwa.setText(przepisy.get(position).getNazwa());
        int i= przepisy.get(position).getCzas();
        viewHolder.czas.setText(i + " min");
    }

    @Override
    public int getItemCount() {
        if (przepisy == null)
            return 0;
        else
            return przepisy.size();
    }
    public Przepis getPrzepis(int position) {
        return przepisy.get(position);
    }




    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private RecyclerPrzepisyAdapter.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final RecyclerPrzepisyAdapter.ClickListener clicklistener) {

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





    public class viewHolder extends RecyclerView.ViewHolder {

        CircleImageView obrazek;
        TextView nazwa;
        TextView czas;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            obrazek = itemView.findViewById(R.id.obrazek_przepisu);
            nazwa = itemView.findViewById(R.id.textview_nazwa_przepisu);
            czas = itemView.findViewById(R.id.textview_czas);

        }
    }
}
