package com.campplanner.app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.adapters.ReservationAdapter;
import com.campplanner.app.database.ReservationDAO;
import com.campplanner.app.models.Reservation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    private RecyclerView reservationsRecyclerView;
    private ReservationAdapter reservationAdapter;
    private FloatingActionButton fabAddReservation;
    private ReservationDAO reservationDAO;
    private int currentTripId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        reservationDAO = new ReservationDAO(this);
        reservationsRecyclerView = findViewById(R.id.reservations_recycler_view);
        fabAddReservation = findViewById(R.id.fab_add_reservation);

        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Récupérer l’ID du séjour depuis l’Intent
        currentTripId = getIntent().getIntExtra("trip_id", -1);

        if (currentTripId != -1) {
            loadReservations();
        }

        fabAddReservation.setOnClickListener(v -> {
            // Ouvrir le dialogue d’ajout de réservation
        });
    }

    private void loadReservations() {
        List<Reservation> reservations = reservationDAO.getReservationsForTrip(currentTripId);
        reservationAdapter = new ReservationAdapter(this, reservations);
        reservationsRecyclerView.setAdapter(reservationAdapter);
    }
}
