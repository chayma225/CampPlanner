package com.campplanner.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.campplanner.app.R;
import com.campplanner.app.database.UserDAO;
import com.campplanner.app.models.User;
import com.campplanner.app.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    // Views
    private ImageButton btnBack;
    private TextView tabLogin, tabRegister;
    private EditText emailInput, passwordInput;
    private ImageButton btnTogglePassword;
    private CheckBox checkboxRemember;
    private TextView tvForgotPassword;
    private Button loginButton;
    private CardView btnGoogle, btnFacebook;

    // Data
    private UserDAO userDAO;
    private SessionManager sessionManager;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser Database et Session
        userDAO = new UserDAO(this);
        sessionManager = new SessionManager(this);

        // Initialiser les vues
        initViews();

        // Configurer les listeners
        setupListeners();

        // Vérifier si l'utilisateur a demandé à être rappelé
        checkRememberedUser();
    }

    private void initViews() {
        // Header
        btnBack = findViewById(R.id.btn_back);

        // Tabs
        tabLogin = findViewById(R.id.tab_login);
        tabRegister = findViewById(R.id.tab_register);

        // Form inputs
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        btnTogglePassword = findViewById(R.id.btn_toggle_password);
        checkboxRemember = findViewById(R.id.checkbox_remember);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        // Buttons
        loginButton = findViewById(R.id.login_button);
        btnGoogle = findViewById(R.id.btn_google);
        btnFacebook = findViewById(R.id.btn_facebook);
    }

    private void setupListeners() {
        // Bouton retour
        btnBack.setOnClickListener(v -> onBackPressed());

        // Tab switching
        setupTabSwitching();

        // Toggle password visibility
        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Forgot password
        tvForgotPassword.setOnClickListener(v -> handleForgotPassword());

        // Login button
        loginButton.setOnClickListener(v -> handleLogin());

        // Social login
        btnGoogle.setOnClickListener(v -> handleGoogleLogin());
        btnFacebook.setOnClickListener(v -> handleFacebookLogin());
    }

    private void setupTabSwitching() {
        tabRegister.setOnClickListener(v -> {
            // Aller à RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Pas d'animation
            finish();
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Cacher le mot de passe
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_arrow_back);
            isPasswordVisible = false;
        } else {
            // Afficher le mot de passe
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btnTogglePassword.setImageResource(R.drawable.ic_arrow_down);
            isPasswordVisible = true;
        }

        // Déplacer le curseur à la fin
        passwordInput.setSelection(passwordInput.getText().length());
    }

    private void checkRememberedUser() {
        // Vérifier si l'utilisateur a coché "Se souvenir de moi"
        String savedEmail = sessionManager.getUserEmail();
        if (savedEmail != null && !savedEmail.isEmpty()) {
            emailInput.setText(savedEmail);
            checkboxRemember.setChecked(true);
        }
    }

    private void handleLogin() {
        // Récupérer les données
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        boolean rememberMe = checkboxRemember.isChecked();

        // Validation
        if (!validateInput(email, password)) {
            return;
        }

        // Afficher un indicateur de chargement
        loginButton.setEnabled(false);
        loginButton.setText("Connexion en cours...");

        // Vérifier les credentials dans la base de données
        if (userDAO.checkUserCredentials(email, password)) {
            // Succès - Récupérer l'utilisateur
            User user = userDAO.getUserByEmail(email);

            if (user != null) {
                // Sauvegarder la session
                sessionManager.createLoginSession(
                        user.getId(),
                        user.getEmail(),
                        user.getFullName()
                );

                // Sauvegarder l'email si "Se souvenir de moi" est coché
                if (rememberMe) {
                    // L'email est déjà sauvegardé dans la session
                } else {
                    // Effacer l'email sauvegardé si décoché
                    // sessionManager.clearRememberedEmail(); // À implémenter si besoin
                }

                // Afficher un message de succès
                Toast.makeText(this, "Bienvenue " + user.getFirstName() + " !",
                        Toast.LENGTH_SHORT).show();

                // Naviguer vers MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity .class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            } else {
                showError("Erreur de chargement du profil");
                resetLoginButton();
            }
        } else {
            // Échec - Afficher un message d'erreur
            showError("Email ou mot de passe incorrect");
            resetLoginButton();

            // Vibration pour indiquer l'erreur (optionnel)
            vibrateError();
        }
    }

    private boolean validateInput(String email, String password) {
        // Valider l'email
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email requis");
            emailInput.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Email invalide");
            emailInput.requestFocus();
            return false;
        }

        // Valider le mot de passe
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Mot de passe requis");
            passwordInput.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordInput.setError("Le mot de passe doit contenir au moins 6 caractères");
            passwordInput.requestFocus();
            return false;
        }

        return true;
    }

    private void resetLoginButton() {
        loginButton.setEnabled(true);
        loginButton.setText("Se connecter");
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void vibrateError() {
        // Vibration courte pour indiquer une erreur
        try {
            android.os.Vibrator vibrator = (android.os.Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleForgotPassword() {
        String email = emailInput.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Veuillez entrer votre email", Toast.LENGTH_SHORT).show();
            emailInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show();
            emailInput.requestFocus();
            return;
        }

        // Vérifier si l'email existe
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            // TODO: Implémenter la logique de réinitialisation du mot de passe
            // Pour l'instant, afficher un message
            Toast.makeText(this,
                    "Un email de réinitialisation a été envoyé à " + email,
                    Toast.LENGTH_LONG).show();

            // Dans une vraie application, vous enverriez un email avec un lien de réinitialisation
        } else {
            Toast.makeText(this,
                    "Aucun compte associé à cet email",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleGoogleLogin() {
        // TODO: Implémenter Google Sign-In
        Toast.makeText(this,
                "Connexion Google à venir...",
                Toast.LENGTH_SHORT).show();

        // Dans une vraie application, vous utiliseriez Google Sign-In SDK
        // https://developers.google.com/identity/sign-in/android/start
    }

    private void handleFacebookLogin() {
        // TODO: Implémenter Facebook Login
        Toast.makeText(this,
                "Connexion Facebook à venir...",
                Toast.LENGTH_SHORT).show();

        // Dans une vraie application, vous utiliseriez Facebook SDK
        // https://developers.facebook.com/docs/facebook-login/android
    }

    @Override
    public void onBackPressed() {
        // Retourner à WelcomeActivity
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Réinitialiser le bouton au cas où
        resetLoginButton();
    }
}