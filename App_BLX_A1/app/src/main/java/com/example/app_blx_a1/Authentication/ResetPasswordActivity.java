package com.example.app_blx_a1.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_blx_a1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText edtEmailReset;

    Button btnReset;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }


    void addEvents(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailReset.getText().toString().trim();
                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmailReset.setError("Enter valid Email");
                    edtEmailReset.requestFocus();
                }
                resetPassword(email);
            }
        });
    }

    void resetPassword(String email){
        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPasswordActivity.this,"Kiem tra email",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this,SignInActivity.class);
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPasswordActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
    void addControls(){
        edtEmailReset = findViewById(R.id.edtEmailReset);
        btnReset = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar4);
    }
}