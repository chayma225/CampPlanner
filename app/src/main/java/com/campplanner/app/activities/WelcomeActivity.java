package com.campplanner.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.campplanner.app.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnStart = findViewById(R.id.btn_start);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void setupListeners() {
        // Bouton "Commencer maintenant" → Register
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Lien "J'ai déjà un compte" → Login
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    @Override
    public void onBackPressed() {
        // Quitter l'application depuis Welcome
        super.onBackPressed();
        finishAffinity();
    }
}