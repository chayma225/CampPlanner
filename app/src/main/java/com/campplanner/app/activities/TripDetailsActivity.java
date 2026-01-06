package com.campplanner.app.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campplanner.app.R;
import com.campplanner.app.database.ActivityDAO;
import com.campplanner.app.database.BudgetDAO;
import com.campplanner.app.database.TripDAO;
import com.campplanner.app.models.Activity;
import com.campplanner.app.models.BudgetCategory;
import com.campplanner.app.models.Trip;
import com.campplanner.app.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TripDetailsActivity extends AppCompatActivity {

    private Trip currentTrip;
    private TripDAO tripDAO;
    private ActivityDAO activityDAO;
    private BudgetDAO budgetDAO;

    private TextView tripName, tripLocation, tripDates, tripStatus, tripDescription;
    private TextView budgetTotal, budgetSpent, budgetRemaining;
    private RecyclerView activitiesRecyclerView, budgetRecyclerView;
    private FloatingActionButton fabAdd;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private int currentTab = 0; // 0 = Activities, 1 = Budget

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        // Initialize DAOs
        tripDAO = new TripDAO(this);
        activityDAO = new ActivityDAO(this);
        budgetDAO = new BudgetDAO(this);

        // Initialize views
        initViews();

        // Get trip ID from intent
        int tripId = getIntent().getIntExtra(Constants.KEY_TRIP_ID, -1);
        if (tripId == -1) {
            Toast.makeText(this, "Erreur: Séjour introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load trip data
        loadTripData(tripId);

        // Setup toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(currentTrip != null ? currentTrip.getName() : "Détails du séjour");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup tabs
        setupTabs();

        // Setup FAB
        fabAdd.setOnClickListener(v -> {
            if (currentTab == 0) {
                showAddActivityDialog();
            } else {
                showAddBudgetCategoryDialog();
            }
        });

        // Load initial data
        loadActivities();
        loadBudgetCategories();
        updateBudgetSummary();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        tripName = findViewById(R.id.trip_name);
        tripLocation = findViewById(R.id.trip_location);
        tripDates = findViewById(R.id.trip_dates);
        tripStatus = findViewById(R.id.trip_status);
        tripDescription = findViewById(R.id.trip_description);
        budgetTotal = findViewById(R.id.budget_total);
        budgetSpent = findViewById(R.id.budget_spent);
        budgetRemaining = findViewById(R.id.budget_remaining);
        activitiesRecyclerView = findViewById(R.id.activities_recycler_view);
        budgetRecyclerView = findViewById(R.id.budget_recycler_view);
        fabAdd = findViewById(R.id.fab_add);

        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        budgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Activités"));
        tabLayout.addTab(tabLayout.newTab().setText("Budget"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                if (currentTab == 0) {
                    activitiesRecyclerView.setVisibility(View.VISIBLE);
                    budgetRecyclerView.setVisibility(View.GONE);
                } else {
                    activitiesRecyclerView.setVisibility(View.GONE);
                    budgetRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadTripData(int tripId) {
        currentTrip = tripDAO.getTripById(tripId);
        if (currentTrip == null) {
            Toast.makeText(this, "Erreur: Séjour introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tripName.setText(currentTrip.getName());
        tripLocation.setText(currentTrip.getLocation());
        tripDates.setText(currentTrip.getStartDate() + " - " + currentTrip.getEndDate());
        tripStatus.setText("Statut: " + currentTrip.getStatus());
        tripDescription.setText(currentTrip.getDescription() != null ? currentTrip.getDescription() : "Aucune description");
    }

    private void loadActivities() {
        if (currentTrip == null) return;

        List<Activity> activities = activityDAO.getActivitiesForTrip(currentTrip.getId());

        // TODO: Create ActivityAdapter and set it here
        // For now, just show a toast
        Toast.makeText(this, "Activités: " + activities.size(), Toast.LENGTH_SHORT).show();
    }

    private void loadBudgetCategories() {
        if (currentTrip == null) return;

        List<BudgetCategory> categories = budgetDAO.getCategoriesForTrip(currentTrip.getId());

        // TODO: Create BudgetCategoryAdapter and set it here
        // For now, just show a toast
        Toast.makeText(this, "Catégories budget: " + categories.size(), Toast.LENGTH_SHORT).show();
    }

    private void updateBudgetSummary() {
        if (currentTrip == null) return;

        List<BudgetCategory> categories = budgetDAO.getCategoriesForTrip(currentTrip.getId());

        double total = 0;
        double spent = 0;

        for (BudgetCategory category : categories) {
            total += category.getBudget();
            spent += category.getSpent();
        }

        double remaining = total - spent;

        budgetTotal.setText(String.format("%.2f €", total));
        budgetSpent.setText(String.format("%.2f €", spent));
        budgetRemaining.setText(String.format("%.2f €", remaining));
    }

    private void showAddActivityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouvelle activité");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_activity, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.activity_name_input);
        EditText dateInput = dialogView.findViewById(R.id.activity_date_input);
        EditText timeInput = dialogView.findViewById(R.id.activity_time_input);
        EditText locationInput = dialogView.findViewById(R.id.activity_location_input);
        EditText descriptionInput = dialogView.findViewById(R.id.activity_description_input);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            String time = timeInput.getText().toString().trim();
            String location = locationInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Nom et date requis", Toast.LENGTH_SHORT).show();
                return;
            }

            Activity activity = new Activity();
            activity.setTripId(currentTrip.getId());
            activity.setName(name);
            activity.setDate(date);
            activity.setTime(time);
            activity.setLocation(location);
            activity.setDescription(description);
            activity.setCompleted(false);

            long id = activityDAO.addActivity(activity);
            if (id > 0) {
                Toast.makeText(this, "Activité ajoutée", Toast.LENGTH_SHORT).show();
                loadActivities();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    private void showAddBudgetCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouvelle catégorie");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_budget_category, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.category_name_input);
        EditText budgetInput = dialogView.findViewById(R.id.category_budget_input);
        Spinner colorSpinner = dialogView.findViewById(R.id.category_color_spinner);

        // Setup color spinner
        String[] colors = {"Bleu", "Vert", "Orange", "Violet", "Rouge", "Gris"};
        String[] colorValues = {
                Constants.COLOR_ACCOMMODATION,
                Constants.COLOR_FOOD,
                Constants.COLOR_TRANSPORT,
                Constants.COLOR_ACTIVITIES,
                Constants.COLOR_EQUIPMENT,
                Constants.COLOR_MISCELLANEOUS
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String budgetStr = budgetInput.getText().toString().trim();

            if (name.isEmpty() || budgetStr.isEmpty()) {
                Toast.makeText(this, "Nom et budget requis", Toast.LENGTH_SHORT).show();
                return;
            }

            double budget = 0;
            try {
                budget = Double.parseDouble(budgetStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Budget invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            String color = colorValues[colorSpinner.getSelectedItemPosition()];

            BudgetCategory category = new BudgetCategory();
            category.setTripId(currentTrip.getId());
            category.setName(name);
            category.setBudget(budget);
            category.setSpent(0);
            category.setColor(color);

            long id = budgetDAO.addCategory(category);
            if (id > 0) {
                Toast.makeText(this, "Catégorie ajoutée", Toast.LENGTH_SHORT).show();
                loadBudgetCategories();
                updateBudgetSummary();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }
}