package com.campplanner.app.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.campplanner.app.R;
import com.campplanner.app.database.ActivityDAO;
import com.campplanner.app.models.Activity;
import com.campplanner.app.utils.DateUtils;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private Context context;
    private List<Activity> activityList;
    private ActivityDAO activityDAO;
    private OnActivityClickListener listener;

    public interface OnActivityClickListener {
        void onActivityClick(Activity activity);
        void onActivityLongClick(Activity activity);
    }

    public ActivityAdapter(Context context, List<Activity> activityList) {
        this.context = context;
        this.activityList = activityList;
        this.activityDAO = new ActivityDAO(context);
    }

    public void setOnActivityClickListener(OnActivityClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = activityList.get(position);

        holder.activityName.setText(activity.getName());
        holder.activityDate.setText(DateUtils.getRelativeDate(activity.getDate()));
        holder.activityTime.setText(activity.getTime());

        if (activity.getLocation() != null && !activity.getLocation().isEmpty()) {
            holder.activityLocation.setText(activity.getLocation());
            holder.activityLocation.setVisibility(View.VISIBLE);
        } else {
            holder.activityLocation.setVisibility(View.GONE);
        }

        if (activity.getDescription() != null && !activity.getDescription().isEmpty()) {
            holder.activityDescription.setText(activity.getDescription());
            holder.activityDescription.setVisibility(View.VISIBLE);
        } else {
            holder.activityDescription.setVisibility(View.GONE);
        }

        holder.checkboxCompleted.setChecked(activity.isCompleted());

        // Strike through text if completed
        if (activity.isCompleted()) {
            holder.activityName.setPaintFlags(holder.activityName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cardView.setAlpha(0.6f);
        } else {
            holder.activityName.setPaintFlags(holder.activityName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cardView.setAlpha(1.0f);
        }

        holder.checkboxCompleted.setOnClickListener(v -> {
            activity.setCompleted(holder.checkboxCompleted.isChecked());
            activityDAO.updateActivity(activity);
            notifyItemChanged(position);
        });

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActivityClick(activity);
            }
        });

        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onActivityLongClick(activity);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void updateList(List<Activity> newList) {
        this.activityList = newList;
        notifyDataSetChanged();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView activityName, activityDate, activityTime, activityLocation, activityDescription;
        CheckBox checkboxCompleted;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            activityName = itemView.findViewById(R.id.activity_name);
            activityDate = itemView.findViewById(R.id.activity_date);
            activityTime = itemView.findViewById(R.id.activity_time);
            activityLocation = itemView.findViewById(R.id.activity_location);
            activityDescription = itemView.findViewById(R.id.activity_description);
            checkboxCompleted = itemView.findViewById(R.id.checkbox_completed);
        }
    }
}