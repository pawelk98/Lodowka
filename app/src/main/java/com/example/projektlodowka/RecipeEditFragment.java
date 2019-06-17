package com.example.projektlodowka;


import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.projektlodowka.database.MyTaskParams;
import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ProduktInPrzepis;
import com.example.projektlodowka.database.Przepis;
import com.example.projektlodowka.database.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeEditFragment extends Fragment {

    Button zmienZdjecie;
    Button usunZdjecie;
    Button zapisz;
    Button usun;
    ImageView obrazek;
    EditText nazwa;
    EditText czas;
    EditText opis;
    Uri imageUri;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    RadioButton  inne,sniadanie,obiad,kolacja,kolacjaSniadanie;
    ProduktDodawaniePrzepisuAdatper adapter;
    public byte [] obrazBajty;
    private static final int PICK_IMAGE = 100;

    Przepis przepis;
    List<Produkt> produkty = new ArrayList<>();
    List<MyTaskParams> produktyDoEdycji = new ArrayList<>();
    List<ProduktInPrzepis> skladniki;
    List<MyTaskParams> produktyDoDodania;

    int idPrzepisu;
    String nazwaPrzepisu;

    ViewModel viewModel;
    int x;

    public RecipeEditFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPrzepisu = getArguments().getInt("id");
            nazwaPrzepisu = getArguments().getString("przepisName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produktyDoDodania = new ArrayList<>();

        radioGroup = view.findViewById(R.id.radioGroupAdd);
        inne = view.findViewById(R.id.radioInne);
        sniadanie = view.findViewById(R.id.radioSniadanie);
        obiad = view.findViewById(R.id.radioObiad);
        kolacja = view.findViewById(R.id.radioKolacja);
        kolacjaSniadanie = view.findViewById(R.id.radioSniadanieKolacja);
        zmienZdjecie = view.findViewById(R.id.zmienFoto);
        usunZdjecie = view.findViewById(R.id.usunFoto);
        zapisz = view.findViewById(R.id.zapiszBaton);
        usun = view.findViewById(R.id.usunBaton);
        obrazek = view.findViewById(R.id.recipkaFoto);
        nazwa = view.findViewById(R.id.zmienName);
        czas = view.findViewById(R.id.zmienTajm);
        opis = view.findViewById(R.id.zmienOpis);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.setEditPrzepis(getActivity(), idPrzepisu);

        recyclerView = view.findViewById(R.id.produkty_w_recipce_zmien);
        adapter = new ProduktDodawaniePrzepisuAdatper(getActivity(), produkty);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getProdukty().observe(this, new Observer<List<Produkt>>() {
            @Override
            public void onChanged(@Nullable final List<Produkt> produkt) {
                produkty = produkt;
                adapter.setProdukty(produkt);

                if(skladniki != null && skladniki.size() != 0){
                    adapter.setSkladniki(skladniki);
                }

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        viewModel.getProduktyInPrzepis(idPrzepisu).observe(this, new Observer<List<ProduktInPrzepis>>() {
            @Override
            public void onChanged(@Nullable final List<ProduktInPrzepis> produktInPrzepis) {
                skladniki = produktInPrzepis;
            }
        });


        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (adapter.checked(i)) {
                        String nazwaP = adapter.nazwaProduktu(i);
                        int ilosc = adapter.iloscProduktu(i);
                        boolean opcjonalny = adapter.opcjonalnyProdukt(i);

                        produktyDoDodania.add(new MyTaskParams(nazwa.getText().toString(),nazwaP, ilosc, opcjonalny));
                    }
                }
                Przepis uPrzepis = new Przepis();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if(selectedId == sniadanie.getId()) {
                    uPrzepis.setPoraDnia(1);
                }
                else if(selectedId == obiad.getId()) {
                    uPrzepis.setPoraDnia(2);
                }
                else if(selectedId == kolacja.getId()) {
                    uPrzepis.setPoraDnia(3);
                }
                else if(selectedId == kolacjaSniadanie.getId()) {
                    uPrzepis.setPoraDnia(4);
                }
                else {
                    uPrzepis.setPoraDnia(0);
                }


                uPrzepis.setId(idPrzepisu);
                uPrzepis.setNazwa(nazwa.getText().toString());
                uPrzepis.setCzas(Integer.parseInt(czas.getText().toString()));
                uPrzepis.setOpis(opis.getText().toString());



                Drawable d1 = obrazek.getDrawable();
                obrazek.setImageDrawable(getResources().getDrawable(R.drawable.custom_dish_03));
                Drawable d2 = obrazek.getDrawable();

                Bitmap d1bmp = ((BitmapDrawable)d1).getBitmap();
                Bitmap d2bmp = ((BitmapDrawable)d2).getBitmap();

                if(!d1bmp.sameAs(d2bmp))
                    uPrzepis.setImage(getBytesFromBitmap(d1));
                else
                    uPrzepis.setImage(null);


                viewModel.deleteProdukty(idPrzepisu);
                viewModel.updatePrzepis(getActivity(), uPrzepis);

                for(int i = 0; i < produktyDoDodania.size(); i++){
                    viewModel.insertProduktPrzepisByName(produktyDoDodania.get(i).getPrzepisName(),produktyDoDodania.get(i).getProduktName(),
                            produktyDoDodania.get(i).getIlosc(), produktyDoDodania.get(i).isOpcjonalny());
                }


                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
                manager.popBackStack();
                manager.popBackStack();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new RecipeFragment());
                fragmentTransaction.commit();
            }
        });

        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());

                builder.setMessage("Chcesz usunac przepis?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Przepis dPrzepis = new Przepis();
                                dPrzepis.setId(idPrzepisu);

                                viewModel.deletePrzepis(dPrzepis);

                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                manager.popBackStack();
                                manager.popBackStack();
                                manager.popBackStack();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.main_frame, new RecipeFragment());
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Anuluj",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        zmienZdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        usunZdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());

                builder.setMessage("Chcesz usunac zdjęcie przepisu?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Przepis dPrzepis = new Przepis();
                                dPrzepis.setId(idPrzepisu);
                                dPrzepis.setNazwa(nazwa.getText().toString());
                                dPrzepis.setCzas(Integer.parseInt(czas.getText().toString()));
                                dPrzepis.setOpis(opis.getText().toString());
                                dPrzepis.setImage(null);

                                viewModel.updatePrzepis(getActivity(), dPrzepis);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(RecipeEditFragment.this).attach(RecipeEditFragment.this).commit();
                            }
                        })
                        .setNegativeButton("Anuluj",null);
                AlertDialog alert = builder.create();
                alert.show();

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
            obrazek.setImageURI(imageUri);
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

    private byte[] getBytesFromBitmap(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}

