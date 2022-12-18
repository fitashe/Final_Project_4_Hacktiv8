package com.hacktiv8.buxfinalproject3.dataBus;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static com.google.android.material.snackbar.Snackbar.make;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.databinding.ActivitySeatChooserBinding;
import com.hacktiv8.buxfinalproject3.model.Buses;
import com.hacktiv8.buxfinalproject3.model.ScheduleReference;
import com.hacktiv8.buxfinalproject3.model.Seats;
import com.hacktiv8.buxfinalproject3.payment.DetailPaymentActivity;
import com.hacktiv8.buxfinalproject3.utils.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatChooserActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivitySeatChooserBinding binding;
    private FirebaseFirestore db;

    private Preferences preferences;
    int totalClick2 = 0;
    String price, nameBus, passenger, asal, tujuan, departTerminal, arrivalTerminal, timeDeparture, timeArrival, waktu, date, type, distance, seats, imgbus, iconPayment, methodPayment;
    private long timeInMillis;

    int seat, seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20, seat21, seat22, seat23, seat24, seat25, seat26, seat27, seat28, seat29, seat30, seat31;

    String kode_seat = "", kdseat1 = "", kdseat2 = "", kdseat3 = "", kdseat4 = "", kdseat5 = "", kdseat6 = "", kdseat7 = "", kdseat8 = "", kdseat9 = "", kdseat10 = "", kdseat11 = "", kdseat12 = "", kdseat13 = "", kdseat14 = "", kdseat15 = "", kdseat16 = "", kdseat17 = "", kdseat18 = "", kdseat19 = "", kdseat20 = "", kdseat21 = "", kdseat22 = "", kdseat23 = "", kdseat24 = "", kdseat25 = "", kdseat26 = "", kdseat27 = "", kdseat28 = "", kdseat29 = "", kdseat30 = "", kdseat31 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatChooserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setSeatView();

        db = FirebaseFirestore.getInstance();

        preferences = new Preferences(this);

        nameBus = getIntent().getStringExtra("nameBus");
        asal = getIntent().getStringExtra("departure");
        tujuan = getIntent().getStringExtra("arrival");
        departTerminal = getIntent().getStringExtra("departTerminal");
        arrivalTerminal = getIntent().getStringExtra("arrivalTerminal");
        timeDeparture = getIntent().getStringExtra("departureHour");
        timeArrival = getIntent().getStringExtra("arrivalHour");
        waktu = getIntent().getStringExtra("waktu");
        timeInMillis = getIntent().getLongExtra("timeInMillis", 0);
        type = getIntent().getStringExtra("type");
        imgbus = getIntent().getStringExtra("imgbus");
        distance = getIntent().getStringExtra("distance");
        passenger = getIntent().getStringExtra("passengers");
        price = getIntent().getStringExtra("price");

        binding.ivSeatA1.setOnClickListener(this);
        binding.ivSeatA2.setOnClickListener(this);
        binding.ivSeatA3.setOnClickListener(this);
        binding.ivSeatA4.setOnClickListener(this);
        binding.ivSeatA5.setOnClickListener(this);
        binding.ivSeatA6.setOnClickListener(this);
        binding.ivSeatA7.setOnClickListener(this);
        binding.ivSeatA8.setOnClickListener(this);
        binding.ivSeatA9.setOnClickListener(this);

        binding.ivSeatB1.setOnClickListener(this);
        binding.ivSeatB2.setOnClickListener(this);
        binding.ivSeatB3.setOnClickListener(this);
        binding.ivSeatB4.setOnClickListener(this);
        binding.ivSeatB5.setOnClickListener(this);
        binding.ivSeatB6.setOnClickListener(this);
        binding.ivSeatB7.setOnClickListener(this);
        binding.ivSeatB8.setOnClickListener(this);
        binding.ivSeatB9.setOnClickListener(this);
        binding.ivSeatB10.setOnClickListener(this);

        binding.ivSeatC1.setOnClickListener(this);
        binding.ivSeatC2.setOnClickListener(this);
        binding.ivSeatC3.setOnClickListener(this);
        binding.ivSeatC4.setOnClickListener(this);
        binding.ivSeatC5.setOnClickListener(this);
        binding.ivSeatC6.setOnClickListener(this);
        binding.ivSeatC7.setOnClickListener(this);
        binding.ivSeatC8.setOnClickListener(this);
        binding.ivSeatC9.setOnClickListener(this);
        binding.ivSeatC10.setOnClickListener(this);

        binding.ivSeatD1.setOnClickListener(this);
        binding.ivSeatD2.setOnClickListener(this);
        binding.ivSeatD3.setOnClickListener(this);
        binding.ivSeatD4.setOnClickListener(this);
        binding.ivSeatD5.setOnClickListener(this);
        binding.ivSeatD6.setOnClickListener(this);
        binding.ivSeatD7.setOnClickListener(this);
        binding.ivSeatD8.setOnClickListener(this);
        binding.ivSeatD9.setOnClickListener(this);
        binding.ivSeatD10.setOnClickListener(this);

        //getSeats();

        binding.ivBackArrow.setOnClickListener(v -> onBackPressed());
        binding.btnBookNow.setOnClickListener(v-> onStartActivity());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SeatChooserActivity.this, BusDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSeatA1:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA1.setBackgroundResource(R.drawable.bg_seat_off);
                    seat1 = 0;
                    kdseat1 = "";
                } else {
                    binding.ivSeatA1.setBackgroundResource(R.drawable.bg_seat_on);
                    seat1 = 1;
                    kdseat1 = "1A";
                }
                break;
            case R.id.ivSeatB1:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB1.setBackgroundResource(R.drawable.bg_seat_off);
                    seat2 = 0;
                    kdseat2 = "";
                } else {
                    binding.ivSeatB1.setBackgroundResource(R.drawable.bg_seat_on);
                    seat2 = 1;
                    kdseat2 = "1B";
                }
                break;
            case R.id.ivSeatA2:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA2.setBackgroundResource(R.drawable.bg_seat_off);
                    seat3 = 0;
                    kdseat3 = "";
                } else {
                    binding.ivSeatA2.setBackgroundResource(R.drawable.bg_seat_on);
                    seat3 = 1;
                    kdseat3 = "2A";
                }
                break;
            case R.id.ivSeatB2:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB2.setBackgroundResource(R.drawable.bg_seat_off);
                    seat4 = 0;
                    kdseat4 = "";
                } else {
                    binding.ivSeatB2.setBackgroundResource(R.drawable.bg_seat_on);
                    seat4 = 1;
                    kdseat4 = "2B";
                }
                break;
            case R.id.ivSeatA3:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA3.setBackgroundResource(R.drawable.bg_seat_off);
                    seat5 = 0;
                    kdseat5 = "";
                } else {
                    binding.ivSeatA3.setBackgroundResource(R.drawable.bg_seat_on);
                    seat5 = 1;
                    kdseat5 = "3A";
                }
                break;
            case R.id.ivSeatB3:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB3.setBackgroundResource(R.drawable.bg_seat_off);
                    seat6 = 0;
                    kdseat6 = "";
                } else {
                    binding.ivSeatB3.setBackgroundResource(R.drawable.bg_seat_on);
                    seat6 = 1;
                    kdseat6 = "3B";
                }
                break;
            case R.id.ivSeatA4:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA4.setBackgroundResource(R.drawable.bg_seat_off);
                    seat7 = 0;
                    kdseat7 = "";
                } else {
                    binding.ivSeatA4.setBackgroundResource(R.drawable.bg_seat_on);
                    seat7 = 1;
                    kdseat7 = "4A";
                }
                break;
            case R.id.ivSeatB4:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB4.setBackgroundResource(R.drawable.bg_seat_off);
                    seat8 = 0;
                    kdseat8 = "";
                } else {
                    binding.ivSeatB4.setBackgroundResource(R.drawable.bg_seat_on);
                    seat8 = 1;
                    kdseat8 = "4B";
                }
                break;
            case R.id.ivSeatA5:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA5.setBackgroundResource(R.drawable.bg_seat_off);
                    seat9 = 0;
                    kdseat9 = "";
                } else {
                    binding.ivSeatA5.setBackgroundResource(R.drawable.bg_seat_on);
                    seat9 = 1;
                    kdseat9 = "5A";
                }
                break;
            case R.id.ivSeatB5:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB5.setBackgroundResource(R.drawable.bg_seat_off);
                    seat10 = 0;
                    kdseat10 = "";
                } else {
                    binding.ivSeatB5.setBackgroundResource(R.drawable.bg_seat_on);
                    seat10 = 1;
                    kdseat10 = "5B";
                }
                break;
            case R.id.ivSeatA6:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA6.setBackgroundResource(R.drawable.bg_seat_off);
                    seat11 = 0;
                    kdseat11 = "";
                } else {
                    binding.ivSeatA6.setBackgroundResource(R.drawable.bg_seat_on);
                    seat11 = 1;
                    kdseat11 = "6A";
                }
                break;
            case R.id.ivSeatB6:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB6.setBackgroundResource(R.drawable.bg_seat_off);
                    seat12 = 0;
                    kdseat12 = "";
                } else {
                    binding.ivSeatB6.setBackgroundResource(R.drawable.bg_seat_on);
                    seat12 = 1;
                    kdseat12 = "6B";
                }
                break;
            case R.id.ivSeatA7:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA7.setBackgroundResource(R.drawable.bg_seat_off);
                    seat13 = 0;
                    kdseat13 = "";
                } else {
                    binding.ivSeatA7.setBackgroundResource(R.drawable.bg_seat_on);
                    seat13 = 1;
                    kdseat13 = "7A";
                }
                break;
            case R.id.ivSeatB7:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB7.setBackgroundResource(R.drawable.bg_seat_off);
                    seat14 = 0;
                    kdseat14 = "";
                } else {
                    binding.ivSeatB7.setBackgroundResource(R.drawable.bg_seat_on);
                    seat14 = 1;
                    kdseat14 = "7B";
                }
                break;
            case R.id.ivSeatA8:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA8.setBackgroundResource(R.drawable.bg_seat_off);
                    seat15 = 0;
                    kdseat15 = "";
                } else {
                    binding.ivSeatA8.setBackgroundResource(R.drawable.bg_seat_on);
                    seat15 = 1;
                    kdseat15 = "8A";
                }
                break;
            case R.id.ivSeatB8:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB8.setBackgroundResource(R.drawable.bg_seat_off);
                    seat15 = 0;
                    kdseat15 = "";
                } else {
                    binding.ivSeatB8.setBackgroundResource(R.drawable.bg_seat_on);
                    seat15 = 1;
                    kdseat15 = "8B";
                }
                break;
            case R.id.ivSeatA9:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatA9.setBackgroundResource(R.drawable.bg_seat_off);
                    seat15 = 0;
                    kdseat15 = "";
                } else {
                    binding.ivSeatA9.setBackgroundResource(R.drawable.bg_seat_on);
                    seat15 = 1;
                    kdseat15 = "9A";
                }
                break;
            case R.id.ivSeatB9:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatB9.setBackgroundResource(R.drawable.bg_seat_off);
                    seat15 = 0;
                    kdseat15 = "";
                } else {
                    binding.ivSeatB9.setBackgroundResource(R.drawable.bg_seat_on);
                    seat15 = 1;
                    kdseat15 = "8B";
                }
                break;
            case R.id.ivSeatC1:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC1.setBackgroundResource(R.drawable.bg_seat_off);
                    seat16 = 0;
                    kdseat16 = "";
                } else {
                    binding.ivSeatC1.setBackgroundResource(R.drawable.bg_seat_on);
                    seat16 = 1;
                    kdseat16 = "1C";
                }
                break;
            case R.id.ivSeatD1:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD1.setBackgroundResource(R.drawable.bg_seat_off);
                    seat17 = 0;
                    kdseat17 = "";
                } else {
                    binding.ivSeatD1.setBackgroundResource(R.drawable.bg_seat_on);
                    seat17 = 1;
                    kdseat17 = "1D";
                }
                break;
            case R.id.ivSeatC2:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC2.setBackgroundResource(R.drawable.bg_seat_off);
                    seat18 = 0;
                    kdseat18 = "";
                } else {
                    binding.ivSeatC2.setBackgroundResource(R.drawable.bg_seat_on);
                    seat18 = 1;
                    kdseat18 = "2C";
                }
                break;
            case R.id.ivSeatD2:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD2.setBackgroundResource(R.drawable.bg_seat_off);
                    seat19 = 0;
                    kdseat19 = "";
                } else {
                    binding.ivSeatD2.setBackgroundResource(R.drawable.bg_seat_on);
                    seat19 = 1;
                    kdseat19 = "2D";
                }
                break;
            case R.id.ivSeatC3:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC3.setBackgroundResource(R.drawable.bg_seat_off);
                    seat20 = 0;
                    kdseat20 = "";
                } else {
                    binding.ivSeatC3.setBackgroundResource(R.drawable.bg_seat_on);
                    seat20 = 1;
                    kdseat20 = "3C";
                }
                break;
            case R.id.ivSeatD3:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD3.setBackgroundResource(R.drawable.bg_seat_off);
                    seat21 = 0;
                    kdseat21 = "";
                } else {
                    binding.ivSeatD3.setBackgroundResource(R.drawable.bg_seat_on);
                    seat21 = 1;
                    kdseat21 = "3D";
                }
                break;
            case R.id.ivSeatC4:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC4.setBackgroundResource(R.drawable.bg_seat_off);
                    seat22 = 0;
                    kdseat22 = "";
                } else {
                    binding.ivSeatC4.setBackgroundResource(R.drawable.bg_seat_on);
                    seat22 = 1;
                    kdseat22 = "4C";
                }
                break;
            case R.id.ivSeatD4:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD4.setBackgroundResource(R.drawable.bg_seat_off);
                    seat23 = 0;
                    kdseat23 = "";
                } else {
                    binding.ivSeatD4.setBackgroundResource(R.drawable.bg_seat_on);
                    seat23 = 1;
                    kdseat23 = "4D";
                }
                break;
            case R.id.ivSeatC5:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC5.setBackgroundResource(R.drawable.bg_seat_off);
                    seat24 = 0;
                    kdseat24 = "";
                } else {
                    binding.ivSeatC5.setBackgroundResource(R.drawable.bg_seat_on);
                    seat24 = 1;
                    kdseat24 = "5C";
                }
                break;
            case R.id.ivSeatD5:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD5.setBackgroundResource(R.drawable.bg_seat_off);
                    seat25 = 0;
                    kdseat25 = "";
                } else {
                    binding.ivSeatD5.setBackgroundResource(R.drawable.bg_seat_on);
                    seat25 = 1;
                    kdseat25 = "5D";
                }
                break;
            case R.id.ivSeatC6:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC6.setBackgroundResource(R.drawable.bg_seat_off);
                    seat26 = 0;
                    kdseat26 = "";
                } else {
                    binding.ivSeatC6.setBackgroundResource(R.drawable.bg_seat_on);
                    seat26 = 1;
                    kdseat26 = "6C";
                }
                break;
            case R.id.ivSeatD6:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD6.setBackgroundResource(R.drawable.bg_seat_off);
                    seat27 = 0;
                    kdseat27 = "";
                } else {
                    binding.ivSeatD6.setBackgroundResource(R.drawable.bg_seat_on);
                    seat27 = 1;
                    kdseat27 = "6D";
                }
                break;
            case R.id.ivSeatC7:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC7.setBackgroundResource(R.drawable.bg_seat_off);
                    seat28 = 0;
                    kdseat28 = "";
                } else {
                    binding.ivSeatC7.setBackgroundResource(R.drawable.bg_seat_on);
                    seat28 = 1;
                    kdseat28 = "7C";
                }
                break;
            case R.id.ivSeatD7:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD7.setBackgroundResource(R.drawable.bg_seat_off);
                    seat29 = 0;
                    kdseat29 = "";
                } else {
                    binding.ivSeatD7.setBackgroundResource(R.drawable.bg_seat_on);
                    seat29 = 1;
                    kdseat29 = "7D";
                }
                break;
            case R.id.ivSeatC8:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC8.setBackgroundResource(R.drawable.bg_seat_off);
                    seat30 = 0;
                    kdseat30 = "";
                } else {
                    binding.ivSeatC8.setBackgroundResource(R.drawable.bg_seat_on);
                    seat30 = 1;
                    kdseat30 = "8C";
                }
                break;
            case R.id.ivSeatD8:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD8.setBackgroundResource(R.drawable.bg_seat_off);
                    seat2 = 0;
                    kdseat31 = "";
                } else {
                    binding.ivSeatD8.setBackgroundResource(R.drawable.bg_seat_on);
                    seat31 = 1;
                    kdseat31 = "8D";
                }
                break;
            case R.id.ivSeatC9:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatC9.setBackgroundResource(R.drawable.bg_seat_off);
                    seat2 = 0;
                    kdseat31 = "";
                } else {
                    binding.ivSeatC9.setBackgroundResource(R.drawable.bg_seat_on);
                    seat31 = 1;
                    kdseat31 = "9C";
                }
                break;
            case R.id.ivSeatD9:
                totalClick2 += 1;
                if (totalClick2 % 2 == 0) {
                    binding.ivSeatD9.setBackgroundResource(R.drawable.bg_seat_off);
                    seat2 = 0;
                    kdseat31 = "";
                } else {
                    binding.ivSeatD9.setBackgroundResource(R.drawable.bg_seat_on);
                    seat31 = 1;
                    kdseat31 = "9D";
                }
                break;
        }
        seat = seat1 + seat2 + seat3 + seat4 + seat5 + seat6 + seat7 + seat8 + seat9 + seat10 + seat11 + seat12 + seat13 + seat14 + seat15 + seat16 + seat17 + seat18 + seat19 + seat20 + seat21 + seat22 + seat23 + seat24 + seat25 + seat26 + seat27 + seat28 + seat29 + seat30 + seat31;

        kode_seat = kdseat1 + kdseat2 + kdseat3 + kdseat4 + kdseat5 + kdseat6 + kdseat7 + kdseat8 + kdseat9 + kdseat10 + kdseat11 + kdseat12 + kdseat13 + kdseat14 + kdseat15 + kdseat16 + kdseat17 + kdseat18 + kdseat19 + kdseat20 + kdseat21 + kdseat22 + kdseat23 + kdseat24 + kdseat25 + kdseat26 + kdseat27 + kdseat28 + kdseat29 + kdseat30 + kdseat31;

        binding.tvStatus.setText(kode_seat);
    }

    private void onStartActivity() {
        Intent intent = new Intent(SeatChooserActivity.this, BusDetailActivity.class);
        if (seat == Integer.parseInt(passenger)){
            intent.putExtra("passengers", passenger);
            intent.putExtra("nameBus", nameBus);
            intent.putExtra("departure", asal);
            intent.putExtra("arrival", tujuan);
            intent.putExtra("departTerminal", departTerminal);
            intent.putExtra("arrivalTerminal", arrivalTerminal);
            intent.putExtra("timeDeparture", timeDeparture);
            intent.putExtra("timeArrival", timeArrival);
            intent.putExtra("waktu", waktu);
            intent.putExtra("seat", binding.tvStatus.getText().toString());
            intent.putExtra("type", type);
            intent.putExtra("price", price);
            intent.putExtra("timeInMillis", timeInMillis);
            intent.putExtra("imgbus", imgbus);
            preferences.getEditor().putString("kodeseat", binding.tvStatus.getText().toString()).apply();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please choose " + passenger + " seats", Toast.LENGTH_SHORT).show();
        }
    }

}