// ==================== RegisterActivity.java ====================
package com.campplanner.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.campplanner.app.R;
import com.campplanner.app.database.UserDAO;
import com.campplanner.app.models.User;
import com.campplanner.app.utils.Constants;
import com.campplanner.app.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private TextView loginLink;
    private UserDAO userDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        userDAO = new UserDAO(this);
        sessionManager = new SessionManager(this);

        registerButton.setOnClickListener(v -> handleRegister());
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        registerButton = findViewById(R.id.register_button);
        loginLink = findViewById(R.id.login_link);
    }

    private void handleRegister() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            firstNameInput.setError("Prénom requis");
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameInput.setError("Nom requis");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email requis");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Email invalide");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Mot de passe requis");
            return;
        }

        if (password.length() < Constants.MIN_PASSWORD_LENGTH) {
            passwordInput.setError("Le mot de passe doit contenir au moins " +
                    Constants.MIN_PASSWORD_LENGTH + " caractères");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Les mots de passe ne correspondent pas");
            return;
        }

        // Check if email already exists
        if (userDAO.getUserByEmail(email) != null) {
            emailInput.setError("Cet email est déjà utilisé");
            return;
        }

        // Create new user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        long userId = userDAO.addUser(user);

        if (userId > 0) {
            Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show();
            sessionManager.createLoginSession((int) userId, email, firstName + " " + lastName);

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
        }
    }
}