package com.example.appchat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appchat.R;
import com.example.appchat.databinding.*;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();


    }
    private void setListeners() {
        binding.textCreateAccount.setOnClickListener(v->{
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}