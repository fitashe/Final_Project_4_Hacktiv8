package com.hacktiv8.buxfinalproject3.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.R;
import com.hacktiv8.buxfinalproject3.databinding.FragmentProfileBinding;
import com.hacktiv8.buxfinalproject3.loginregister.LoginUserActivity;
import com.hacktiv8.buxfinalproject3.loginregister.SignInGoogleUser;
import com.hacktiv8.buxfinalproject3.model.DataUser;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private FirebaseDatabase getDatabase;
    private DatabaseReference getReferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (googleSignInAccount != null) {
            binding.nameProfile.setText(googleSignInAccount.getDisplayName());
            binding.emailProfile.setText(googleSignInAccount.getEmail());
        }


        getDatabase = FirebaseDatabase.getInstance();
        getReferences = getDatabase.getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            getReferences.child("User");
            getReferences.child(currentUser.getUid());
            getReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.emailProfile.setText(currentUser.getEmail());
                    binding.nameProfile.setText(currentUser.getDisplayName());
                    binding.nameProfile.setText(currentUser.getPhoneNumber());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        binding.btnSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        startActivity(new Intent(getActivity(), SignInGoogleUser.class));
                        requireActivity().finish();
                    }
                });

                // Auth
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), SignInGoogleUser.class));
                requireActivity().finish();

            }
        });
    }

}
