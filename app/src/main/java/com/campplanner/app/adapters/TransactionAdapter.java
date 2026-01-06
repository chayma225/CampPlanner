package com.campplanner.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.models.Transaction;
import com.campplanner.app.utils.DateUtils;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<Transaction> transactionList;
    private OnTransactionClickListener listener;

    public interface OnTransactionClickListener {
        void onTransactionClick(Transaction transaction);
        void onTransactionLongClick(Transaction transaction);
    }

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    public void setOnTransactionClickListener(OnTransactionClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.transactionDescription.setText(transaction.getDescription());
        holder.transactionDate.setText(DateUtils.getRelativeDate(transaction.getDate()));
        holder.transactionAmount.setText(String.format("%.2f €", transaction.getAmount()));

        // Set icon and color based on type
        if ("revenu".equals(transaction.getType())) {
            holder.transactionIcon.setImageResource(R.drawable.ic_arrow_up);
            holder.transactionAmount.setTextColor(context.getColor(R.color.budget_good));
        } else {
            holder.transactionIcon.setImageResource(R.drawable.ic_arrow_down);
            holder.transactionAmount.setTextColor(context.getColor(R.color.budget_critical));
        }

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTransactionClick(transaction);
            }
        });

        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onTransactionLongClick(transaction);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void updateList(List<Transaction> newList) {
        this.transactionList = newList;
        notifyDataSetChanged();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView transactionIcon;
        TextView transactionDescription, transactionDate, transactionAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            transactionIcon = itemView.findViewById(R.id.transaction_icon);
            transactionDescription = itemView.findViewById(R.id.transaction_description);
            transactionDate = itemView.findViewById(R.id.transaction_date);
            transactionAmount = itemView.findViewById(R.id.transaction_amount);
        }
    }
}