package com.campplanner.app.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campplanner.app.R;
import com.campplanner.app.adapters.EquipmentAdapter;
import com.campplanner.app.models.EquipmentItem;

import java.util.ArrayList;
import java.util.List;

public class EquipmentActivity extends AppCompatActivity implements EquipmentAdapter.OnEquipmentClickListener {

    private RecyclerView equipmentRecyclerView;
    private EquipmentAdapter equipmentAdapter;
    private Spinner spinnerLists;
    private Button btnAddItem;
    private TextView tvSubtitle;

    private List<EquipmentItem> equipmentItems = new ArrayList<>();
    private List<String> staticLists = new ArrayList<>();
    private String currentList = "Liste principale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipement);

        initViews();
        setupSpinner();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        equipmentRecyclerView = findViewById(R.id.equipment_recycler_view);
        spinnerLists = findViewById(R.id.spinner_Lists);
        btnAddItem = findViewById(R.id.btnManageList); // bouton "Gérer la liste"
        tvSubtitle = findViewById(R.id.tvSubtitle);
    }

    private void setupSpinner() {
        // listes statiques
        staticLists.add("Tous les équipements"); // <-- Nouvelle liste
        staticLists.add("Liste principale");
        staticLists.add("Cuisine");
        staticLists.add("Couchage");
        staticLists.add("Vêtements");
        staticLists.add("Autres");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                staticLists
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLists.setAdapter(adapter);

        spinnerLists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentList = staticLists.get(position);
                Toast.makeText(EquipmentActivity.this, "Liste sélectionnée: " + currentList, Toast.LENGTH_SHORT).show();
                loadEquipmentForCurrentList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupRecyclerView() {
        equipmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        equipmentAdapter = new EquipmentAdapter(this, equipmentItems);
        equipmentAdapter.setOnEquipmentClickListener(this);
        equipmentRecyclerView.setAdapter(equipmentAdapter);
    }

    private void setupListeners() {
        btnAddItem.setOnClickListener(v -> showAddEditEquipmentDialog(null));
    }

    private void loadEquipmentForCurrentList() {
        equipmentItems.clear();

        if (currentList.equals("Tous les équipements")) {
            // Combiner tous les équipements de toutes les listes
            equipmentItems.add(new EquipmentItem("Tente", "Abri", 1));
            equipmentItems.add(new EquipmentItem("Sac de couchage", "Couchage", 1));
            equipmentItems.add(new EquipmentItem("Réchaud", "Cuisine", 1));
            equipmentItems.add(new EquipmentItem("Casserole", "Cuisine", 1));
            equipmentItems.add(new EquipmentItem("Matelas gonflable", "Couchage", 1));
            equipmentItems.add(new EquipmentItem("Oreiller", "Couchage", 1));
            equipmentItems.add(new EquipmentItem("Veste imperméable", "Vêtements", 1));
            equipmentItems.add(new EquipmentItem("Vêtements de rechange", "Vêtements", 3));
            equipmentItems.add(new EquipmentItem("Lampe torche", "Divers", 1));
            equipmentItems.add(new EquipmentItem("Chargeur portable", "Divers", 1));
        } else {
            // Les listes existantes
            switch (currentList) {
                case "Liste principale":
                    equipmentItems.add(new EquipmentItem("Tente", "Abri", 1));
                    equipmentItems.add(new EquipmentItem("Sac de couchage", "Couchage", 1));
                    break;
                case "Cuisine":
                    equipmentItems.add(new EquipmentItem("Réchaud", "Cuisine", 1));
                    equipmentItems.add(new EquipmentItem("Casserole", "Cuisine", 1));
                    break;
                case "Couchage":
                    equipmentItems.add(new EquipmentItem("Matelas gonflable", "Couchage", 1));
                    equipmentItems.add(new EquipmentItem("Oreiller", "Couchage", 1));
                    break;
                case "Vêtements":
                    equipmentItems.add(new EquipmentItem("Veste imperméable", "Vêtements", 1));
                    equipmentItems.add(new EquipmentItem("Vêtements de rechange", "Vêtements", 3));
                    break;
                case "Autres":
                    equipmentItems.add(new EquipmentItem("Lampe torche", "Divers", 1));
                    equipmentItems.add(new EquipmentItem("Chargeur portable", "Divers", 1));
                    break;
            }
        }

        equipmentAdapter.updateList(equipmentItems);
        updateProgress();
    }


    private void updateProgress() {
        int packed = 0;
        for (EquipmentItem item : equipmentItems) {
            if (item.isPacked()) packed++;
        }
        tvSubtitle.setText(packed + "/" + equipmentItems.size() + " articles emballés");
    }

    private void showAddEquipmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_equipement, null);
        builder.setView(dialogView);

        Spinner spinnerList = dialogView.findViewById(R.id.spinner_list_selection);
        EditText etName = dialogView.findViewById(R.id.et_equipment_name);
        EditText etQuantity = dialogView.findViewById(R.id.et_equipment_quantity);

        // Listes disponibles
        List<String> lists = new ArrayList<>();
        lists.add("Liste principale");
        lists.add("Cuisine");
        lists.add("Couchage");
        lists.add("Vêtements");
        lists.add("Autres");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerList.setAdapter(adapter);

        builder.setTitle("Ajouter un équipement");

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String selectedList = spinnerList.getSelectedItem().toString();
            String name = etName.getText().toString().trim();
            int quantity;

            try {
                quantity = Integer.parseInt(etQuantity.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantité invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty()) {
                Toast.makeText(this, "Le nom de l'équipement est requis", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ajouter l'équipement à la liste correspondante
            EquipmentItem newItem = new EquipmentItem(name, selectedList, quantity);
            equipmentItems.add(newItem);
            equipmentAdapter.updateList(equipmentItems);
            updateProgress();

            Toast.makeText(this, "Équipement ajouté à " + selectedList, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }


    @Override
    public void onEquipmentClick(EquipmentItem item) {
        showAddEditEquipmentDialog(item);
    }

    private void showAddEditEquipmentDialog(EquipmentItem item) {
    }

    @Override
    public void onEquipmentLongClick(EquipmentItem item) {
        equipmentItems.remove(item);
        equipmentAdapter.updateList(equipmentItems);
        updateProgress();
        Toast.makeText(this, "Équipement supprimé", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEquipmentCheckedChanged(EquipmentItem item, boolean isChecked) {
        item.setPacked(isChecked);
        updateProgress();
    }
}
