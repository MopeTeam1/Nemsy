package com.example.nemsy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    ArrayList<Bill> items = new ArrayList<Bill>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.bill_item, viewGroup, false);

        return new ViewHolder(itemView);
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

    public Bill getItem(int position) {
        return items.get(position);
    }

    public Bill setItem(int position, Bill item) {
        return items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, proposer, date;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.billName);
            age = itemView.findViewById(R.id.billAge);
            proposer = itemView.findViewById(R.id.proposer);
            date = itemView.findViewById(R.id.date);
        }

        public void setItem(Bill item) {
            name.setText(item.getName());
            age.setText(item.getAge() + "대");
            proposer.setText(item.getProposer());
            date.setText(item.getDate());
        }
    }
}
