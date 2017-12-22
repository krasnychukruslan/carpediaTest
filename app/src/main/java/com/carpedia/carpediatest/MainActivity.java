package com.carpedia.carpediatest;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.carpedia.carpediatest.Model.ModelMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterMain adapterMain;
    List<ModelMain> list;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBarMain);
        recyclerView = findViewById(R.id.recycleMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterMain= new AdapterMain(list, getApplicationContext());
        recyclerView.setAdapter(adapterMain);
        retrofitGetJobs();
    }

    private void retrofitGetJobs () {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitBaseClient.create();
        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
        Call<List<ModelMain>> call = iRetrofit.getJobs();
        call.enqueue(new Callback<List<ModelMain>>() {
            @Override
            public void onResponse(Call<List<ModelMain>> call, Response<List<ModelMain>> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body());
                    adapterMain.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelMain>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
