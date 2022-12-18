package com.hacktiv8.buxfinalproject3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hacktiv8.buxfinalproject3.databinding.ActivityHomePageBinding;
import com.hacktiv8.buxfinalproject3.fragment.ProfileFragment;
import com.hacktiv8.buxfinalproject3.fragment.SearchFragmentActivity;
import com.hacktiv8.buxfinalproject3.fragment.TicketFragment;
import com.hacktiv8.buxfinalproject3.loginregister.LoginUserActivity;

public class HomePageActivity extends AppCompatActivity {
    private ActivityHomePageBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.vNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.search_navigation:
                    fragment = new SearchFragmentActivity();
                    break;
                case R.id.riwayat_navigation:
                    fragment = new TicketFragment();
                    break;
                case R.id.profil_navigation:
                    fragment = new ProfileFragment();
                    break;
            }
            TransisiFragment(fragment);
            return true;
        });

    }

    private void TransisiFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null) {
            startActivity(new Intent(HomePageActivity.this, LoginUserActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}