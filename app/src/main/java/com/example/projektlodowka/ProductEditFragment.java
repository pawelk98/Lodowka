package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.projektlodowka.database.Produkt;
import com.example.projektlodowka.database.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    int id;
    EditText nazwa;
    EditText ilosc;
    int type;
    Button edytuj;
    Button usunObrazek;
    ViewModel viewModel;
    Produkt uProdukt;
    CircleImageView circleImageView;
    Button setImage;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    public byte [] obrazBajty;
    Button dodajBtn;
    EditText dodajEdit;
    TextView dodajText;

    public ProductEditFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_edit, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Spinner typ = view.findViewById(R.id.spinner_prod_edit);
        ArrayAdapter<CharSequence> adapter_prod = ArrayAdapter.createFromResource(this.getActivity() ,R.array.types ,android.R.layout.simple_spinner_item);
        adapter_prod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typ.setAdapter(adapter_prod);
        typ.setOnItemSelectedListener(this);

        nazwa = view.findViewById(R.id.produktEditNazwaEditText);
        ilosc = view.findViewById(R.id.produktEditIloscEditText);
        edytuj = view.findViewById(R.id.produktEditButton);
        setImage = view.findViewById(R.id.changeImage);
        circleImageView = view.findViewById(R.id.productImage);
        dodajBtn = view.findViewById(R.id.QuickAddButton);
        dodajEdit = view.findViewById(R.id.QuickAddEditText);
        dodajText = view.findViewById(R.id.QuickAddTextView);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setEditProdukt(getActivity(), id);
        usunObrazek = view.findViewById(R.id.deleteImage);

        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        dodajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dodajEdit.getText().toString().trim().length() != 0) {
                    float iloscPosiadana = Float.parseFloat(ilosc.getText().toString());
                    float iloscDoDodania = Float.parseFloat(dodajEdit.getText().toString()) + iloscPosiadana;

                    ilosc.setText(String.valueOf(iloscDoDodania));
                }
            }
        });

        edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nazwa.getText().toString().trim().length() != 0
                        && ilosc.getText().toString().trim().length() != 0){

                    switch (type) {
                        case 0:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                    (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                            break;
                        case 1:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                    (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                            break;
                        case 2:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                    (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                            break;
                        case 3:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type-3,
                                    (int)(Float.parseFloat(ilosc.getText().toString())));
                            break;
                        case 4:
                            uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type-3,
                                    (int)(Float.parseFloat(ilosc.getText().toString())));
                            break;
                    }

                    uProdukt.setId(id);
                    uProdukt.setImage(getBytesFromBitmap(circleImageView.getDrawable()));
                    viewModel.updateProdukt(getActivity(), uProdukt);

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.popBackStack();
                    manager.popBackStack();
                    manager.popBackStack();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new ProductFragment());
                    fragmentTransaction.commit();
                }
            }
        });

        usunObrazek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 0:
                        uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                        break;
                    case 1:
                        uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                        break;
                    case 2:
                        uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type,
                                (int)(Float.parseFloat(ilosc.getText().toString())*1000));
                        break;
                    case 3:
                        uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type-3,
                                (int)(Float.parseFloat(ilosc.getText().toString())));
                        break;
                    case 4:
                        uProdukt = new Produkt(nazwa.getText().toString().trim().toLowerCase(), type-3,
                                (int)(Float.parseFloat(ilosc.getText().toString())));
                        break;
                }

                uProdukt.setId(id);
                uProdukt.setImage(null);


                viewModel.updateProdukt(getActivity(),uProdukt);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(ProductEditFragment.this).attach(ProductEditFragment.this).commit();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private byte[] getBytesFromBitmap(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
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
            circleImageView.setImageURI(imageUri);
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