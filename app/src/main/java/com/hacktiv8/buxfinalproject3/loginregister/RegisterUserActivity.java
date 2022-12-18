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
import com.google.firebase.database.FirebaseDatabase;
import com.hacktiv8.buxfinalproject3.databinding.ActivityRegisterUserBinding;
import com.hacktiv8.buxfinalproject3.model.DataUser;

public class RegisterUserActivity extends AppCompatActivity {
    ActivityRegisterUserBinding binding;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFirebaseAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccountUser();
            }

        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUserActivity.this, LoginUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterUserActivity.this, LoginUserActivity.class);
        startActivity(intent);
    }

    private void createAccountUser(){
        String username = binding.etUsername.getText().toString().trim();
        String email = binding.etRegisEmail.getText().toString().trim();
        String pass = binding.etRegisPass.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();

        if(email.isEmpty() && pass.isEmpty() && username.isEmpty() && phone.isEmpty()){
            Toast.makeText(RegisterUserActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DataUser dataUser = new DataUser(username, email, phone, pass, "user");
                            String uId = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference("User").child(uId).setValue(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUserActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterUserActivity.this, LoginUserActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterUserActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                });
    }
}