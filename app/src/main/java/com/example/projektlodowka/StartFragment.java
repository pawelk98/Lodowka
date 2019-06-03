package com.example.projektlodowka;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projektlodowka.database.ViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    ImageView obrazek;
    TextView nazwa;
    ViewModel viewModel;
    TextView doNotDelete;
    int id;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        obrazek = view.findViewById(R.id.image_in_start);
        nazwa = view.findViewById(R.id.recipe_name_in_start);
        doNotDelete = view.findViewById(R.id.doNotDelete);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.setStartPrzepis(getActivity());

        obrazek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doNotDelete.getText().toString().length() != 0) {

                    id = Integer.parseInt(doNotDelete.getText().toString());

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    RecipeShowFragment fragment = new RecipeShowFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, fragment);
                    fragmentTransaction.addToBackStack("przepisyRecycler").commit();
                }
            }
        });
    }
}
