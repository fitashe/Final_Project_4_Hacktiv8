package com.hacktiv8.buxfinalproject3.fragment;

import static android.app.Activity.RESULT_OK;

import static com.hacktiv8.buxfinalproject3.databinding.FragmentSearchBinding.inflate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.chooser.DatePickerActivity;
import com.hacktiv8.buxfinalproject3.chooser.DestinationChooserActivity;
import com.hacktiv8.buxfinalproject3.dataBus.BusScheduleActivity;
import com.hacktiv8.buxfinalproject3.databinding.FragmentSearchBinding;
import com.hacktiv8.buxfinalproject3.model.DataCities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchFragmentActivity extends Fragment implements View.OnClickListener {
    private FragmentSearchBinding binding;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private DataCities departureCity;
    private DataCities arrivalCity;
    FirebaseFirestore db;
    private int passenger;
    private int lastPassenger;
    private long timeInMillis;
    double jarak;
    String linkBus = "";

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormat = new SimpleDateFormat(" dd MMM");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = inflate(inflater, container, false);

//        binding.tvDeparture.setOnClickListener(this);
//        binding.tvArrival.setOnClickListener(this);
        binding.layoutPassengers.setOnClickListener(this);
        binding.layoutDate.setOnClickListener(this);
        binding.btnSearchBus.setOnClickListener(this);

        if(savedInstanceState != null){
            onStateData(savedInstanceState);
        }

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding.btnSearchBus.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterAsal = ArrayAdapter.createFromResource(getActivity(), R.array.Asal, android.R.layout.simple_spinner_item);
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.tvDeparture.setAdapter(adapterAsal);

        ArrayAdapter<CharSequence> adapterTujuan = ArrayAdapter.createFromResource(getActivity(), R.array.Tujuan, android.R.layout.simple_spinner_item);
        adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.tvArrival.setAdapter(adapterTujuan);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("departure", departureCity);
        outState.putParcelable("arrival", arrivalCity);
        outState.putSerializable("date", calendar);
        outState.putString("passengers", binding.tvPassengers.getText().toString());
    }

    private void onStateData(Bundle savedInstanceState) {
        departureCity = savedInstanceState.getParcelable("departure");
        arrivalCity = savedInstanceState.getParcelable("arrival");
        calendar = (Calendar) savedInstanceState.getSerializable("date");
        String passengers = savedInstanceState.getString("passengers");

//        if(departureCity!=null) binding.tvDeparture.setText(departureCity.getCity());
//        if(arrivalCity!=null) binding.tvArrival.setText(arrivalCity.getCity());
        if(calendar!=null) binding.tvDate.setText(dateFormat.format(calendar.getTime()));

        binding.tvPassengers.setText(passengers);
    }

    private void onSearchActivity() {
        if(binding.tvDeparture.getSelectedItem().toString().length() == 0 || binding.tvArrival.getSelectedItem().toString().length() == 0) {
            Toast.makeText(getActivity(), "Complete the data please", Toast.LENGTH_SHORT).show();
        }else if (binding.tvDate == null) {
            Toast.makeText(getActivity(), "Please select your date", Toast.LENGTH_SHORT).show();
        } else if (binding.tvPassengers == null) {
            Toast.makeText(getActivity(), "Please select your passengers", Toast.LENGTH_SHORT).show();
        } else{
            checkPriceAndBus();
            startActivity(new Intent(getContext(), BusScheduleActivity.class)
                    .putExtra("departure", binding.tvDeparture.getSelectedItem().toString())
                    .putExtra("arrival", binding.tvArrival.getSelectedItem().toString())
                    .putExtra("date", calendar)
                    .putExtra("timeInMillis", timeInMillis)
                    .putExtra("passengers", binding.tvPassengers.getText().toString().trim())
                    .putExtra("jarak", String.valueOf(jarak))
                    .putExtra("imgBus", String.valueOf(linkBus))
            );
        }
    }

    private void checkPriceAndBus() {
        if (binding.tvDeparture.getSelectedItem().toString().equals("Semarang") && binding.tvArrival.getSelectedItem().toString().equals("Solo") || binding.tvDeparture.getSelectedItem().toString().equals("Solo") && binding.tvArrival.getSelectedItem().toString().equals("Semarang")) {
            Toast.makeText(getActivity(), "Sukses", Toast.LENGTH_SHORT).show();
            jarak = 48.7;
            linkBus = "https://firebasestorage.googleapis.com/v0/b/buxfinalproject3.appspot.com/o/bus.jpg?alt=media&token=d77fdfe3-f2e8-4941-85dc-34fe7a53e824";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Semarang") && binding.tvArrival.getSelectedItem().toString().equals("Surabaya") || binding.tvDeparture.getSelectedItem().toString().equals("Surabaya") && binding.tvArrival.getSelectedItem().toString().equals("Semarang")) {
            jarak = 350;
            linkBus = "https://firebasestorage.googleapis.com/v0/b/buxfinalproject3.appspot.com/o/bus.jpg?alt=media&token=d77fdfe3-f2e8-4941-85dc-34fe7a53e824";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Semarang") && binding.tvArrival.getSelectedItem().toString().equals("Bandung") || binding.tvDeparture.getSelectedItem().toString().equals("Bandung") && binding.tvArrival.getSelectedItem().toString().equals("Semarang")) {
            jarak = 438;
            linkBus = "";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Semarang") && binding.tvArrival.getSelectedItem().toString().equals("Jakarta") || binding.tvDeparture.getSelectedItem().toString().equals("Jakarta") && binding.tvArrival.getSelectedItem().toString().equals("Semarang")) {
            jarak = 442;
            linkBus = "";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Solo") && binding.tvArrival.getSelectedItem().toString().equals("Surabaya") || binding.tvDeparture.getSelectedItem().toString().equals("Surabaya") && binding.tvArrival.getSelectedItem().toString().equals("Solo")) {
            jarak = 262;
            linkBus = "https://jadwalbis.com/images/bus_logo/61547445_688326811623726_5558866464863158272_n.jpg";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Solo") && binding.tvArrival.getSelectedItem().toString().equals("Jakarta") || binding.tvDeparture.getSelectedItem().toString().equals("Jakarta") && binding.tvArrival.getSelectedItem().toString().equals("Solo")) {
            jarak = 531;
            linkBus = "";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Bandung") && binding.tvArrival.getSelectedItem().toString().equals("Jakarta") || binding.tvDeparture.getSelectedItem().toString().equals("Jakarta") && binding.tvArrival.getSelectedItem().toString().equals("Bandung")) {
            jarak = 151;
            linkBus = "https://ik.imagekit.io/tvlk/image/imageResource/2018/12/05/1544004740153-50b7565a2aeb2f73e30f979ac9b93644.jpeg?tr=q-75";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Bandung") && binding.tvArrival.getSelectedItem().toString().equals("Surabaya") || binding.tvDeparture.getSelectedItem().toString().equals("Surabaya") && binding.tvArrival.getSelectedItem().toString().equals("Bandung")) {
            jarak = 780;
            linkBus = "";
        } else if (binding.tvDeparture.getSelectedItem().toString().equals("Surabaya") && binding.tvArrival.getSelectedItem().toString().equals("Jakarta") || binding.tvDeparture.getSelectedItem().toString().equals("Jakarta") && binding.tvArrival.getSelectedItem().toString().equals("Surabaya")) {
            jarak = 781;
            linkBus = "";
        }
    }

    private void showSheetSlider() {

        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_slider_passenger);

        TextView totalSeat = dialog.findViewById(R.id.tvNumberSlider);

        Slider slider = dialog.findViewById(R.id.sliderPassenger);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                passenger = (int) value;
                lastPassenger = (int) value;
                totalSeat.setText(String.valueOf(passenger));
            }
        });

        dialog.findViewById(R.id.btnCancel).setOnClickListener(v -> {
            passenger = 0;
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btnSelected).setOnClickListener(v -> {
            binding.tvPassengers.setText(String.valueOf(lastPassenger));
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(data != null && resultCode == RESULT_OK){
                    departureCity = data.getParcelableExtra("city");
//                    binding.tvDeparture.setText(departureCity.getCity());
                }
                break;
            case 2:
                if(data != null && resultCode == RESULT_OK){
                    arrivalCity = data.getParcelableExtra("city");
//                    binding.tvArrival.setText(arrivalCity.getCity());
                }
                break;
            case 3:
                if(data != null && resultCode == RESULT_OK){
                    calendar = (Calendar) data.getSerializableExtra("date");
                    timeInMillis = (long) data.getSerializableExtra("timeInMillis");
                    Log.d("timeInMillis", String.valueOf(timeInMillis));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
                    binding.tvDate.setText(simpleDateFormat.format(calendar.getTime()));
                }
                break;
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), DestinationChooserActivity.class);
        switch (view.getId()){
//            case R.id.tvDeparture:
//                intent.putExtra("hint", binding.tvDeparture.getText().toString());
//                startActivityForResult(intent, 1);
//                break;
//            case R.id.tvArrival:
//                intent.putExtra("hint", binding.tvArrival.getText().toString());
//                startActivityForResult(intent, 2);
//                break;
            case R.id.layoutPassengers:
                showSheetSlider();
                break;
            case R.id.layoutDate:
                startActivityForResult(new Intent(getContext(),
                        DatePickerActivity.class), 3);
                break;
            case R.id.btnSearchBus:
                onSearchActivity();
                break;
        }
    }
}
