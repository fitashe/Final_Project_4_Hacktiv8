package com.hacktiv8.buxfinalproject3.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hacktiv8.buxfinalproject3.HomePageActivity;
import com.hacktiv8.buxfinalproject3.databinding.ActivityLoginUserBinding;
import com.hacktiv8.buxfinalproject3.model.DataUser;

public class LoginUserActivity extends AppCompatActivity {
    ActivityLoginUserBinding binding;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirebaseAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccUser();
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUserActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginUserActivity.this, SignInGoogleUser.class);
        startActivity(intent);
    }

    public void loginAccUser(){
        String email = binding.etLoginEmail.getText().toString().trim();
        String pass = binding.etPassword.getText().toString().trim();

        if(email.isEmpty()){
            binding.etLoginEmail.setError("Please enter your email");
            binding.etLoginEmail.requestFocus();
        }else if(pass.isEmpty()){
            binding.etPassword.setError("Please enter your password");
            binding.etPassword.requestFocus();
        }else if(email.isEmpty() && pass.isEmpty()){
            Toast.makeText(LoginUserActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
        }else if(!(email.isEmpty() && pass.isEmpty())){
            mFirebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(LoginUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                String uId = firebaseUser.getUid();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                                databaseReference.child(uId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        boolean isExcist = snapshot.exists();

                                        if (isExcist){
                                            DataUser dataUser = snapshot.getValue(DataUser.class);
                                            String statusUser = dataUser.getRole().toString();

                                            if (statusUser.equals("user")){
                                                Toast.makeText(LoginUserActivity.this, "Succesfully logged in", Toast.LENGTH_SHORT).show();
                                                mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                                                    @Override
                                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                                        if(mFirebaseUser != null){
                                                            Toast.makeText(LoginUserActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(LoginUserActivity.this, HomePageActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(LoginUserActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                };
                                                mFirebaseAuth.addAuthStateListener(mAuthStateListener);
                                                finish();
                                            } else {
                                                Toast.makeText(LoginUserActivity.this, "Not User", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(LoginUserActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginUserActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
        }
    }
}