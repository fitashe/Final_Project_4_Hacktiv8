package com.hacktiv8.buxfinalproject3.dataBus;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.databinding.ActivityBusDetailBinding;
import com.hacktiv8.buxfinalproject3.loginregister.LoginUserActivity;
import com.hacktiv8.buxfinalproject3.loginregister.RegisterUserActivity;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;
import com.hacktiv8.buxfinalproject3.utils.Preferences;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BusDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBusDetailBinding binding;
    private FirebaseFirestore db;
    int price;
    int passengers;
    int totalprice;
    private Preferences preferences;
    String price1;
    String nameBus;
    String passenger;
    String departTerminal;
    String arrivalTerminal;
    String asal;
    String tujuan;
    String timeDeparture;
    String timeArrival;
    String waktu;
    String date;
    String type;
    String distance;
    String seats;
    String imgbus;
    String iconPayment;
    String methodPayment;
    Calendar calendar;
    long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        preferences = new Preferences(this);

        binding.bookNow.setOnClickListener(this);

        binding.btnSeatChooser.setOnClickListener(view12 -> {
            Intent intent = new Intent(BusDetailActivity.this, SeatChooserActivity.class);
            intent.putExtra("passengers", passenger);
            intent.putExtra("nameBus", nameBus);
            intent.putExtra("departure", asal);
            intent.putExtra("arrival",tujuan);
            intent.putExtra("departTerminal", departTerminal);
            intent.putExtra("arrivalTerminal", arrivalTerminal);
            intent.putExtra("timeDeparture", timeDeparture);
            intent.putExtra("timeArrival", timeArrival);
            intent.putExtra("waktu", waktu);
            intent.putExtra("seat", seats);
            intent.putExtra("type", type);
            intent.putExtra("price", price1);
            intent.putExtra("imgbus", imgbus);
            intent.putExtra("date", calendar);
            intent.putExtra("timeInMillis", timeInMillis);
            startActivity(intent);
        });

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusDetailActivity.this, BusScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        seats = preferences.getSharedPreferences().getString("kodeseat", null);
        if (seats != null) {
            binding.tvSeat.setText(seats);
        }

//        methodPayment = preferences.getSharedPreferences().getString("methodPayment", null);
//        if (methodPayment != null) {
//            binding.tvPayment.setText(methodPayment);
//        }
//
//        iconPayment = preferences.getSharedPreferences().getString("iconPayment", null);
//        if (iconPayment != null) {
//            Glide.with(BusDetailActivity.this).load(iconPayment).error(R.drawable.ic_launcher_background).centerCrop().into(binding.iconPayment);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        preferences.getEditor().clear().apply();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        nameBus = getIntent().getStringExtra("nameBus");
        asal = getIntent().getStringExtra("departure");
        tujuan = getIntent().getStringExtra("arrival");
        departTerminal = getIntent().getStringExtra("departTerminal");
        arrivalTerminal = getIntent().getStringExtra("arrivalTerminal");
        timeDeparture = getIntent().getStringExtra("departureHour");
        timeArrival = getIntent().getStringExtra("arrivalHour");
        waktu = getIntent().getStringExtra("waktu");
        timeInMillis = getIntent().getLongExtra("timeInMillis", 0);
//        date = getIntent().getStringExtra("date");
        type = getIntent().getStringExtra("type");
        imgbus = getIntent().getStringExtra("imgbus");
        distance = getIntent().getStringExtra("distance");
        passenger = getIntent().getStringExtra("passengers");
        price1 = getIntent().getStringExtra("price");

        passengers = Integer.parseInt(passenger);
        price = Integer.parseInt(price1);
        totalprice = price * passengers;

        getImageBus();
        binding.nameBusDetail.setText(nameBus);
        binding.fromDetail.setText(asal);
        binding.toDetail.setText(tujuan);
        binding.pickUpDetail.setText(departTerminal);
        binding.dropOffDetail.setText(arrivalTerminal);
        binding.timeStartDetail.setText(timeDeparture);
        binding.timeEndDetail.setText(timeArrival);
        binding.longTimeDetail.setText(waktu);
        binding.dateDetail.setText(DateHelper.timestampToLocalDate3(timeInMillis));
//        binding.tvClass.setText(type);
        binding.totalPrice.setText(getPrice(totalprice));
        binding.pessengersDetail.setText(passenger + " Passengers");


    }

    private void getImageBus() {
        Glide.with(BusDetailActivity.this).load(imgbus).error(R.drawable.ic_launcher_background).centerCrop().into(binding.imgBus);
    }

    private String getPrice(double price) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(price);
    }


    @Override
    public void onClick(View view) {

//        if (binding.tvPayment.getText().toString().isEmpty()) {
//            Toast.makeText(this, "Choose Payment Method", Toast.LENGTH_SHORT).show();
//        } else
        if (binding.tvSeat.getText().toString().isEmpty()) {
            Toast.makeText(this, "Choose Seats", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> detail = new HashMap<>();
            detail.put("nameBus", nameBus);
            detail.put("asal", asal);
            detail.put("tujuan", tujuan);
            detail.put("passenger", String.valueOf(passenger));
            detail.put("departTerminal", departTerminal);
            detail.put("arrivalTerminal", arrivalTerminal);
            detail.put("departureHour", timeDeparture);
            detail.put("arrivalHour", timeArrival);
            detail.put("waktu", waktu);
            detail.put("timeInMillis", String.valueOf(timeInMillis));
            detail.put("type", type);
            detail.put("distance", distance);
            detail.put("price", String.valueOf(price));
            detail.put("totalPrice", String.valueOf(totalprice));
            detail.put("kodeSeat", seats);
            detail.put("methodPayment", methodPayment);


            // Add a new document with a generated ID
            db.collection("Booking")
                    .add(detail)
                    .addOnSuccessListener(documentReference -> {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                Toast.makeText(BusDetailActivity.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusDetailActivity.this, HomePageActivity.class);
                intent.putExtra("nameBus", binding.nameBusDetail.getText().toString());
                intent.putExtra("asal", binding.fromDetail.getText().toString());
                intent.putExtra("tujuan", binding.toDetail.getText().toString());
                intent.putExtra("seat", binding.tvSeat.getText().toString());
                intent.putExtra("passenger", binding.pessengersDetail.getText().toString());
//                intent.putExtra("ticket", binding.tvClass.getText().toString());
                intent.putExtra("totalPrice", binding.totalPrice.getText().toString());
//                intent.putExtra("timeInMillis", binding.dateDetail.getText().toString());
                intent.putExtra("date", binding.dateDetail.getText().toString());
                intent.putExtra("departureHour", binding.timeStartDetail.getText().toString());
                intent.putExtra("arrivalHour", binding.timeEndDetail.getText().toString());
                startActivity(intent);
                preferences.getEditor().clear().apply();
            }).addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}