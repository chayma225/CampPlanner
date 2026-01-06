package com.campplanner.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.adapters.JournalAdapter;
import com.campplanner.app.database.JournalDAO;
import com.campplanner.app.models.JournalEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class JournalActivity extends AppCompatActivity implements JournalAdapter.OnJournalClickListener {

    private RecyclerView journalRecyclerView;
    private JournalAdapter journalAdapter;
    private FloatingActionButton fabAddEntry;
    private JournalDAO journalDAO;
    private int currentTripId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        journalDAO = new JournalDAO(this);
        journalRecyclerView = findViewById(R.id.journal_recycler_view);
        fabAddEntry = findViewById(R.id.fab_add_entry);

        journalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        journalAdapter = new JournalAdapter(this, new ArrayList<>());
        journalAdapter.setOnJournalClickListener(this);
        journalRecyclerView.setAdapter(journalAdapter);

        if (getIntent() != null) {
            currentTripId = getIntent().getIntExtra("trip_id", -1);
        }

        if (currentTripId != -1) {
            loadJournalEntries();
        }

        fabAddEntry.setOnClickListener(v -> showAddEditJournalDialog(null));
    }

    private void loadJournalEntries() {
        List<JournalEntry> entries = journalDAO.getEntriesForTrip(currentTripId);
        if (entries == null) entries = new ArrayList<>();
        journalAdapter.updateList(entries);
    }

    private void showAddEditJournalDialog(JournalEntry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_journal, null);
        builder.setView(dialogView);

        EditText etTitle = dialogView.findViewById(R.id.et_journal_title);
        EditText etContent = dialogView.findViewById(R.id.et_journal_content);
        EditText etLocation = dialogView.findViewById(R.id.et_journal_location);

        if (entry != null) {
            builder.setTitle("Modifier l'entrée de journal");
            etTitle.setText(entry.getTitle());
            etContent.setText(entry.getContent());
            etLocation.setText(entry.getLocation());
        } else {
            builder.setTitle("Ajouter une entrée de journal");
        }

        builder.setPositiveButton("Sauvegarder", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Le titre et le contenu sont requis", Toast.LENGTH_SHORT).show();
                return;
            }

            if (entry == null) {
                JournalEntry newEntry = new JournalEntry();
                newEntry.setTripId(currentTripId);
                newEntry.setTitle(title);
                newEntry.setContent(content);
                newEntry.setLocation(location);
                newEntry.setDate(String.valueOf(System.currentTimeMillis()));
                newEntry.setPhotoPath(null);
                journalDAO.addEntry(newEntry);
                Toast.makeText(this, "Entrée de journal ajoutée", Toast.LENGTH_SHORT).show();
            } else {
                entry.setTitle(title);
                entry.setContent(content);
                entry.setLocation(location);
                journalDAO.updateEntry(entry);
                Toast.makeText(this, "Entrée de journal mise à jour", Toast.LENGTH_SHORT).show();
            }
            loadJournalEntries();
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    @Override
    public void onJournalClick(JournalEntry entry) {
        showAddEditJournalDialog(entry);
    }

    @Override
    public void onJournalLongClick(JournalEntry entry) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer l'entrée de journal")
                .setMessage("Êtes-vous sûr de vouloir supprimer l'entrée \"" + entry.getTitle() + "\" ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    journalDAO.deleteEntry(entry.getId());
                    Toast.makeText(this, "Entrée de journal supprimée", Toast.LENGTH_SHORT).show();
                    loadJournalEntries();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
