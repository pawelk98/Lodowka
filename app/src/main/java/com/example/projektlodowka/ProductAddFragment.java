package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText nazwa;
    EditText ilosc;
    //EditText typ;
    Button dodaj;
    ImageButton dodajObrazek;
    int type;
    Produkt p;
    ViewModel viewModel;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
   public byte [] obrazBajty;

    public ProductAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Spinner typ = view.findViewById(R.id.spinner_prod_edit);
        ArrayAdapter<CharSequence> adapter_prod = ArrayAdapter.createFromResource(this.getActivity(), R.array.types, android.R.layout.simple_spinner_item);
        adapter_prod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typ.setAdapter(adapter_prod);
        typ.setOnItemSelectedListener(this);

        nazwa = view.findViewById(R.id.produktEditNazwaEditText);
        ilosc = view.findViewById(R.id.produktEditIloscEditText);
        // typ = view.findViewById(R.id.spinner_prod);
        dodaj = view.findViewById(R.id.produktEditButton);
        dodajObrazek = view.findViewById(R.id.productImageAdd);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        dodajObrazek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nazwa.getText().toString().trim().length() != 0
                        && ilosc.getText().toString().trim().length() != 0) {

                    switch (type) {
                        case 0:
                            p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.valueOf(ilosc.getText().toString()) * 1000);
                            break;
                        case 1:
                            p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.valueOf(ilosc.getText().toString()) * 1000);
                            break;
                        case 2:
                            p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type),
                                    Integer.valueOf(ilosc.getText().toString()) * 1000);
                            break;
                        case 3:
                            p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type - 3),
                                    Integer.valueOf(ilosc.getText().toString()));
                            break;
                        case 4:
                            p = new Produkt(nazwa.getText().toString().trim().toLowerCase(), Integer.valueOf(type - 3),
                                    Integer.valueOf(ilosc.getText().toString()));
                            break;
                    }

                    viewModel.insertProdukt(getActivity(), p);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                    fragmentTransaction.commit();
                }
                p.setImage(obrazBajty);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            dodajObrazek.setImageURI(imageUri);
            obrazBajty=convertImageToByte(imageUri);
        }
    }

    public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
