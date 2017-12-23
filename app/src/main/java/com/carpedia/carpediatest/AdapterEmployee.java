package com.carpedia.carpediatest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.carpedia.carpediatest.Model.ModelEmployee;
import java.util.List;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.ViewHolder> {
    private List<ModelEmployee> list;
    private Context context;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.firstName.setText(list.get(position).getFirstName());
        holder.lastName.setText(list.get(position).getLastName());
        holder.salary.setText(list.get(position).getSalary());
        setImage(list.get(position).getAvatar(), holder.imageView);
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted()) {
                    call_action(holder.getAdapterPosition());
                } else {
                    Toast.makeText(context, "You don't have permission", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void call_action(int position) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + list.get(position).getNumber()));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView firstName;
        TextView lastName;
        TextView salary;
        Button call;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemEmployeeImage);
            firstName = itemView.findViewById(R.id.itemEmployeeFirstName);
            lastName = itemView.findViewById(R.id.itemEmployeeLastName);
            salary = itemView.findViewById(R.id.itemEmployeeSalary);
            call = itemView.findViewById(R.id.itemEmployeeBtnCall);
        }
    }

    private void setImage(String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(100, 100)
                .into(imageView);
    }
}
