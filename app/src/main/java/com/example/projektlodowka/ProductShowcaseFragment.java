package com.example.projektlodowka;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktInPrzepis;
import com.example.projektlodowka.database.ProduktPrzepis;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.PrzepisInProdukt;
import com.example.projektlodowka.database.ViewModel;

import java.util.ArrayList;
import java.util.List;

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
    RecyclerView recyclerView;
    PrzepisyWProdukcieAdapter adapter;
    List<PrzepisInProdukt> przepisy = new ArrayList<>();


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
        circleImageView = view.findViewById(R.id.productImage);
        recyclerView = view.findViewById(R.id.reciepesContainingProduct);
        adapter = new PrzepisyWProdukcieAdapter(getActivity(),przepisy);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setShowcaseProdukt(getActivity(), id);

        viewModel.getPrzepisyInProdukt(id).observe(this, new Observer<List<PrzepisInProdukt>>() {
            @Override
            public void onChanged(@Nullable List<PrzepisInProdukt> przepisInProdukts) {
                przepisy=przepisInProdukts;
                adapter.setPrzepisy(przepisy);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }
        });


        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uProdukt = new Produkt();
                uProdukt.setId(id);
                viewModel.deleteProdukt(uProdukt);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
                manager.popBackStack();
                manager.popBackStack();
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
                fragmentTransaction.addToBackStack("produktyShowcase").commit();
            }
        });
    }
}
