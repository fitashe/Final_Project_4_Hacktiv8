package com.hacktiv8.buxfinalproject3.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.buxfinalproject3.adapter.TicketAdapter;
import com.hacktiv8.buxfinalproject3.databinding.FragmentTicketBinding;
import com.hacktiv8.buxfinalproject3.model.Bus;
import com.hacktiv8.buxfinalproject3.model.DataUser;
import com.hacktiv8.buxfinalproject3.model.MyTiket;
import com.hacktiv8.buxfinalproject3.model.Order;

import java.util.ArrayList;
import java.util.List;

public class TicketFragment extends Fragment {

    private FragmentTicketBinding binding;
    private List<MyTiket> tiketList;
    private TicketAdapter ticketAdapter;
    private RecyclerView rvTicket;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTicket = binding.rvTicket;

        rvTicket.setHasFixedSize(true);
        rvTicket.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = FirebaseFirestore.getInstance();
        tiketList = new ArrayList<MyTiket>();
        ticketAdapter = new TicketAdapter(requireActivity(), tiketList);

        rvTicket.setAdapter(ticketAdapter);

        getTiketData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void getTiketData() {
        db.collection("Booking")
                .addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore Error", error.getMessage());
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    tiketList.add(dc.getDocument().toObject(MyTiket.class));
                }
                ticketAdapter.notifyDataSetChanged();
            }

        });
    }

}