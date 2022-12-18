package com.hacktiv8.buxfinalproject3.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.databinding.ActivityOrderDetailBinding;
import com.hacktiv8.buxfinalproject3.model.Bus;
import com.hacktiv8.buxfinalproject3.model.DataUser;
import com.hacktiv8.buxfinalproject3.model.Order;
import com.hacktiv8.buxfinalproject3.model.Trip;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;

public class OrderDetailActivity extends AppCompatActivity {

    private ActivityOrderDetailBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    public static final String EXTRA_TRIP_ID = "extra_trip_id";
    public static final String EXTRA_BUS_NO = "extra_bus_no";
    public static final String EXTRA_BOOK_NO = "extra_book_no";
    public static final String EXTRA_RATING_STATE = "extra_rating_state";
    private String tripId, platBus, bookNo;
    private boolean isRated;
    private Order order;
    private Trip trip;
    private Bus bus;
    private DataUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        getUserData(auth.getCurrentUser().getUid());

        isRated = getIntent().getBooleanExtra(EXTRA_RATING_STATE, false);
        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookNo = getIntent().getStringExtra(EXTRA_BOOK_NO);

        if (tripId != null) {
            getTripData(tripId);
        }
        if (platBus != null) {
            getBusData(platBus);
        }
        if (!isRated) {
            showRatingDialog();
        }

    }

    private void getUserData(String userId) {

        db.collection("user")
                .whereEqualTo("uid", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            user = documentSnapshot.toObject(DataUser.class);
                            populateUserData(user);
                        }
                        if (bookNo != null) {
                            getOrderData(bookNo);
                        }
                    } else {
                        Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void getOrderData(String orderId) {

        db.collection("user")
                .document(user.getPhoneNumber())
                .collection("order")
                .whereEqualTo("bookNo", orderId)
                .get().addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           order = document.toObject(Order.class);
                           populateOrderData(order);

                       }
                   } else {
                       Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
                   }
                });
    }

    private void getTripData(String tripId) {

        db.collection("trip")
                .whereEqualTo("idTrip", tripId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            trip = document.toObject(Trip.class);
                            populateTripData(trip);
                        }
                    } else {
                        Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getBusData(String platBus) {

        db.collection("bus")
                .whereEqualTo("platno", platBus)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            bus = document.toObject(Bus.class);
                            populateBusData(bus);
                        }
                    } else {
                        Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateUserData(DataUser user) {
        binding.name.setText(user.getName());
        binding.phone.setText(user.getPhoneNumber());
    }

    private void populateOrderData(Order order) {
        binding.idTicket.setText(order.getBookNo());
        binding.seatNo.setText(order.getSeatNo());
        binding.Total.setText("Rp."+order.getTotal());
        binding.status.setText(order.getStatus());
    }

    private void populateTripData(Trip trip) {
        binding.tgl.setText(DateHelper.timestampToLocalDate4(trip.getDate()));
        binding.jam.setText(trip.getDepartHour());
        binding.departTime.setText(trip.getDepartHour());
        binding.estimasiTime.setText(trip.getEtaJam());
        binding.estimationTime.setText(trip.getArrivalHour());
        binding.from.setText(trip.getDepartCity());
        binding.to.setText(trip.getArrivalCity());
        binding.fromTerminal.setText(trip.getDepartTerminal());
        binding.toTerminal.setText(trip.getArrivalTerminal());

    }

    private void populateBusData(Bus bus) {
        binding.nameBus.setText(bus.getBusName());
//        binding.platBus.setText(bus.getPlatno());
    }

    private void showRatingDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating_layout);

        dialog.findViewById(R.id.btnRateTrip).setOnClickListener(v -> {
            updateOrderData();
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void updateOrderData() {
        order.setRated(true);

        db.collection("user")
                .document(user.getPhoneNumber())
                .collection("order")
                .document(bookNo)
                .set(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}