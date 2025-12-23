package com.example.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable delayedRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView logo = findViewById(R.id.logoAndroid);
        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1000)
                .setStartDelay(500)
                .start();

        ImageView gifImage = findViewById(R.id.gifImage);
        Glide.with(this).asGif().load(R.drawable.taylor).into(gifImage);


        Button btnInicio = findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(v -> {
            btnInicio.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
            btnInicio.setEnabled(false);

            delayedRunnable = () -> {
                Intent intent = new Intent(MainActivity.this, CalculadoraActivity.class);
                startActivity(intent);
                finish();
            };
            handler.postDelayed(delayedRunnable, 30000);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (delayedRunnable != null) {
            handler.removeCallbacks(delayedRunnable);
        }
    }
}