package com.campplanner.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.campplanner.app.R;
import com.campplanner.app.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 secondes
    private ImageView logoIcon;
    private TextView appName, tagline;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialiser SessionManager
        sessionManager = new SessionManager(this);

        // Initialiser les vues
        initViews();

        // Appliquer les animations
        applyAnimations();

        // Naviguer après le délai
        navigateToNextScreen();
    }

    private void initViews() {
        logoIcon = findViewById(R.id.logo);
        appName = findViewById(R.id.app_name);
        tagline = findViewById(R.id.app_tagline);
    }

    private void applyAnimations() {
        // Animation fade in pour le logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1000);

        // Animation slide up pour le texte
        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideUp.setDuration(1000);
        slideUp.setStartOffset(300);

        // Appliquer les animations
        if (logoIcon != null) {
            logoIcon.startAnimation(fadeIn);
        }

        if (appName != null) {
            appName.startAnimation(slideUp);
        }

        if (tagline != null) {
            Animation fadeInText = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            fadeInText.setDuration(1000);
            fadeInText.setStartOffset(600);
            tagline.startAnimation(fadeInText);
        }
    }

    private void navigateToNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                // Vérifier si l'utilisateur est connecté
                if (sessionManager.isLoggedIn()) {
                    // Aller directement à MainActivity
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    // Aller à WelcomeActivity (ou LoginActivity)
                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    // Si vous n'avez pas WelcomeActivity, utilisez LoginActivity
                    // intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);

                // Animation de transition
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                // Terminer cette activité
                finish();
            }
        }, SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        // Désactiver le bouton retour sur le splash screen
        // Ne rien faire
        super.onBackPressed();
    }
}