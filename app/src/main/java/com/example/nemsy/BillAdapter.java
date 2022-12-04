package com.example.nemsy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> implements OnBillItemClickListener {
    ArrayList<Bill> items = new ArrayList<Bill>();
    OnBillItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.bill_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    public void setOnItemClickListener(OnBillItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Bill item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Bill item) {
        items.add(item);
    }

    public void setItems(ArrayList<Bill> items) {
        this.items = items;
    }

    public void clearItem() { items.clear(); }

    public Bill getItem(int position) {
        return items.get(position);
    }

    public Bill setItem(int position, Bill item) {
        return items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, proposer, date;

        public ViewHolder(View itemView, final OnBillItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.billName);
            age = itemView.findViewById(R.id.billAge);
            proposer = itemView.findViewById(R.id.proposer);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Bill item) {
            name.setText(item.BILL_NAME);
            age.setText(item.AGE + "대");
            proposer.setText(item.PROPOSER);
            date.setText(item.PROPOSE_DT);
        }
    }
    public void clear() {
        items.clear();
    }
}
