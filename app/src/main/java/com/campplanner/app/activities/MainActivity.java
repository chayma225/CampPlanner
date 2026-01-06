package com.campplanner.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.campplanner.app.R;
import com.campplanner.app.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private CardView cardPlanning, cardBudget, cardReservations;
    private CardView cardEquipment, cardJournal, cardWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        // Vérifier si l'utilisateur est connecté
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // Initialiser toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("CampPlanner");
        }

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        cardPlanning = findViewById(R.id.plannig_card);
        cardBudget = findViewById(R.id.budget_card);
        cardReservations = findViewById(R.id.reservation_card);
        cardEquipment = findViewById(R.id.equipement_card);
        cardJournal = findViewById(R.id.journal_card);
        cardWeather = findViewById(R.id.meteo_card);
    }

    private void setupClickListeners() {
        cardPlanning.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PlanningActivity.class));
        });

        cardBudget.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BudgetActivity.class));
        });

        cardReservations.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReservationActivity.class));
        });

        cardEquipment.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EquipmentActivity.class));
        });

        cardJournal.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, JournalActivity.class));
        });

        cardWeather.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MeteoActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sessionManager.logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}