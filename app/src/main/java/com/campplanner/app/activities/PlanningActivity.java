package com.campplanner.app.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campplanner.app.R;
import com.campplanner.app.adapters.TripAdapter;
import com.campplanner.app.database.TripDAO;
import com.campplanner.app.models.Trip;
import com.campplanner.app.utils.Constants;
import com.campplanner.app.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PlanningActivity extends AppCompatActivity {

    private static final String TAG = "PlanningActivity";

    private RecyclerView tripsRecyclerView, todayActivitiesRecyclerView;
    private TextView tvNoActivities;
    private TripAdapter tripAdapter;
    private TripDAO tripDAO;
    private SessionManager sessionManager;
    private MaterialButton btnNewTrip;
    private MaterialCardView cardCreateItinerary, cardExplorePlaces;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        // Init DAOs et SessionManager
        tripDAO = new TripDAO(this);
        sessionManager = new SessionManager(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Init views
        tripsRecyclerView = findViewById(R.id.upcoming_trips_recycler);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        todayActivitiesRecyclerView = findViewById(R.id.today_activities_recycler);
        todayActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvNoActivities = findViewById(R.id.tv_no_activities);
        btnNewTrip = findViewById(R.id.btn_add_trip);
        cardCreateItinerary = findViewById(R.id.card_create_itinerary);
        cardExplorePlaces = findViewById(R.id.card_explore_places);

        // Listener boutons
        btnNewTrip.setOnClickListener(v -> showAddTripDialog());
        cardCreateItinerary.setOnClickListener(v -> Toast.makeText(this, "Créer un itinéraire (à venir)", Toast.LENGTH_SHORT).show());
        cardExplorePlaces.setOnClickListener(v -> Toast.makeText(this, "Explorer des lieux (à venir)", Toast.LENGTH_SHORT).show());

        // Charger les données
        loadTrips();
        loadTodayActivities();
    }

    private void loadTrips() {
        int userId = sessionManager.getUserId();
        List<Trip> trips = tripDAO.getAllTripsForUser(userId);
        if (trips == null) trips = new ArrayList<>();

        tripAdapter = new TripAdapter(this, trips);
        tripsRecyclerView.setAdapter(tripAdapter);

        tripAdapter.setOnTripClickListener(trip -> {
            Intent intent = new Intent(PlanningActivity.this, TripDetailsActivity.class);
            intent.putExtra(Constants.KEY_TRIP_ID, trip.getId());
            startActivity(intent);
        });
    }

    private void loadTodayActivities() {
        tvNoActivities.setVisibility(View.VISIBLE);
        todayActivitiesRecyclerView.setVisibility(View.GONE);
    }

    private void showAddTripDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouveau séjour");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_trip, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.name_input);
        EditText startDateInput = dialogView.findViewById(R.id.start_date_input);
        EditText endDateInput = dialogView.findViewById(R.id.end_date_input);
        EditText participantsInput = dialogView.findViewById(R.id.participants_input);
        EditText locationInput = dialogView.findViewById(R.id.location_input);

        Calendar calendar = Calendar.getInstance();

        startDateInput.setOnClickListener(v -> new DatePickerDialog(this,
                (view, year, month, day) -> startDateInput.setText(dateFormat.format(new GregorianCalendar(year, month, day).getTime())),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        endDateInput.setOnClickListener(v -> new DatePickerDialog(this,
                (view, year, month, day) -> endDateInput.setText(dateFormat.format(new GregorianCalendar(year, month, day).getTime())),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        builder.setPositiveButton("Créer", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String startDate = startDateInput.getText().toString().trim();
            String endDate = endDateInput.getText().toString().trim();
            String location = locationInput.getText().toString().trim();
            int participants = 1;
            try {
                participants = Integer.parseInt(participantsInput.getText().toString().trim());
            } catch (Exception ignored) {}

            if (name.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Trip newTrip = new Trip();
            newTrip.setUserId(sessionManager.getUserId());
            newTrip.setName(name);
            newTrip.setStartDate(startDate);
            newTrip.setEndDate(endDate);
            newTrip.setLocation(location);
            newTrip.setParticipants(participants);
            newTrip.setStatus("En attente");

            long id = tripDAO.addTrip(newTrip);
            if (id > 0) {
                newTrip.setId((int) id);
                tripAdapter.addTrip(newTrip);
                tripsRecyclerView.scrollToPosition(tripAdapter.getItemCount() - 1);
                Toast.makeText(this, "Séjour créé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de la création", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }
}
