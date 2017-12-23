package com.carpedia.carpediatest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        adapterMain = new AdapterMain(list);
        recyclerView.setAdapter(adapterMain);
        retrofitGetJobs();
        isPermissionGranted();
    }

    private void isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
        }
    }

    private void retrofitGetJobs() {
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
