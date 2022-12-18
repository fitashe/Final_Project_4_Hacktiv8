package com.hacktiv8.buxfinalproject3.payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.databinding.ActivityRetailPaymentVerificationBinding;
import com.hacktiv8.buxfinalproject3.model.DataUser;
import com.hacktiv8.buxfinalproject3.model.Ticket;
import com.hacktiv8.buxfinalproject3.model.Trip;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.NumberFormat;
import java.util.Locale;

public class RetailPaymentVerificationActivity extends AppCompatActivity {

    private ActivityRetailPaymentVerificationBinding binding;
    private String total, tripId, platBus, bookedSeat, toTgl, email, phoneNumber, bookNo, tgl;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DataUser user;
    private Trip trip;
    public static final String EXTRA_TRIP_ID = "extra_tripid";
    public static final String EXTRA_BUS_NO = "extra_busno";
    public static final String EXTRA_BOOKED_SEAT = "extra_booked_seat";
    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_TO_TGL = "extra_to_tgl";
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    private MultiFormatWriter multi = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailPaymentVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        getUserData(email);

        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);
        total = getIntent().getStringExtra(EXTRA_TOTAL);
        toTgl = getIntent().getStringExtra(EXTRA_TO_TGL);
        double dTotal = Double.valueOf(total);
        binding.totalPaymentTv.setText(formatRupiah.format((double)dTotal));

        getTripData(tripId);

        binding.tvPaymentNumber.setOnClickListener(v ->{
            puQrCode();
        });

        binding.btnVerifyPayment.setOnClickListener(v ->{
            String tranfer = "Alfamart";
            String status = "Paid";
            Boolean rate = false;

            Ticket addOrder = new Ticket();
            addOrder.setBookNo(bookNo);
            addOrder.setIdTrip(tripId);
//            addOrder.setPlatno(platBus);
            addOrder.setSeatNo(bookedSeat);
            addOrder.setTotal(total);
            addOrder.setToTgl(toTgl);
            addOrder.setTransaksi(tranfer);
            addOrder.setStatus(status);
            addOrder.setRated(rate);


            order(phoneNumber, bookNo, addOrder);
        });

    }

    void puQrCode(){
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.form_qrcode, null);

        ImageView imgQRCode = (ImageView) view.findViewById(R.id.imgQrCode);
        TextView back = (TextView) view.findViewById(R.id.back);

        Bitmap bitmap ;
        String code = binding.tvPaymentNumber.getText().toString();
        try {
            BitMatrix bitMatrix = multi.encode(code, BarcodeFormat.AZTEC, 320, 320);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQRCode.setImageBitmap(bitmap);

        }catch (WriterException e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }


        popupBuilder.setView(view);

        AlertDialog popupForm = popupBuilder.create();
        popupForm.show();

        back.setOnClickListener(v ->{
            popupForm.dismiss();
        });


    }

    private void getUserData(String userId) {
        db.collection("user").whereEqualTo("email", userId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            user = documentSnapshot.toObject(DataUser.class);
                            getPhoneNumber(user);
                        }
                    }

                });

    }

    private void getPhoneNumber(DataUser user){
        phoneNumber = user.getPhoneNumber();
    }

    private void getTripData(String tripId) {
        db.collection("trip").whereEqualTo("idTrip", tripId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            trip = documentSnapshot.toObject(Trip.class);
                            getBookNo(trip);
                        }
                    }

                });

    }

    private void getBookNo(Trip trip){
        tgl = DateHelper.timestampToBookNo(trip.getDate());
        String strNew = tripId.replaceAll("([a-z])","");
        if(strNew.length() == 1){
            strNew = "0" +strNew;
        }
        bookNo = tgl + "-"+ strNew + bookedSeat;

    }

    private void order(String email, String bookNo, Ticket ticket){
        db.collection("user")
                .document(email)
                .collection("order")
                .document(bookNo)
                .set(ticket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(RetailPaymentVerificationActivity.this, HomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        RetailPaymentVerificationActivity.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}