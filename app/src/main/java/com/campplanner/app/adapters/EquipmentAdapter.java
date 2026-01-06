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
import com.campplanner.app.activities.EquipmentActivity;
import com.campplanner.app.database.EquipmentDAO;
import com.campplanner.app.models.EquipmentItem;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    private Context context;
    private List<EquipmentItem> equipmentList;
    private EquipmentDAO equipmentDAO;
    private OnEquipmentClickListener listener;

    public void setOnEquipmentClickListener(EquipmentActivity equipmentActivity) {
        this.listener = listener;
    }

    public interface OnEquipmentClickListener {
        void onEquipmentClick(EquipmentItem item);
        void onEquipmentLongClick(EquipmentItem item);

        void onEquipmentCheckedChanged(EquipmentItem item, boolean isChecked);
    }

    public EquipmentAdapter(Context context, List<EquipmentItem> equipmentList) {
        this.context = context;
        this.equipmentList = equipmentList;
        this.equipmentDAO = new EquipmentDAO(context);
    }


    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_equipement, parent, false);
        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        EquipmentItem item = equipmentList.get(position);

        holder.itemName.setText(item.getName());
        holder.itemCategory.setText(item.getCategory());
        holder.itemQuantity.setText("Quantité: " + item.getQuantity());
        holder.checkboxPacked.setChecked(item.isPacked());

        // Strike through text if packed
        if (item.isPacked()) {
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cardView.setAlpha(0.6f);
        } else {
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cardView.setAlpha(1.0f);
        }

        holder.checkboxPacked.setOnClickListener(v -> {
            item.setPacked(holder.checkboxPacked.isChecked());
            equipmentDAO.updateEquipmentItem(item);
            notifyItemChanged(holder.getAdapterPosition()); // Use getAdapterPosition()
        });

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEquipmentClick(equipmentList.get(holder.getAdapterPosition())); // Use getAdapterPosition()
            }
        });

        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onEquipmentLongClick(equipmentList.get(holder.getAdapterPosition())); // Use getAdapterPosition()
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public void updateList(List<EquipmentItem> newList) {
        this.equipmentList = newList;
        notifyDataSetChanged();
    }

    public int getPackedCount() {
        int count = 0;
        for (EquipmentItem item : equipmentList) {
            if (item.isPacked()) count++;
        }
        return count;
    }

    public int getTotalCount() {
        return equipmentList.size();
    }

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView itemName, itemCategory, itemQuantity;
        CheckBox checkboxPacked;

        public EquipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            itemName = itemView.findViewById(R.id.item_name);
            itemCategory = itemView.findViewById(R.id.item_category);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            checkboxPacked = itemView.findViewById(R.id.checkbox_packed);
        }
    }
}