package com.hacktiv8.buxfinalproject3.dataBus;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.adapter.BusAdapter;
import com.hacktiv8.buxfinalproject3.databinding.ActivityBusScheduleBinding;
import com.hacktiv8.buxfinalproject3.fragment.SearchFragmentActivity;
import com.hacktiv8.buxfinalproject3.model.Bus;
import com.hacktiv8.buxfinalproject3.model.DataCities;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusScheduleActivity extends AppCompatActivity {

    private ActivityBusScheduleBinding binding;
    private FirebaseFirestore db;
    private List<Bus> busList = new ArrayList<>();
    private String departure;
    private String arrival;
    private Calendar calendar;
    private String passengers, distance, imgbus;
    private SimpleDateFormat format, date;
    private BusAdapter busAdapter;
    private RecyclerView recyclerView;
    private long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.rvTrip;

        db = FirebaseFirestore.getInstance();

        departure = getIntent().getStringExtra("departure");
        arrival = getIntent().getStringExtra("arrival");
        passengers = getIntent().getStringExtra("passengers");
        calendar =  (Calendar)getIntent().getSerializableExtra("date");
        format = new SimpleDateFormat("EEE, d MMM yyyy");
        date = new SimpleDateFormat("d MMM yyyy");
        timeInMillis = getIntent().getLongExtra("timeInMillis", 0);

        String displayPassengers = "Seat " + passengers;
        imgbus = getIntent().getStringExtra("imgBus");

        busAdapter = new BusAdapter(BusScheduleActivity.this, busList);
        binding.seats.setText(displayPassengers);
        binding.departure.setText(departure);
        binding.arrival.setText(arrival);
        binding.date.setText(DateHelper.timestampToLocalDate3(timeInMillis));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(busAdapter);
        getData();

        busAdapter.setOnItemClickCallback(this::showSelectedBus);

    }

    private void getData() {
        progressBar(true);
        if (departure.equals("Solo") && arrival.equals("Semarang")) {
            db.collection("Solo - Semarang")
                .get()
                .addOnCompleteListener(task -> {
                    busList.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            //adapter
                            Bus bus = new Bus(documentSnapshot.getString("busName"),
                                    documentSnapshot.getString("departCity"),
                                    documentSnapshot.getString("arrivalCity"),
                                    documentSnapshot.getString("price"),
                                    documentSnapshot.getString("departTerminal"),
                                    documentSnapshot.getString("arrivalTerminal"),
                                    documentSnapshot.getString("departHour"),
                                    documentSnapshot.getString("arrivalHour"),
                                    documentSnapshot.getString("waktu"));
//                                    documentSnapshot.getString("date"));
                            busList.add(bus);
                        }
                        busAdapter.notifyDataSetChanged();
                        binding.progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(this, "Data gagal diambil", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(e -> Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show());
        }else if (departure.equals("Surabaya") && arrival.equals("Jakarta")) {
            db.collection("Surabaya-Jakarta")
                    .get()
                    .addOnCompleteListener(task -> {
                        busList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                //adapter
                                Bus bus = new Bus(documentSnapshot.getString("busName"),
                                        documentSnapshot.getString("departCity"),
                                        documentSnapshot.getString("arrivalCity"),
                                        documentSnapshot.getString("price"),
                                        documentSnapshot.getString("departTerminal"),
                                        documentSnapshot.getString("arrivalTerminal"),
                                        documentSnapshot.getString("departHour"),
                                        documentSnapshot.getString("arrivalHour"),
                                        documentSnapshot.getString("waktu"));
//                                    documentSnapshot.getString("date"));
                                busList.add(bus);
                            }
                            busAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(this, "Data gagal diambil", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(e -> Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show());
        }else if (departure.equals("Bandung") && arrival.equals("Jakarta")) {
            db.collection("Bandung-Jakarta")
                    .get()
                    .addOnCompleteListener(task -> {
                        busList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                //adapter
                                Bus bus = new Bus(documentSnapshot.getString("busName"),
                                        documentSnapshot.getString("departCity"),
                                        documentSnapshot.getString("arrivalCity"),
                                        documentSnapshot.getString("price"),
                                        documentSnapshot.getString("departTerminal"),
                                        documentSnapshot.getString("arrivalTerminal"),
                                        documentSnapshot.getString("departHour"),
                                        documentSnapshot.getString("arrivalHour"),
                                        documentSnapshot.getString("waktu"));
//                                    documentSnapshot.getString("date"));
                                busList.add(bus);
                            }
                            busAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(this, "Data gagal diambil", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(e -> Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show());
        }else if (departure.equals("Semarang") && arrival.equals("Jakarta")) {
            db.collection("Semarang-Jakarta")
                    .get()
                    .addOnCompleteListener(task -> {
                        busList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                //adapter
                                Bus bus = new Bus(documentSnapshot.getString("busName"),
                                        documentSnapshot.getString("departCity"),
                                        documentSnapshot.getString("arrivalCity"),
                                        documentSnapshot.getString("price"),
                                        documentSnapshot.getString("departTerminal"),
                                        documentSnapshot.getString("arrivalTerminal"),
                                        documentSnapshot.getString("departHour"),
                                        documentSnapshot.getString("arrivalHour"),
                                        documentSnapshot.getString("waktu"));
//                                    documentSnapshot.getString("date"));
                                busList.add(bus);
                            }
                            busAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(this, "Data gagal diambil", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(e -> Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show());
        }else if (departure.equals("Semarang") && arrival.equals("Solo")) {
            db.collection("Semarang-Solo")
                    .get()
                    .addOnCompleteListener(task -> {
                        busList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                //adapter
                                Bus bus = new Bus(documentSnapshot.getString("busName"),
                                        documentSnapshot.getString("departCity"),
                                        documentSnapshot.getString("arrivalCity"),
                                        documentSnapshot.getString("price"),
                                        documentSnapshot.getString("departTerminal"),
                                        documentSnapshot.getString("arrivalTerminal"),
                                        documentSnapshot.getString("departHour"),
                                        documentSnapshot.getString("arrivalHour"),
                                        documentSnapshot.getString("waktu"));
//                                    documentSnapshot.getString("date"));
                                busList.add(bus);
                            }
                            busAdapter.notifyDataSetChanged();
                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(this, "Data gagal diambil", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(e -> Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(this, "Maat tidak ada bus yang tersedia", Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void progressBar(boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void emptyState(boolean state) {
        if (state) {
            binding.emptyState.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.emptyState.getRoot().setVisibility(View.GONE);
        }
    }

    private void showSelectedBus(Bus bus) {
        Intent detail = new Intent(BusScheduleActivity.this, BusDetailActivity.class);
        detail.putExtra("nameBus", bus.getBusName());
        detail.putExtra("departure", bus.getAsal());
        detail.putExtra("arrival", bus.getTujuan());
        detail.putExtra("price", bus.getHarga());
        detail.putExtra("waktu", bus.getWaktu());
        detail.putExtra("departTerminal", bus.getDepartTerminal());
        detail.putExtra("arrivalTerminal", bus.getArrivalTerminal());
        detail.putExtra("departureHour", bus.getTimeDeparture());
        detail.putExtra("arrivalHour", bus.getTimeArrival());
        detail.putExtra("passengers", passengers);
        detail.putExtra("departure", binding.departure.getText().toString());
        detail.putExtra("arrival", binding.arrival.getText().toString());
        detail.putExtra("date", date);
        detail.putExtra("timeInMillis", timeInMillis);
        detail.putExtra("distance", distance);
        detail.putExtra("imgbus", imgbus);
        startActivity(detail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BusScheduleActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
