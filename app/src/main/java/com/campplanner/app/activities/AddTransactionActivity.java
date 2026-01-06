package com.campplanner.app.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.campplanner.app.R;
import com.campplanner.app.database.BudgetDAO;
import com.campplanner.app.models.BudgetCategory;
import com.campplanner.app.models.Transaction;
import com.campplanner.app.utils.Constants;
import com.campplanner.app.utils.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner categorySpinner;
    private EditText descriptionInput, amountInput, dateInput;
    private RadioGroup typeRadioGroup;
    private RadioButton expenseRadio, incomeRadio;
    private Button saveButton, cancelButton;

    private BudgetDAO budgetDAO;
    private List<BudgetCategory> categoryList;
    private int selectedCategoryId = -1;
    private int tripId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initViews();
        setupToolbar();

        budgetDAO = new BudgetDAO(this);

        // Get category ID from intent if available
        selectedCategoryId = getIntent().getIntExtra(Constants.KEY_CATEGORY_ID, -1);
        tripId = getIntent().getIntExtra(Constants.KEY_TRIP_ID, -1);

        loadCategories();
        setupDatePicker();

        saveButton.setOnClickListener(v -> saveTransaction());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        categorySpinner = findViewById(R.id.category_spinner);
        descriptionInput = findViewById(R.id.description_input);
        amountInput = findViewById(R.id.amount_input);
        dateInput = findViewById(R.id.date_input);
        typeRadioGroup = findViewById(R.id.type_radio_group);
        expenseRadio = findViewById(R.id.expense_radio);
        incomeRadio = findViewById(R.id.income_radio);
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Set current date as default
        dateInput.setText(DateUtils.getCurrentDate());

        // Set expense as default
        expenseRadio.setChecked(true);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Nouvelle transaction");
        }
    }

    private void loadCategories() {
        // Load categories for the trip
        if (tripId != -1) {
            categoryList = budgetDAO.getCategoriesForTrip(tripId);
        } else if (selectedCategoryId != -1) {
            // Get category and its trip
            // For simplicity, we'll create a list with just this category
            categoryList = new ArrayList<>();
            // Note: You'd need to get the full category object here
        } else {
            categoryList = new ArrayList<>();
        }

        List<String> categoryNames = new ArrayList<>();
        for (BudgetCategory category : categoryList) {
            categoryNames.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Select the category if one was passed
        if (selectedCategoryId != -1) {
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == selectedCategoryId) {
                    categorySpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setupDatePicker() {
        dateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        dateInput.setText(DateUtils.formatDate(selectedDate.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });
    }

    private void saveTransaction() {
        String description = descriptionInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(description)) {
            descriptionInput.setError("Description requise");
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            amountInput.setError("Montant requis");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                amountInput.setError("Le montant doit être positif");
                return;
            }
        } catch (NumberFormatException e) {
            amountInput.setError("Montant invalide");
            return;
        }

        if (categorySpinner.getSelectedItemPosition() < 0) {
            Toast.makeText(this, "Veuillez sélectionner une catégorie", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected category
        BudgetCategory selectedCategory = categoryList.get(categorySpinner.getSelectedItemPosition());

        // Determine transaction type
        String type = expenseRadio.isChecked() ? "dépense" : "revenu";

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCategoryId(selectedCategory.getId());
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setType(type);

        // Save transaction
        long id = budgetDAO.addTransaction(transaction);

        if (id > 0) {
            Toast.makeText(this, "Transaction enregistrée", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}