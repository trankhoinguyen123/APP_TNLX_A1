package com.example.app_blx_a1.Authentication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_blx_a1.MainActivity;
import com.example.app_blx_a1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    TextView txtTaoMoiTK, txtQuenMK;
    Button btnSignIn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }


    void addEvents(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if( email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Enter a valid Email");
                    edtEmail.requestFocus();
                }
                if(password.isEmpty() || password.length() <6){
                    edtPassword.setError("Enter a valid Password");
                    edtPassword.requestFocus();
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(SignInActivity.this, "Sign in suc.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                } else {

                                    Toast.makeText(SignInActivity.this, "Sign in failed.",
                                            Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                        });
            }
        });

        txtTaoMoiTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    void addControls(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar3);
        txtTaoMoiTK = findViewById(R.id.txtTaoMoiTK);
        txtTaoMoiTK.setPaintFlags(txtTaoMoiTK.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtQuenMK = findViewById(R.id.txtQuenMatKhau);
        txtQuenMK.setPaintFlags(txtQuenMK.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }
}