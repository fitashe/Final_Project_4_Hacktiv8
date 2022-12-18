package com.hacktiv8.buxfinalproject3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.hacktiv8.buxfinalproject3.databinding.ItemTiketBinding;
import com.hacktiv8.buxfinalproject3.fragment.OrderDetailActivity;
import com.hacktiv8.buxfinalproject3.model.Bus;
import com.hacktiv8.buxfinalproject3.model.MyTiket;
import com.hacktiv8.buxfinalproject3.model.Order;
import com.hacktiv8.buxfinalproject3.model.Trip;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private Context context;
    private List<MyTiket> tiketList;

    public TicketAdapter(Context context, List<MyTiket> tiketList) {
        this.context = context;
        this.tiketList = tiketList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTiketBinding binding = ItemTiketBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTiket myTiket = tiketList.get(position);
        if (myTiket != null){
            holder.bind(myTiket);
        }
    }

    @Override
    public int getItemCount() {
        return tiketList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemTiketBinding binding;

        public MyViewHolder(ItemTiketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MyTiket myTiket) {
            binding.tvNameBus.setText(myTiket.getNameBus());
            binding.tvTanggal.setText(myTiket.getDate());
            binding.tvAsal.setText(myTiket.getAsal());
            binding.tvTujuan.setText(myTiket.getTujuan());
            binding.tvJumlahPenumpang.setText(myTiket.getPassenger());
            binding.tvHarga.setText(myTiket.getTotalPrice());

        }
    }


}
