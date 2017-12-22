package com.carpedia.carpediatest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carpedia.carpediatest.Model.ModelEmployee;

import java.util.List;

/**
 * Created by rusci on 22-Dec-17.
 */

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.ViewHolder> {
    List<ModelEmployee> list;
    Context context;

    AdapterEmployee(List<ModelEmployee> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.firstName.setText(list.get(position).getFirstName());
        holder.lastName.setText(list.get(position).getLastName());
        holder.salary.setText(list.get(position).getSalary());
        setImage(list.get(position).getAvatar(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView firstName;
        TextView lastName;
        TextView salary;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemEmployeeImage);
            firstName = itemView.findViewById(R.id.itemEmployeeFirstName);
            lastName = itemView.findViewById(R.id.itemEmployeeLastName);
            salary = itemView.findViewById(R.id.itemEmployeeSalary);
        }
    }

    private void setImage(String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(100, 100)
                .into(imageView);
    }
}
