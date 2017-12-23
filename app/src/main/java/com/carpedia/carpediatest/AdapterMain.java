package com.carpedia.carpediatest;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carpedia.carpediatest.Model.ModelEmployee;
import com.carpedia.carpediatest.Model.ModelMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private List<ModelMain> list;

    AdapterMain(List<ModelMain> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.job.setText(list.get(position).getJobTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEmployee dialogFragment = new DialogEmployee();
                dialogFragment.show(((Activity) view.getContext()).getFragmentManager(), "Dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView job;
        LinearLayout linearLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            job = itemView.findViewById(R.id.textViewJob);
            linearLayout = itemView.findViewById(R.id.itemMainLinearLayout);
        }
    }

    public static class DialogEmployee extends DialogFragment {

        RecyclerView recyclerView;
        AdapterEmployee adapterEmployee;
        List<ModelEmployee> list;
        ProgressBar progressBar;
        Button btnClose;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_employee, container, false);
            progressBar = view.findViewById(R.id.progressBarEmployee);
            recyclerView = view.findViewById(R.id.recycleEmployee);
            btnClose = view.findViewById(R.id.close);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            list = new ArrayList<>();
            adapterEmployee = new AdapterEmployee(list, view.getContext().getApplicationContext());
            recyclerView.setAdapter(adapterEmployee);
            retrofitGetEmployees();
            return view;
        }

        private void retrofitGetEmployees() {
            progressBar.setVisibility(View.VISIBLE);
            Retrofit retrofit = RetrofitBaseClient.create();
            IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
            Call<List<ModelEmployee>> call = iRetrofit.getEmployee();
            call.enqueue(new Callback<List<ModelEmployee>>() {
                @Override
                public void onResponse(Call<List<ModelEmployee>> call, Response<List<ModelEmployee>> response) {
                    if (response.isSuccessful()) {
                        list.addAll(response.body());
                        adapterEmployee.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<ModelEmployee>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
