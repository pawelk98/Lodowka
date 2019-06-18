package com.example.projektlodowka;

import android.arch.lifecycle.Observer;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projektlodowka.database.MyTaskParams;
import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktPrzepis;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PrzepisAddFragment extends Fragment {

    Button dodaj;
    EditText nazwa;
    EditText czas;
    EditText opis;
    ViewModel viewModel;
    RecyclerView recyclerView;
    ProduktDodawaniePrzepisuAdatper adapter;
    Przepis przepis;
    RadioGroup radioGroup;
    RadioButton inne,sniadanie,obiad,kolacja,kolacjaSniadanie;
    List<Produkt> produkty = new ArrayList<>();
    List<MyTaskParams> produktyDoDodania = new ArrayList<>();
    ImageButton dodajObrazek;
    public  byte [] obrazBajty;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;


    public PrzepisAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_przepis_add, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_dodaj_produkt_do_przepisu);
        adapter = new ProduktDodawaniePrzepisuAdatper(getActivity(), produkty);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        radioGroup = view.findViewById(R.id.radioGroupAdd);
        inne = view.findViewById(R.id.radioInneAdd);
        sniadanie = view.findViewById(R.id.radioSniadanieAdd);
        obiad = view.findViewById(R.id.radioObiadAdd);
        kolacja = view.findViewById(R.id.radioKolacjaAdd);
        kolacjaSniadanie = view.findViewById(R.id.radioSniadanieKolacjaAdd);
        dodajObrazek = view.findViewById(R.id.recipeImageAdd);

        viewModel.getProdukty().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(@Nullable final List<Produkt> produkt) {
                produkty = produkt;
                adapter.setProdukty(produkt);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        dodajObrazek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        nazwa = view.findViewById(R.id.przepis_dodaj_nazwe);
        czas = view.findViewById(R.id.przepis_dodaj_czas);
        opis = view.findViewById(R.id.przepis_dodaj_opis);
        dodaj = view.findViewById(R.id.dodaj_przepis);


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (adapter.checked(i)) {
                        String nazwa = adapter.nazwaProduktu(i);
                        int ilosc = adapter.iloscProduktu(i);
                        boolean opcjonalny = adapter.opcjonalnyProdukt(i);

                        produktyDoDodania.add(new MyTaskParams(nazwa, ilosc, opcjonalny));
                    }
                }
                if (nazwa.getText().toString().trim().length() != 0
                        && czas.getText().toString().trim().length() != 0) {
                    Przepis uPrzepis = new Przepis();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    int pora=0;
                    if(selectedId == sniadanie.getId()) {
                        pora=1;
                    }
                    else if(selectedId == obiad.getId()) {
                        pora=2;
                    }
                    else if(selectedId == kolacja.getId()) {
                        pora=3;
                    }
                    else if(selectedId == kolacjaSniadanie.getId()) {
                        pora=4;
                    }
                    else {
                        pora=0;
                    }

                    przepis = new Przepis(nazwa.getText().toString().toLowerCase(),
                            Integer.valueOf(czas.getText().toString()),
                            opis.getText().toString().toLowerCase(), pora);
                    przepis.setImage(obrazBajty);
                    viewModel.insertPrzepis(getActivity(), przepis, produktyDoDodania);
                    produktyDoDodania.clear();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new RecipeFragment());
                    fragmentTransaction.commit();
                }
            }
        });
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
