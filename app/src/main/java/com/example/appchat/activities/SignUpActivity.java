package com.example.appchat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appchat.R;
import com.example.appchat.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.textSignIn.setOnClickListener(v->{
            finish();
        });

    }
}