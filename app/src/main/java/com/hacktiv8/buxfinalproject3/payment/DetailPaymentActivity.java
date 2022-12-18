package com.hacktiv8.buxfinalproject3.payment;

import static com.hacktiv8.buxfinalproject3.payment.BankTransferVerificationActivity.EXTRA_TOTAL;
import static com.hacktiv8.buxfinalproject3.payment.BankTransferVerificationActivity.EXTRA_TO_TGL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hacktiv8.buxfinalproject3.databinding.ActivityDetailPaymentBinding;
import com.hacktiv8.buxfinalproject3.model.DataUser;
import com.hacktiv8.buxfinalproject3.model.Seats;
import com.hacktiv8.buxfinalproject3.model.Trip;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailPaymentActivity extends AppCompatActivity {

    private ActivityDetailPaymentBinding binding;
    private FirebaseFirestore db;
    private Seats seats;
    private Trip trip;
    private String total;
    private String tripId, platBus, bookedSeat, userId, toTgl;
    public static final String EXTRA_TRIP_ID = "extra_tripid";
    public static final String EXTRA_BUS_NO = "extra_busno";
    public static final String EXTRA_BOOKED_SEAT = "extra_booked_seat";
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        tripId  = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);
        userId = "088228659668";


        if (tripId==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (platBus==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (bookedSeat==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }

//        getSeatsData(platBus);
        getUserData(userId);
        getTrip(tripId);

        binding.seatNo.setText(bookedSeat);


        binding.btnPayment.setOnClickListener(v ->{
            startActivity(new Intent(this, PaymentMethod.class)
                    .putExtra(PaymentMethod.EXTRA_TRIP_ID, tripId)
                    .putExtra(PaymentMethod.EXTRA_BUS_NO, platBus)
                    .putExtra(PaymentMethod.EXTRA_BOOKED_SEAT, bookedSeat)
                    .putExtra(EXTRA_TOTAL, total)
                    .putExtra(EXTRA_TO_TGL, toTgl)
            );
        });

    }


    private void getUserData(String userId) {
        db.collection("user").document(userId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        DataUser user = doc.toObject(DataUser.class);
                        binding.name.setText(user.getName());
                        binding.phone.setText(user.getPhoneNumber());

                    }

                });

    }

    private void getTrip(String tripId) {
        db.collection("trip").document(tripId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        trip = doc.toObject(Trip.class);
                        textTrip(trip);

                    }
                });


    }

    private void textTrip(Trip trip){
        binding.nameBus.setText(trip.getBusName());
//        binding.platBus.setText(trip.getPlatBus());
        binding.departTime.setText(trip.getDepartHour());
        String[] deparTime = null;
        String[] arrivalTime = null;
        String[] date = null;
        String tanggal = "20 Des 2022";
        binding.fromTgl.setText(tanggal);
        date = tanggal.split(" ");
        deparTime   = trip.getDepartHour().split(":");
        arrivalTime = trip.getArrivalHour().split(":");
        String sdate = date[0];
        String sdJam = deparTime[0];
        String saJam = arrivalTime[0];
        Integer sJam = Integer.parseInt(saJam);
        Integer djam = Integer.parseInt(sdJam);
        Integer iDate = Integer.parseInt(sdate);
        if(sJam < djam){
            iDate +=1;
            date[0] = String.valueOf(iDate);
            toTgl = (date[0] + " " + date[1] + " " + date[2]);
            binding.toTgl.setText(toTgl);
        }else {
            toTgl = tanggal;
            binding.toTgl.setText(toTgl);
        }

        binding.arrivalTime.setText(trip.getArrivalHour());
        binding.from.setText(trip.getDepartCity());
        binding.fromTerminal.setText("Terminal " +trip.getDepartTerminal());
        binding.to.setText(trip.getArrivalCity());
        binding.toTerminal.setText("Terminal "+trip.getArrivalTerminal());
        binding.estimasiTime.setText(trip.getEtaJam());
        String jumSeats = "1";
        int price = Integer.parseInt(trip.getPrice());
        int seats = Integer.parseInt(jumSeats);
        double dTotal = price * seats;
        total = String.valueOf(dTotal);
        binding.tiket.setText(jumSeats +" x Rp"+ trip.getPrice());
        binding.seats.setText(jumSeats);
        binding.total.setText(formatRupiah.format((double)dTotal));


    }



}