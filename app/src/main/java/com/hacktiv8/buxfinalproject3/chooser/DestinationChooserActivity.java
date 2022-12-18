package com.hacktiv8.buxfinalproject3.chooser;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.buxfinalproject3.adapter.CitiesAdapter;
import com.hacktiv8.buxfinalproject3.databinding.ActivityDestinationChooserBinding;
import com.hacktiv8.buxfinalproject3.model.DataCities;

import java.util.ArrayList;

public class DestinationChooserActivity extends AppCompatActivity {
    private RecyclerView rvCity;
    private ArrayList<DataCities> list = new ArrayList<>();
    private CitiesAdapter citiesAdapter;
    private ActivityDestinationChooserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDestinationChooserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvCity = binding.rVDestination;
        rvCity.setHasFixedSize(true);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        // add data
        list.add(new DataCities("1","Semarang", "Terminal Terboyo"));
        list.add(new DataCities("5","Solo", "Terminal Tirtonadi"));
        list.add(new DataCities("2","Surabaya", "Terminal Purabaya"));
        list.add(new DataCities("3","Bandung", "Terminal Leuwipanjang"));
        list.add(new DataCities("4","Jakarta", "Terminal Kalideres"));

        data();

        binding.edSearch.setFocusable(true);
        binding.tvCancel.setOnClickListener(v -> onBackPressed());
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = String.valueOf(binding.edSearch.getText());
                filterList(newText);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void filterList(String text) {
        ArrayList<DataCities> filteredList = new ArrayList<>();
        for(DataCities dataCities : list){
            if(dataCities.getCity().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(dataCities);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            citiesAdapter.setFilteredList(DestinationChooserActivity.this, filteredList);
        }
    }

    private void data(){
        citiesAdapter = new CitiesAdapter(DestinationChooserActivity.this, list);
        rvCity.setAdapter(citiesAdapter);

        citiesAdapter.setItemClickListener(new CitiesAdapter.ItemClickListener() {
            @Override
            public void onClick(DataCities dataCities) {
                setResult(RESULT_OK, new Intent()
                        .putExtra("city", dataCities)
                );
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
