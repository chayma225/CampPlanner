package com.campplanner.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.models.BudgetCategory;
import java.util.List;

public class BudgetCategoryAdapter extends RecyclerView.Adapter<BudgetCategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<BudgetCategory> categoryList;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(BudgetCategory category);
    }

    public BudgetCategoryAdapter(Context context, List<BudgetCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_budget_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        BudgetCategory category = categoryList.get(position);

        holder.categoryName.setText(category.getName());
        holder.categoryBudget.setText(String.format("Budget: %.2f €", category.getBudget()));
        holder.categorySpent.setText(String.format("Dépensé: %.2f €", category.getSpent()));
        holder.categoryRemaining.setText(String.format("Restant: %.2f €", category.getRemaining()));

        // Set progress bar
        int percentage = (int) category.getPercentageUsed();
        holder.progressBar.setProgress(percentage);
        holder.percentageText.setText(percentage + "%");

        // Change color based on percentage
        if (percentage >= 90) {
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(context.getColor(R.color.budget_critical))
            );
            holder.percentageText.setTextColor(context.getColor(R.color.budget_critical));
        } else if (percentage >= 75) {
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(context.getColor(R.color.budget_warning))
            );
            holder.percentageText.setTextColor(context.getColor(R.color.budget_warning));
        } else {
            holder.progressBar.setProgressTintList(
                    android.content.res.ColorStateList.valueOf(context.getColor(R.color.budget_good))
            );
            holder.percentageText.setTextColor(context.getColor(R.color.budget_good));
        }

        // Set category color indicator
        if (category.getColor() != null) {
            try {
                holder.colorIndicator.setBackgroundColor(Color.parseColor(category.getColor()));
            } catch (IllegalArgumentException e) {
                holder.colorIndicator.setBackgroundColor(context.getColor(R.color.primary));
            }
        }

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateList(List<BudgetCategory> newList) {
        this.categoryList = newList;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View colorIndicator;
        TextView categoryName, categoryBudget, categorySpent, categoryRemaining, percentageText;
        ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            colorIndicator = itemView.findViewById(R.id.color_indicator);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryBudget = itemView.findViewById(R.id.category_budget);
            categorySpent = itemView.findViewById(R.id.category_spent);
            categoryRemaining = itemView.findViewById(R.id.category_remaining);
            progressBar = itemView.findViewById(R.id.progress_bar);
            percentageText = itemView.findViewById(R.id.percentage_text);
        }
    }
}