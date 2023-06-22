package com.example.appchat.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.appchat.MainActivity;
import com.example.appchat.R;
import com.example.appchat.databinding.ActivitySignUpBinding;
import com.example.appchat.model.User;
import com.example.appchat.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String baseCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        // func click
        setListeners();


    }




    private void setListeners() {
        binding.textSignIn.setOnClickListener(v->{
            finish();
        });
        binding.btnSignUp.setOnClickListener(v->{
            validateForm();

            signUp();
        });
        binding.layoutImage.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

    }


    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        User user = new User();
        user.setName(binding.edName.getText().toString().trim());
        user.setEmail(binding.edEmail.getText().toString().trim());
        user.setPassword(binding.edPassword.getText().toString().trim());
        user.setImage(baseCodeImage);
        database.collection(User.KEY_COLLECTION_USER).add(user)
                .addOnSuccessListener(documentReference ->{
                    loading(false);
                    preferenceManager.putBoolean(User.KEY_IS_SIGN_IN, true);
                    preferenceManager.putString(User.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(User.KEY_NAME,binding.edName.getText().toString().trim());
                    preferenceManager.putString(User.KEY_IMAGE,baseCodeImage);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    showToast("Thêm thành công");

                })
                .addOnFailureListener(exception ->{
                    loading(false);
                    Log.e("ERROR", "");
                    showToast("Thêm không thành công");
                });





    }


    private  String encodeImage(Bitmap bitmap){
        int width = 150;
        int height = bitmap.getHeight() * width / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    private ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null){
                    Uri uri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imageProfile.setImageBitmap(bitmap);
                        binding.textAddImage.setVisibility(View.GONE);
                        baseCodeImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void validateForm(){
        if (baseCodeImage==null) {
            showToast("Select image ");
            return;
        }
      if (binding.edName.getText().toString().trim().isEmpty()){
          showToast("Enter name");
          return;
      }
        if (binding.edEmail.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.getText()).matches()) {
            showToast("Enter email address");
            return;
        }
        if (binding.edPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return;
        }
        if (binding.edConfirmPassword.getText().toString().trim().isEmpty()){
            showToast("Enter confirm password");
            return;
        }
        if (!binding.edPassword.getText().toString().trim().equals(binding.edConfirmPassword.getText().toString().trim())){
            showToast("password == confirm password");
            return;
        }

    }
    private void loading(boolean isLoading) {
        if (isLoading){
            binding.btnSignUp.setVisibility(View.INVISIBLE);
            binding.progress.setVisibility(View.VISIBLE);

        }else {
            binding.btnSignUp.setVisibility(View.VISIBLE);
            binding.progress.setVisibility(View.INVISIBLE);
        }

    }
}