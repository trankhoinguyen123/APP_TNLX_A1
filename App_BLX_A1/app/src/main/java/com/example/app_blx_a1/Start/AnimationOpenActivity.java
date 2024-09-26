package com.example.app_blx_a1.Start;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_blx_a1.Authentication.SignInActivity;
import com.example.app_blx_a1.MainActivity;
import com.example.app_blx_a1.R;

public class AnimationOpenActivity extends AppCompatActivity {

    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animation_open);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt1 = findViewById(R.id.txtWaterDelivery);
        txt1.animate().translationY(1000).setDuration(1000).setStartDelay(2500);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {

                    Thread.sleep(4000);
                    Intent intent = new Intent(AnimationOpenActivity.this, SignInActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}