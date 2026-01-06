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
import com.campplanner.app.models.Reservation;
import com.campplanner.app.utils.Constants;
import com.campplanner.app.utils.DateUtils;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private Context context;
    private List<Reservation> reservationList;
    private OnReservationClickListener listener;

    public interface OnReservationClickListener {
        void onReservationClick(Reservation reservation);
        void onReservationLongClick(Reservation reservation);
    }

    public ReservationAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    public void setOnReservationClickListener(OnReservationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);

        holder.reservationName.setText(reservation.getName());
        holder.reservationType.setText(reservation.getType());
        holder.reservationDate.setText(DateUtils.getRelativeDate(reservation.getDate()));
        holder.reservationPrice.setText(String.format("%.2f €", reservation.getPrice()));
        holder.reservationStatus.setText(reservation.getStatus());

        if (reservation.getConfirmationNumber() != null && !reservation.getConfirmationNumber().isEmpty()) {
            holder.confirmationNumber.setText("Confirmation: " + reservation.getConfirmationNumber());
            holder.confirmationNumber.setVisibility(View.VISIBLE);
        } else {
            holder.confirmationNumber.setVisibility(View.GONE);
        }

        // Set status color
        int statusColor;
        switch (reservation.getStatus()) {
            case Constants.RESERVATION_CONFIRMED:
                statusColor = context.getColor(R.color.status_confirmed);
                break;
            case Constants.RESERVATION_PENDING:
                statusColor = context.getColor(R.color.status_planned);
                break;
            case Constants.RESERVATION_CANCELLED:
                statusColor = context.getColor(R.color.budget_critical);
                break;
            default:
                statusColor = context.getColor(R.color.text_secondary);
                break;
        }
        holder.reservationStatus.setTextColor(statusColor);

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservationClick(reservation);
            }
        });

        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onReservationLongClick(reservation);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public void updateList(List<Reservation> newList) {
        this.reservationList = newList;
        notifyDataSetChanged();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView reservationName, reservationType, reservationDate, reservationPrice,
                reservationStatus, confirmationNumber;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            reservationName = itemView.findViewById(R.id.reservation_name);
            reservationType = itemView.findViewById(R.id.reservation_type);
            reservationDate = itemView.findViewById(R.id.reservation_date);
            reservationPrice = itemView.findViewById(R.id.reservation_price);
            reservationStatus = itemView.findViewById(R.id.reservation_status);
            confirmationNumber = itemView.findViewById(R.id.confirmation_number);
        }
    }
}