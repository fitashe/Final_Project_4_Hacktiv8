package com.hacktiv8.buxfinalproject3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.buxfinalproject3.databinding.ItemDestinationSearchBinding;
import com.hacktiv8.buxfinalproject3.model.DataCities;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyViewHolder> {

    public ItemClickListener itemClickListener;
    private Context context;
    private ArrayList<DataCities> list;

    public CitiesAdapter(Context context, ArrayList<DataCities> list){
        this.context = context;
        this.list = list;
    }

    public void setFilteredList(Context context, ArrayList<DataCities> filteredList){
        this.context = context;
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDestinationSearchBinding binding = ItemDestinationSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.MyViewHolder holder, int position) {
        DataCities dataCities = list.get(position);
        if (dataCities != null){
            holder.bind(dataCities);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemDestinationSearchBinding binding;

        public MyViewHolder(ItemDestinationSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DataCities dataCities){
            binding.tvCity.setText(dataCities.getCity());
            binding.tvTerminal.setText(dataCities.getTerminal());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClick(list.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ItemClickListener{
        void onClick(DataCities dataCities);
    }}
