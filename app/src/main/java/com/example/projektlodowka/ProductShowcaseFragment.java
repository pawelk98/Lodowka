package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductShowcaseFragment extends Fragment {

    int id;
    TextView nazwa;
    TextView ilosc;
    Button edytuj;
    Button usun;
    Produkt uProdukt;
    ViewModel viewModel;
    CircleImageView circleImageView;
    ImageButton back;

    public ProductShowcaseFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_showcase, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nazwa = view.findViewById(R.id.productName);
        ilosc = view.findViewById(R.id.productMuch);
        usun = view.findViewById(R.id.produktUsunButton);
        edytuj = view.findViewById(R.id.produktEditButton);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        circleImageView = view.findViewById(R.id.productImage);
        back = view.findViewById(R.id.backBttn2);

        viewModel.setShowcaseProdukt(getActivity(), id);

        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uProdukt = new Produkt();
                uProdukt.setId(id);
                viewModel.deleteProdukt(uProdukt);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                fragmentTransaction.commit();
            }
        });

        edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                ProductEditFragment fragment = new ProductEditFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                fragmentTransaction.commit();
            }
        });
    }
}
