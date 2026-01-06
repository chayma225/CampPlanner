package com.campplanner.app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.adapters.BudgetCategoryAdapter;
import com.campplanner.app.database.BudgetDAO;
import com.campplanner.app.models.BudgetCategory;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    private RecyclerView categoriesRecyclerView;
    private BudgetCategoryAdapter categoryAdapter;
    private BudgetDAO budgetDAO;
    private int currentTripId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        budgetDAO = new BudgetDAO(this);
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent() != null)
            currentTripId = getIntent().getIntExtra("trip_id", -1);

        if (currentTripId != -1)
            loadCategories();
    }

    private void loadCategories() {
        List<BudgetCategory> categories = budgetDAO.getCategoriesForTrip(currentTripId);
        if (categories == null) categories = java.util.Collections.emptyList();

        categoryAdapter = new BudgetCategoryAdapter(this, categories);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }
}
