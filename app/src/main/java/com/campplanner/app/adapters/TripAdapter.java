package com.campplanner.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.campplanner.app.R;
import com.campplanner.app.models.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context context;
    private List<Trip> trips;
    private OnTripClickListener listener;

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
    }

    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }

    public void setOnTripClickListener(OnTripClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.tvTripName.setText(trip.getName());
        holder.tvTripLocation.setText(trip.getLocation());
        holder.tvTripDates.setText(trip.getStartDate() + " - " + trip.getEndDate());
        holder.tvTripStatus.setText(trip.getStatus());

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTripClick(trip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips != null ? trips.size() : 0;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
        notifyItemInserted(trips.size() - 1);
    }

    public void updateTrip(int position, Trip trip) {
        trips.set(position, trip);
        notifyItemChanged(position);
    }

    public void removeTrip(int position) {
        trips.remove(position);
        notifyItemRemoved(position);
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTripName, tvTripLocation, tvTripDates, tvTripStatus;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_trip);
            tvTripName = itemView.findViewById(R.id.tv_trip_name);
            tvTripLocation = itemView.findViewById(R.id.tv_trip_location);
            tvTripDates = itemView.findViewById(R.id.tv_trip_dates);
            tvTripStatus = itemView.findViewById(R.id.tv_trip_status);
        }
    }
}