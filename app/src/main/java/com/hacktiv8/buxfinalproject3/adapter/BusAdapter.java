package com.hacktiv8.buxfinalproject3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.buxfinalproject3.dataBus.BusDetailActivity;
import com.hacktiv8.buxfinalproject3.dataBus.SeatChooserActivity;
import com.hacktiv8.buxfinalproject3.databinding.ItemTripTicketLayoutBinding;
import com.hacktiv8.buxfinalproject3.model.Bus;
import com.hacktiv8.buxfinalproject3.utils.DateHelper;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ListViewHolder> {
    private Context context;
    private List<Bus> list;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public BusAdapter(Context context, List<Bus> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BusAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripTicketLayoutBinding binding = ItemTripTicketLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BusAdapter.ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ListViewHolder holder, int position) {
        Bus bus = list.get(position);
        if(bus!= null){
            holder.bind(bus);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private final ItemTripTicketLayoutBinding binding;

        public ListViewHolder(ItemTripTicketLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bus bus) {
            binding.tvBusName.setText(bus.getBusName());
            binding.tvDepartHour.setText(bus.getTimeDeparture());
            binding.tvDepartCity.setText(bus.getAsal());
            binding.tvArriveCity.setText(bus.getTujuan());
            binding.tvPrice.setText("Rp "+ bus.getHarga());
            binding.tvDepartStation.setText("Terminal "+ bus.getDepartTerminal());
            binding.tvArriveStation.setText("Terminal "+ bus.getArrivalTerminal());
            binding.tvArriveHour.setText(bus.getTimeArrival());
            binding.tvDepartDate.setText(bus.getWaktu());
//            binding.tvRating.setText(trip.getRating() + "/5");


            itemView.setOnClickListener(v -> {
                onItemClickCallback.onItemClicked(list.get(getAdapterPosition()));
            });

            binding.btnBookNow.setOnClickListener(v -> {
//                Intent intent = new Intent(context, BusDetailActivity.class);
//                itemView.getContext().startActivity(intent);
                onItemClickCallback.onItemClicked(list.get(getAdapterPosition()));
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Bus bus);
    }
}
