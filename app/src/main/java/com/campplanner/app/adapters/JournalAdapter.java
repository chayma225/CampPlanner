package com.campplanner.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.campplanner.app.R;
import com.campplanner.app.models.JournalEntry;
import com.campplanner.app.utils.DateUtils;

import java.io.File;
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private Context context;
    private List<JournalEntry> journalList;
    private OnJournalClickListener listener;

    public interface OnJournalClickListener {
        void onJournalClick(JournalEntry entry);
        void onJournalLongClick(JournalEntry entry);
    }

    public JournalAdapter(Context context, List<JournalEntry> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    public void setOnJournalClickListener(OnJournalClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_journal, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry entry = journalList.get(position);

        holder.entryTitle.setText(entry.getTitle());
        holder.entryDate.setText(DateUtils.getRelativeDate(entry.getDate()));

        // LOCATION
        if (entry.getLocation() != null && !entry.getLocation().isEmpty()) {
            holder.entryLocation.setText(entry.getLocation());
            holder.entryLocation.setVisibility(View.VISIBLE);
        } else {
            holder.entryLocation.setVisibility(View.GONE);
        }

        // CONTENT PREVIEW
        if (entry.getContent() != null && !entry.getContent().isEmpty()) {
            String preview = entry.getContent();
            if (preview.length() > 100) preview = preview.substring(0, 100) + "...";
            holder.entryContent.setText(preview);
            holder.entryContent.setVisibility(View.VISIBLE);
        } else {
            holder.entryContent.setVisibility(View.GONE);
        }

        // PHOTO WITHOUT GLIDE
        if (entry.getPhotoPath() != null && !entry.getPhotoPath().isEmpty()) {

            File file = new File(entry.getPhotoPath());

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.entryPhoto.setImageBitmap(bitmap);
                holder.entryPhoto.setVisibility(View.VISIBLE);
            } else {
                holder.entryPhoto.setVisibility(View.GONE);
            }

        } else {
            holder.entryPhoto.setVisibility(View.GONE);
        }

        // CLICK
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) listener.onJournalClick(journalList.get(holder.getAdapterPosition()));
        });

        // LONG CLICK
        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) listener.onJournalLongClick(journalList.get(holder.getAdapterPosition()));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public void updateList(List<JournalEntry> newList) {
        this.journalList = newList;
        notifyDataSetChanged();
    }

    public static class JournalViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView entryPhoto;
        TextView entryTitle, entryDate, entryLocation, entryContent;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            entryPhoto = itemView.findViewById(R.id.entry_photo);
            entryTitle = itemView.findViewById(R.id.entry_title);
            entryDate = itemView.findViewById(R.id.entry_date);
            entryLocation = itemView.findViewById(R.id.entry_location);
            entryContent = itemView.findViewById(R.id.entry_content);
        }
    }
}